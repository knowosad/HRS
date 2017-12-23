package pl.com.bottega.hrs.acceptance;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.CommandGateway;
import pl.com.bottega.hrs.application.UserFinder;
import pl.com.bottega.hrs.application.dtos.DetailedUserDto;
import pl.com.bottega.hrs.application.users.Role;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.application.users.commands.RegisterUserCommand;
import pl.com.bottega.hrs.application.users.commands.UpdateUserCommand;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by freszczypior on 2017-11-30.
 */
@SpringBootTest
@RunWith (SpringRunner.class)
public class UpdateUserTest extends AcceptanceTest {

    @Autowired
    private RegisterUserCommand command;

    @Autowired
    private UserFinder userFinder;

    @Autowired
    private CommandGateway gateway;

    @Before
    public void setUser(){
        createRegisterUserCommand("sutLogin", "sutPassword", "sutPassword");
    }

    @Test
    public void shouldUpdateUser(){
        //when
        UpdateUserCommand updateCommand = new UpdateUserCommand();
        updateCommand.setId(1);
        updateCommand.setLogin("newLogin");
        updateCommand.setNewPassword("newPassword");
        updateCommand.setRepeatedNewPassword("newPassword");
        gateway.execute(updateCommand);

        //then
        DetailedUserDto detailedUserDto = userFinder.getUserDetails(1);
        assertEquals("newLogin", detailedUserDto.getLogin());
        assertEquals("newPassword", detailedUserDto.getPassword());
    }
    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToChangeLoginForTheSameItWas(){
        //when
        UpdateUserCommand updateCommand = new UpdateUserCommand();
        updateCommand.setId(1);
        updateCommand.setLogin("sutLogin");
        gateway.execute(updateCommand);
        cleanUp();
    }
    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToUpdateUserWhenGotDifferentPasswords(){
        //when
        UpdateUserCommand updateCommand = new UpdateUserCommand();
        updateCommand.setId(1);
        updateCommand.setNewPassword("newPassword");
        updateCommand.setRepeatedNewPassword("XXnewPassword");
        gateway.execute(updateCommand);
    }
    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToUpdateUserWhenRolesAreEmpty(){
        //when
        Set<Role> newRoles = new HashSet<>();
        UpdateUserCommand updateCommand = new UpdateUserCommand();
        updateCommand.setId(1);
        updateCommand.setRoles(newRoles);
        gateway.execute(updateCommand);
    }
    private void createRegisterUserCommand(String login, String password, String newPassword) {
        command.setLogin(login);
        command.setPassword(password);
        command.setRepeatedPassword(newPassword);
        gateway.execute(command);
    }
}
