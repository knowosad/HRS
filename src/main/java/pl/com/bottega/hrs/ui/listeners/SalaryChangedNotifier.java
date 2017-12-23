package pl.com.bottega.hrs.ui.listeners;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.events.SalaryChangedEvent;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;



/**
 * Created by freszczypior on 2017-12-10.
 */
@Component
@ConditionalOnProperty(name = "hrs.notificationsEnabled", havingValue = "true")
//@ConditionalOnProperty(name = "hrs.notificationsEnabled", havingValue = "true") pozwala zarządzać działaniem lub nie tej funkcjonalności,
// na podstawie tego co mamy w application.properities / hrs.notificationsEnabled=false;
// na tej samej zasadzie mamy to czy korzystamy z jpa czy csv
public class SalaryChangedNotifier {

    private EmployeeRepository repository;

    public SalaryChangedNotifier(EmployeeRepository repository) {
        this.repository = repository;
    }

    //dla loggera trzeba importować pakiet import org.apache.log4j.Logger;
    //@EventListener  //ten wykona się w tej samej transakcji
    @TransactionalEventListener //ten wykona się dopiero po zakomitowaniu transakcji
    @Async
    public void salaryChanged(SalaryChangedEvent event) {
        Logger.getLogger(SalaryChangedNotifier.class).info("Starting email sending");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Employee employee = repository.get(event.getEmpNo());
        Logger.getLogger(SalaryChangedNotifier.class).info("Sending email to " + employee.getLastName());
    }


}
