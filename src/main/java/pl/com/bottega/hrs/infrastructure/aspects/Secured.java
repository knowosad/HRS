package pl.com.bottega.hrs.infrastructure.aspects;

import pl.com.bottega.hrs.application.users.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by freszczypior on 2017-12-10.
 */
@Retention(value = RetentionPolicy.RUNTIME)// czy tylko przy kompilacji czy zawsze w runtime
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Secured {

    Role[] roles() default {}; //do adnotacji secure można użyć paramatru roles i domyślnie jest on pustą tablicą



}
