package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Петр", "1", (byte) 33);
        userService.saveUser("Александр", "2", (byte) 40);
        userService.saveUser("Павел", "3", (byte) 29);
        userService.saveUser("Иван", "4", (byte) 36);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
