package pl.com.bottega.hrs.application.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.application.users.Role;
import pl.com.bottega.hrs.application.users.User;

import java.util.Set;

/**
 * Created by freszczypior on 2017-12-09.
 */
@Getter
@Setter
public class UserDto {

    private Integer id;
    private String login;
    private Set<Role> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.roles = user.getRoles();
    }
}
