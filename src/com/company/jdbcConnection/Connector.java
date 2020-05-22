package com.company.jdbcConnection;

import com.company.client.User;

import java.sql.*;

public class Connector extends JDBCConnector {
    @Override
    public void insertIntoTable(User user) {
        Connection connection = getConnection();
        String sql = "insert into user (user_name, message) values (?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getMessage());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String selectFromTable() {
        String message = "";
        Connection connection = getConnection();
        String sql = "select * from user";
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(sql);
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                User user = new User();
                user.setName(resultSet.getString("user_name"));
                user.setMessage(resultSet.getString("message"));
                message += user.toString() + "\r\n";
            }
            return message;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return message;
    }
}
