package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        final UserService userDaoHibernate = new UserServiceImpl();

        userDaoHibernate.createUsersTable();
        userDaoHibernate.saveUser("Ivan", "Ivanov", (byte) 99);
        userDaoHibernate.saveUser("Zaur", "Kazachkov", (byte) 99);
        userDaoHibernate.saveUser("Ruben", "Pupin", (byte) 99);
        userDaoHibernate.saveUser("Ilya", "Ivanov", (byte) 99);

        System.out.println(userDaoHibernate.getAllUsers());

        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();
    }
}
