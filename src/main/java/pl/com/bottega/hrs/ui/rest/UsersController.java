package pl.com.bottega.hrs.ui.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.bottega.hrs.application.CommandGateway;
import pl.com.bottega.hrs.application.dtos.UserDto;
import pl.com.bottega.hrs.application.users.CurrentUser;
import pl.com.bottega.hrs.application.users.commands.LoginCommand;
import pl.com.bottega.hrs.application.UserFinder;
import pl.com.bottega.hrs.application.dtos.DetailedUserDto;
import pl.com.bottega.hrs.application.users.commands.RegisterUserCommand;
import pl.com.bottega.hrs.application.users.commands.UpdateUserCommand;
import pl.com.bottega.hrs.infrastructure.aspects.Secured;

import java.util.Optional;

/**
 * Created by freszczypior on 2017-11-28.
 */

@RestController
@RequestMapping("/users")
public class UsersController {

    private UserFinder userFinder;

    private CommandGateway gateway;

    private CurrentUser currentUser;

    public UsersController(UserFinder userFinder, CommandGateway gateway, CurrentUser currentUser) {
        this.userFinder = userFinder;
        this.gateway = gateway;
        this.currentUser = currentUser;
    }

    @PostMapping
    public void register(@RequestBody RegisterUserCommand command) {
        gateway.execute(command);
    }

    @PutMapping("/{id}")
    public DetailedUserDto update(@PathVariable Integer id, @RequestBody UpdateUserCommand command) {
        command.setId(id);
        gateway.execute(command);
        return userFinder.getUserDetails(id);
    }

    @PutMapping("/session")
    public void login(@RequestBody LoginCommand command) {
        gateway.execute(command);
    }

    @DeleteMapping("/session")
    public void logout() {
        currentUser.logout();
    }

    //testowy request, aby spr czy jest currentUser
    @GetMapping("/current")
    //@Secured
    public ResponseEntity<UserDto> getCurrent() {
        Optional<UserDto> userDtoOptional = currentUser.getUserInfo();
        if (userDtoOptional.isPresent())
            return new ResponseEntity<>(userDtoOptional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
