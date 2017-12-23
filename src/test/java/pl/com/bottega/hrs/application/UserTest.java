package pl.com.bottega.hrs.application;

import org.junit.Test;
import static org.junit.Assert.*;

import pl.com.bottega.hrs.application.users.Role;
import pl.com.bottega.hrs.application.users.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by freszczypior on 2017-11-30.
 */
public class UserTest {

    private static Set<Role> roles = new HashSet<>();
    static {
        roles.add(Role.ADMIN);
        roles.add(Role.STANDARD);
    }
    private final User sut = new User(
            1,
            "sutLogin",
            "sutPassword",
            roles
    );

    @Test
    public void shouldUpdateUser(){
        //when
        Set<Role> newRoles = new HashSet<>();
        newRoles.add(Role.STANDARD);
        sut.updateProfile(null, "newSutPassword", newRoles);

        //then
        assertEquals(Integer.valueOf(1), sut.getId());
        assertEquals("sutLogin", sut.getLogin());
        assertEquals("newSutPassword", sut.getPassword());
        assertEquals(Arrays.asList(Role.STANDARD), sut.getRoles().stream().collect(Collectors.toList()));
    }

}
