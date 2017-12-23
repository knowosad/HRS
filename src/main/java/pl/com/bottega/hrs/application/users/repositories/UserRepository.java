package pl.com.bottega.hrs.application.users.repositories;

import pl.com.bottega.hrs.application.users.User;

/**
 * Created by freszczypior on 2017-11-28.
 */
public interface UserRepository {

    void save(User user);

    User get(String login);

    User get(Integer id);

    boolean loginOccupied(String login);

    User get(String login, String password);
}
