package pl.com.bottega.hrs.application.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.application.users.Role;
import pl.com.bottega.hrs.application.users.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by freszczypior on 2017-11-29.
 */
@Getter
@Setter
public class DetailedUserDto {

    private Integer id;

    private String login;

    private String password;

    private Set<Role> roles;

    public DetailedUserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.roles = new HashSet<>(user.getRoles());
    }
}
