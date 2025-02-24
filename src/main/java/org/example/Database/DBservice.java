package org.example.Database;

import org.example.Account.User;
import org.example.Account.UsersDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DBservice {


    public long addNewUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            long id = dao.insertUser(user);
            transaction.commit();
            return id;
        } catch (HibernateException e) {
            throw new HibernateException("Ошибка добавления пользователя");
        }
    }

    public boolean checkUser(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            User user = dao.get(login);
            transaction.commit();
            return user == null;
        } catch (HibernateException e) {
            throw new HibernateException("Ошибка поиска пользователя");
        }
    }

    public boolean authUser(String login, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            User user = dao.get(login);
            transaction.commit();
            return user.getPassword().equals(password);
        } catch (HibernateException e) {
            throw new HibernateException("Ошибка авторизации пользователя");
        }
    }
}
