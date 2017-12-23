package pl.com.bottega.hrs.ui;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by freszczypior on 2017-12-10.
 */
@Component
public class Test {

    @Scheduled(cron = "0 43 15 * * *")
    public void test(){
        System.out.println("xxxxxxx");
    }

}
