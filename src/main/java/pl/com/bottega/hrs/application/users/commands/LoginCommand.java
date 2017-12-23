package pl.com.bottega.hrs.application.users.commands;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.ValidationErrors;

/**
 * Created by freszczypior on 2017-12-09.
 */
@Getter
@Setter
public class LoginCommand implements Command {

    private String login, password;

    @Override
    public void validate(ValidationErrors errors){
        validatePresence(errors, "login", login);
        validatePresence(errors, "password", password);

    }

}
