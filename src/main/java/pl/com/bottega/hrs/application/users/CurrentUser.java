package pl.com.bottega.hrs.application.users;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.com.bottega.hrs.application.dtos.UserDto;
import pl.com.bottega.hrs.application.users.repositories.UserRepository;

import java.util.Optional;

/**
 * Created by freszczypior on 2017-12-09.
 */
@Component
@SessionScope
public class CurrentUser {

    private Integer userId;

    private UserRepository repository;

    public CurrentUser(UserRepository repository) {
        this.repository = repository;
    }

    public void login(User user){
        userId = user.getId();
    }

    public void logout(){
        userId = null;
    }

    public Optional<UserDto> getUserInfo() {
        if (userId == null)
            return Optional.empty();
        User user = repository.get(userId);
        return Optional.of(new UserDto(user));
    }

    public boolean isAuthenticated(Role[] requiredRoles) {
        if (userId != null)
            return false;
        if (requiredRoles == null || requiredRoles.length == 0)
            return true;
        User user = repository.get(userId);
        return user.hasRoles(requiredRoles);
    }
}
