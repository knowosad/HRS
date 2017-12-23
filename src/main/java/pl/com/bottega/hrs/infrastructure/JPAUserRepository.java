package pl.com.bottega.hrs.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.application.users.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Created by freszczypior on 2017-11-28.
 */
@Component
public class JPAUserRepository implements UserRepository {

    private EntityManager entityManager;

    public JPAUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User get(String login) {
        Query query = entityManager.createQuery("SELECT " +
                "NEW pl.com.bottega.hrs.application.users.User(u.id, u.login, u.password) FROM User u " +
                "WHERE u.login LIKE :login");
        query.setParameter("login", login);
        User user = (User) query.getSingleResult();
        if (user == null)
            throw new NoSuchEntityException();
        return user;
    }

    @Override
    public User get(Integer id) {
        User user = entityManager.find(User.class, id);
        if (user == null)
            throw new NoSuchEntityException();
        return user;
    }

    @Override
    public boolean loginOccupied(String login) {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM User u WHERE u.login LIKE :login");
        query.setParameter("login", login);
        return ((Long) (query.getSingleResult())).intValue() > 0 ? true : false;
    }

    @Override
    public User get(String login, String password) {
        try {
            User user = (User) entityManager.createQuery("SELECT u FROM User u WHERE u.login LIKE :login " +
                    "AND u.password LIKE :password").
                    setParameter("login", login).
                    setParameter("password", password).
                    getSingleResult();
            return user;
        } catch (NoResultException ex) {
            throw new NoSuchEntityException();
        }
    }
}
