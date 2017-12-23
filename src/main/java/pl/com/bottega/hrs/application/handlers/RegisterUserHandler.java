package pl.com.bottega.hrs.application.handlers;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.application.users.commands.RegisterUserCommand;
import pl.com.bottega.hrs.model.commands.ValidationErrors;
import pl.com.bottega.hrs.application.users.repositories.UserRepository;

import javax.transaction.Transactional;

/**
 * Created by freszczypior on 2017-11-28.
 */

@Component
public class RegisterUserHandler implements Handler<RegisterUserCommand> {

    private UserRepository repository;
    private ValidationErrors errors;

    public RegisterUserHandler(UserRepository userRepository, ValidationErrors errors) {
        this.repository = userRepository;
        this.errors = errors;
    }
    @Transactional
    @Override
    public void handle(RegisterUserCommand command) {
        validateLoginIsFree(command);
        repository.save(new User(command.getLogin(), command.getPassword()));
    }

    private void validateLoginIsFree(RegisterUserCommand command){
        if (isOccupied(command.getLogin())) {
            errors.add("login", "such login already exists");
            throw new CommandInvalidException(errors);
        }
    }

    private boolean isOccupied(String login) {
        return repository.loginOccupied(login);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return RegisterUserCommand.class;
    }
}
