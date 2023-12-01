package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `Users` ( `id` bigint NOT NULL AUTO_INCREMENT, `name` varchar(45) " +
                    "NOT NULL, `lastName` varchar(45) NOT NULL,  `age` tinyint NOT NULL, PRIMARY KEY (`id`));");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ea) {
                System.out.println("Rollback ecxeption");
            }
        }
    }

    public void dropUsersTable() {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ea) {
                System.out.println("Rollback ecxeption");
            }
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(" INSERT INTO Users (name, lastName, age) VALUES(?, ?, ?);")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println(name + " added to table");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ea) {
                System.out.println("Rollback ecxeption");
            }

        }

    }

    public void removeUserById(long id) {

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE ID = " + id)) {
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ea) {
                System.out.println("Rollback exception");
            }
        }

    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();


        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, lastName, age FROM Users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ea) {
                System.out.println("Rollback exception");
            }
        }
        return users;
    }

    public void cleanUsersTable() {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE Users");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ea) {
                System.out.println("Rollback exception");
            }
        }

    }
}
