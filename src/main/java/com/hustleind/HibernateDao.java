package com.hustleind;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
public class HibernateDao implements UserDao {

    private static Logger logger = Logger.getLogger(HibernateDao.class);

    private SessionFactory factory;

    @Autowired
    public HibernateDao(SessionFactory factory){
        this.factory=factory;
    }

    @Override
    public User getUserById(int id) {
        if (id < 0) {
            logger.error("Unable to return user with id = " +id);
            return null;
        }
        Session session = factory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public User getUserByEmail(String email) {
        if (email==null || email.isEmpty()) {
            logger.error("Unable to return user by email: email parameter is null or empty");
            return null;
        }
        Session session = factory.getCurrentSession();
        return (User) session.createQuery("FROM User user WHERE user.email = :email").
                setParameter("email", email).
                uniqueResult();
    }

    @Override
    public List<User> getUsers() {
        Session session = factory.getCurrentSession();
        return session.createQuery("FROM User").getResultList();
    }

    @Override
    public void addUser(User user) {
        if (user==null) {
            logger.error("Unable to add user. User parameter is null");
            return;
        }
        Session session = factory.getCurrentSession();
        session.save(user);
    }

    @Override
    public void updateUser(User user) {
        if (user==null) {
            logger.error("Unable to update user. User parameter is null");
            return;
        }
        Session session = factory.getCurrentSession();
        session.update(user);
    }

    @Override
    public void deactivateUser(User user) {
        if (user==null) {
            logger.error("Unable to deativate user. User parameter is null");
            return;
        }
        Session session = factory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.set(root.get("enabled"), 0).
                where(builder.equal(root.get("email"), user.getEmail()));

    }

    @Override
    public void deleteUserById(int id) {
        if (id < 0) {
            logger.error("Unable to delete user with id = " + id);
            return;
        }
        Session session = factory.getCurrentSession();
        User user = getUserById(id);
        session.delete(user);
    }
}
