package com.company.jdbcConnection;

import com.company.client.User;
import com.company.file.ReadFile;
import com.company.file.ReadFileImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class JDBCConnector {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public Connection getConnection(){
        ReadFileImpl readFile = new ReadFileImpl();
        HashMap<String,String> properties = readFile.getProperties();
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(properties.get("url"),properties.get("user"),properties.get("password"));
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public abstract void insertIntoTable(User user);
    public abstract String selectFromTable();
}
