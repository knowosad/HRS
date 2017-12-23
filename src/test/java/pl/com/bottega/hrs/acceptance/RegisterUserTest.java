package pl.com.bottega.hrs.acceptance;

import static org.junit.Assert.*;

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

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by freszczypior on 2017-11-28.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegisterUserTest extends AcceptanceTest{

    @Autowired
    private RegisterUserCommand command;

    @Autowired
    private CommandGateway gateway;

    @Autowired
    private UserFinder userFinder;

    @Test
    public void shouldRegisterUser(){
        //given
        createRegisterUserCommand("Batman", "password", "password");
        //then
        DetailedUserDto detailedUserDto = userFinder.getUserDetails(1);
        assertEquals(Integer.valueOf(1), detailedUserDto.getId());
        assertEquals("Batman", detailedUserDto.getLogin());
        assertEquals(new HashSet<>(Arrays.asList(Role.STANDARD)), detailedUserDto.getRoles());
    }
    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToRegisterUserTwoTimes(){
        //given
        createRegisterUserCommand("Batman", "password", "password");
        createRegisterUserCommand("Batman", "password", "password");
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToRegisterUserWithWrongLogin(){
        //given
        createRegisterUserCommand("Batman%%!!", "password", "password");
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToRegisterUserWithDifferentPasswords(){
        //given
        createRegisterUserCommand("Batman", "password1", "password2");
    }

    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToRegisterUserWithToShortPassword(){
        //given
        createRegisterUserCommand("Batman", "pass", "pass");
    }

    private void createRegisterUserCommand(String login, String password, String newPassword) {
        command.setLogin(login);
        command.setPassword(password);
        command.setRepeatedPassword(newPassword);
        gateway.execute(command);
    }
}
