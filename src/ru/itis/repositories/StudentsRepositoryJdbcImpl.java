package ru.itis.repositories;

import ru.itis.models.Mentor;
import ru.itis.models.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 10.07.2020
 * 01. Database
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class StudentsRepositoryJdbcImpl implements StudentsRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from student where id = ";
    private static final String SQL_SELECT_ALL = "select * from student";
    private static final String SQL_SELECT_MENTORS = "select * from mentor where id = ";
    private static final String SQL_SELECT_ALL_BY_AGE = SQL_SELECT_ALL + " where age = ";
    private static final String SQL_INSERT_STUDENT = "insert into student (first_name, last_name, age, group_number)" +
            "values (";

    private Connection connection;

    public StudentsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Student> findAllByAge(int age) {

        try (Statement statement = connection.createStatement(); ResultSet result = statement.executeQuery(
                SQL_SELECT_ALL_BY_AGE + age)) {
            ArrayList<Student> list = new ArrayList<>();
            while (result.next()) {
                list.add(new Student(
                        result.getLong("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("age"),
                        result.getInt("group_number")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    // Необходимо вытащить список всех студентов, при этом у каждого студента должен быть проставлен список менторов
    // у менторов в свою очередь ничего проставлять (кроме имени, фамилии, id не надо)
    // student1(id, firstName, ..., mentors = [{id, firstName, lastName, null}, {}, ), student2, student3
    // все сделать одним запросом
    @Override
    public List<Student> findAll() {

        try (Statement statement = connection.createStatement(); ResultSet result = statement.executeQuery(
                SQL_SELECT_ALL)) {
            ArrayList<Student> list = new ArrayList<>();
            while (result.next()) {
                Student current = new Student(
                        result.getLong("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("age"),
                        result.getInt("group_number")
                );
                try (ResultSet mentors = statement.executeQuery(SQL_SELECT_MENTORS + current.getId())) {
                    ArrayList<Mentor> mentorArrayList = new ArrayList<>();
                    while (mentors.next()) {
                        mentorArrayList.add(new Mentor(
                                mentors.getLong("id"),
                                mentors.getString("first_name"),
                                mentors.getString("last_name"),
                                current
                        ));
                    }
                    current.setMentors(mentorArrayList);
                }
                list.add(current);
            }
            return list;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Student findById(Long id) {

        try (Statement statement = connection.createStatement(); ResultSet result = statement.executeQuery(
                SQL_SELECT_BY_ID + id)) {
            if (result.next()) {
                return new Student(
                        result.getLong("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("age"),
                        result.getInt("group_number")
                );
            } else return null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        // ignore
        // ignore
    }

    // просто вызывается insert для сущности
    // student = Student(null, 'Марсель', 'Сидиков', 26, 915)
    // studentsRepository.save(student);
    // // student = Student(3, 'Марсель', 'Сидиков', 26, 915)
    @Override
    public void save(Student entity) {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("" +
                            "insert into student (first_name, last_name, age, group_number)" +
                            " values ('%s', '%s', %d, %d)",
                    entity.getFirstName(), entity.getLastName(), entity.getAge(), entity.getGroupNumber()), Statement.RETURN_GENERATED_KEYS);
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                entity.setId(result.getLong(1));
            } else {
                throw new IllegalArgumentException();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    // для сущности, у которой задан id выполнить обновление всех полей

    // student = Student(3, 'Марсель', 'Сидиков', 26, 915)
    // student.setFirstName("Игорь")
    // student.setLastName(null);
    // studentsRepository.update(student);
    // (3, 'Игорь', null, 26, 915)

    @Override
    public void update(Student entity) {

        try (Statement statement = connection.createStatement()) {
            statement.executeQuery("update first_name set value = " + entity.getFirstName() + "where id = " +
                    entity.getId());
            statement.executeQuery("update last_name set value = " + entity.getLastName() + "where id = " +
                    entity.getId());
            statement.executeQuery("update age set value = " + entity.getAge() + "where id = " + entity.getId());
            statement.executeQuery("update group_number set value = " + entity.getGroupNumber() + "where id = " +
                    entity.getId());
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
