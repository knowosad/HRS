package pl.com.bottega.hrs.application.users.commands;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.application.users.Role;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.ValidationErrors;

import java.util.Set;

/**
 * Created by freszczypior on 2017-11-30.
 */
@Getter
@Setter
public class UpdateUserCommand implements Command {

    private Integer id;

    private String login;

    private String newPassword;

    private String repeatedNewPassword;

    private Set<Role> roles;

    private final String LOGIN_PATTERN = "[a-zA-Z0-9]*";
    private final String PASSWORD_ANTY_PATTERN = "[ ]*";

    public void validate(ValidationErrors errors) {

        validatePresence(errors, "id", id);

        if (login != null && !login.matches(LOGIN_PATTERN)) {
            errors.add("login", "must contain only a-z, A-Z, 0-9");
        }
        if (newPassword != null && !newPassword.equals(repeatedNewPassword)) {
            errors.add("repeatedPassword", "both passwords must be the same");
        }
        if (newPassword != null && newPassword.matches(PASSWORD_ANTY_PATTERN) && newPassword.length() < 6) {
            errors.add("password", "password must contain more then six characters");
        }
        if (roles != null && roles.isEmpty()){
            errors.add("roles", "new roles must contain some elements");
        }
    }


}
