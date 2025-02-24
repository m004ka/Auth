package org.example.Account;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;


@RequiredArgsConstructor
public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;

    }

    public User get(String login) throws HibernateException {
        try {
            return session.createQuery("FROM User WHERE login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResult();
        } catch (Exception e) {
            throw new HibernateException("Ошибка при получении пользователя с логином: " + login, e);
        }
    }

    public long insertUser(User user) throws HibernateException {
        session.persist(user);
        return user.getId();
    }
}