package pl.com.bottega.hrs.infrastructure.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.users.CurrentUser;

/**
 * Created by freszczypior on 2017-12-10.
 */
@Component
@Aspect
public class SecurityAspect {

    private CurrentUser currentUser;

    public SecurityAspect(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    // porada przed wykonaniem jakiejś metody
    @Before("@within(secured)")
    public void checkSecurity(Secured secured) {
        if (!currentUser.isAuthenticated(secured.roles())) {
            throw new SecurityException();
        }
    }

    @Before("@annotation(secured)")
    public void checkMethodSecurity(Secured secured){
        checkSecurity(secured);
    }
}
