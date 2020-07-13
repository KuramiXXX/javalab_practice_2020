# 01. Database

## Базы данных

* Бизнес-логика (само приложение) <-> Данные

* СУБД = Приложение для администрирования + база данных

1. Хранение данных
2. Поиск
3. Безопасность
4. Целостность данных

* SQL

## Реляционные базы данных

- Таблицы
	- Столбцы - название, тип, ограничения
	- Строки - каждая строка представляет собой единицу информации

`student`

```
id bigint PK| first_name char(20)| last_name char(20)| age integer| group_number integer|
-----------------------------------------------------------------------------------------
           1| Марсель            | Сидиков           | 26         | 09-915              |
```

`subject`

```
id bignint PK| title char(20) |
-------------------------------
          32 | Math           |
          77 | English        |
```

`mark`

```
student_id FK| subject_id FK | value
-------------------------------------
           1 |            32 |    50
           1 |            77 |    99
```

`Primary Key` - первичный ключ, идентификатор строки, уникальный для всей таблицы
`Foreign Key` - внешний ключ, ссылка на другой столбец 

`student_id` -> `student (id)`
`subject_id` -> `subject (id)`

* MySQL
* SQL Server
* SQLite
* H2
* HSQL
* Oracle
* PostgreSQL

```sql
create table student 
(
	id bigserial primary key,
	first_name char(20),
	last_name char(20),
	age integer,
	group_number integer
);

create table subject
(
    id    bigserial primary key,
    title char(20)
);

create table mark
(
    student_id bigint,
    subject_id bigint,
    value      integer,
    foreign key (student_id) references student (id),
    foreign key (subject_id) references subject (id)
);


insert into student (first_name, last_name, age, group_number)
values ('Марсель', 'Сидиков', 26, 915),
       ('Максим', 'Иванов', 19, 904),
       ('Леонид', 'Тыщенко', 19, 902);

insert into subject (title)
values ('Math'), ('English');

insert into mark (student_id, subject_id, value)
values (2, 1, 90),
       (2, 2, 95),
       (3, 1, 91),
       (3, 2, 96),
       (4, 1, 95),
       (4, 2, 94)
```

* Получение всех записей

```sql
select *
from student;
```

* Получение конкретных колонок

```sql
select first_name, last_name
from student;
```

* Сортировка

```sql
select * from student order by age desc;
```

* Условие выборки

```sql
select * from student where age > 25;
select * from student where age < 25 and group_number = 902;
select * from student where age > 25 or group_number = 902;
```

```sql
select *
from student s
where id in (select id from mark where value > 90 and student_id = s.id);
```

* Обновление

```sql
update mark set value = 20 where student_id = 2;
```

* Изменение колонок (добавление внешнего ключа)

```sql
alter table mentor add foreign key(student_id) references student(id);
alter table mentor add foreign key(subject_id) references subject(id);
```

## JOINS

* INNER JOIN

```sql
select *
from student s
         inner join mentor m on s.id = m.student_id;
```

* LEFT JOIN

```sql
select *
from student s
         left join mentor m on s.id = m.student_id;
```

* RIGHT JOIN

```sql
select *
from student s
         right join mentor m on s.id = m.student_id;
```

* FULL OUTER JOIN

```sql
select *
from student s
         full outer join mentor m on s.id = m.student_id;
```

* Переименование колонок 

```sql
select s.id as s_id, m.id as m_id, *
from student s
         inner join mentor m on s.id = m.student_id;
```

[JOINS](https://www.w3schools.com/sql/sql_join.asp)

## Базы данных в JAVA

* Баз данных много, а Java одна.

* Для того, чтобы Java могла работать с конкретной СУБД, эта самая СУБД предоставляет драйвер. Данный подход называется JDBC.

* Java не знает, как это СУБД, код приложения не зависит от СУБД, код обращается к общим интерфейсам, а эти интерфейсы реализуются конкретным драйвером.

### Классы и интерфейсы java.sql

`Connection` - интерфейс, объекты которого описывают подключение к БД.

`DriverManager`  - класс для работы с JDBC-драйверами.

`Statement` - интерфейс, позволяющий отправлять запросы в БД.

`ResultSet` - интерфейс, представляющий собой итератор по результирующей таблице.