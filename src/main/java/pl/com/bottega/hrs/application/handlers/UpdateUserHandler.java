package pl.com.bottega.hrs.application.handlers;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.application.users.commands.UpdateUserCommand;
import pl.com.bottega.hrs.model.commands.ValidationErrors;
import pl.com.bottega.hrs.application.users.repositories.UserRepository;

import javax.transaction.Transactional;

/**
 * Created by freszczypior on 2017-11-30.
 */
@Component
public class UpdateUserHandler implements Handler<UpdateUserCommand> {

    private UserRepository repository;
    private ValidationErrors errors;

    public UpdateUserHandler(UserRepository repository, ValidationErrors errors) {
        this.repository = repository;
        this.errors = errors;
    }

    @Override
    @Transactional
    public void handle(UpdateUserCommand command) {
        validateLoginIsFree(command);
        User user = repository.get(command.getId());
        user.updateProfile(command.getLogin(), command.getNewPassword(), command.getRoles());
        repository.save(user);
    }

    private void validateLoginIsFree(UpdateUserCommand command){
        if (isPresent(command.getLogin()) && isOccupied(command.getLogin())) {
            errors.add("login", "such login already exists");
            throw new CommandInvalidException(errors);
        }
    }

    private boolean isPresent(String login) {
        return login != null;
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return UpdateUserCommand.class;
    }

    private boolean isOccupied(String login) {
        return repository.loginOccupied(login);
    }
}
