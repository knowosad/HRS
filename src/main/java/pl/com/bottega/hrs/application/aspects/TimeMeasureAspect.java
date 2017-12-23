package pl.com.bottega.hrs.application.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;



/**
 * Created by freszczypior on 2017-12-10.
 */
@Component
@Aspect
public class TimeMeasureAspect {

    // nie muszę nigdzie umieszczać adnotacji @TimeMeasureAspect bo w tej wersji aspect będzie
    // odpalany przy każdym wykonaniu metody handle z klasy Handler
    @Around("execution(* pl.com.bottega.hrs.application.handlers.Handler.handle(..))")
    public Object timeMeasure(ProceedingJoinPoint pjp) throws Throwable {

        long start=System.currentTimeMillis();

        Object retval = pjp.proceed();

        long stop=System.currentTimeMillis();

        String msg = String.format("Execution %s took %s ms", pjp.getSignature(), (stop-start));
        Logger.getLogger(TimeMeasureAspect.class).info(msg);
        return retval;
    }
}
