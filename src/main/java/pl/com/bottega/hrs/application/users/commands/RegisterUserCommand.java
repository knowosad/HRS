package pl.com.bottega.hrs.application.users.commands;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.ValidationErrors;

/**
 * Created by freszczypior on 2017-11-28.
 */
@Getter
@Setter
@Component
public class RegisterUserCommand implements Command {

    private String login;
    private String password;
    private String repeatedPassword;

    private final String LOGIN_PATTERN = "[a-zA-Z0-9]*";
    private final String PASSWORD_ANTY_PATTERN = "[ ]*";

    public void validate(ValidationErrors errors){
        validatePresence(errors, "login", login);
        validatePresence(errors, "password", password);
        validatePresence(errors, "password", repeatedPassword);

        if (!password.equals(repeatedPassword)){
            errors.add("repeatedPassword", "both passwords must be the same");
        }
        if (!login.matches(LOGIN_PATTERN)){
            errors.add("login", "must contain only a-z, A-Z, 0-9");
        }
        if (password.matches(PASSWORD_ANTY_PATTERN) || password.length() < 6){
            errors.add("password", "password must contain more then six characters and shouldn't contains spaces");
        }
    }


}
