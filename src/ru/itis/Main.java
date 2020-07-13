package ru.itis;

import ru.itis.jdbc.SimpleDataSource;
import ru.itis.repositories.StudentsRepository;
import ru.itis.repositories.StudentsRepositoryJdbcImpl;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/java_lab_pract_2020";
    private static final String USER = "postgres";
    private static final String PASSWORD = "qwerty007";


    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        StudentsRepository studentsRepository = new StudentsRepositoryJdbcImpl(connection);
        System.out.println(studentsRepository.findById(2L));
        connection.close();
    }
}
