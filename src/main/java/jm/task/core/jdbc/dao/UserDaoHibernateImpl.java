package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction;
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    //
//    Session session = sessionFactory.openSession();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = sessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users"
                    + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(30), " +
                    "lastName VARCHAR(30), age TINYINT)").executeUpdate();
            transaction.commit();
        }   catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction !=null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, (byte) age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction !=null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
            } else {
                System.out.println("User with id " + id + " not found");
            }
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }


    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try(Session session = sessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = session.createCriteria(User.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User");
            transaction.commit();
        } catch (Exception e) {
            if (transaction !=null) {
                transaction.rollback();
            }
        }
    }
}
