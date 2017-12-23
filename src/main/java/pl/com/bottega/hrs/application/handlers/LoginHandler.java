package pl.com.bottega.hrs.application.handlers;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.users.CurrentUser;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.application.users.commands.LoginCommand;
import pl.com.bottega.hrs.application.users.repositories.UserRepository;
import pl.com.bottega.hrs.model.commands.Command;

/**
 * Created by freszczypior on 2017-12-09.
 */
@Component
public class LoginHandler implements Handler<LoginCommand> {

    private UserRepository repository;
    private CurrentUser currentUser;


    public LoginHandler(UserRepository repository, CurrentUser currentUser) {
        this.repository = repository;
        this.currentUser = currentUser;
    }

    @Override
    public void handle(LoginCommand command) {
        User user = repository.get(command.getLogin(), command.getPassword());
        currentUser.login(user);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return LoginCommand.class;
    }
}
