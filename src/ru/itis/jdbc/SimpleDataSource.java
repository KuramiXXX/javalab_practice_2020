package ru.itis.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 09.07.2020
 * 01. Database
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class SimpleDataSource {
    public Connection openConnection(String url, String user, String password) {
        try {
            return DriverManager.getConnection(url,  user, password);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
