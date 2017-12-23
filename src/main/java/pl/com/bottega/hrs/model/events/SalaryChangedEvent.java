package pl.com.bottega.hrs.model.events;

import lombok.Getter;

import java.time.LocalDate;

/**
 * Created by freszczypior on 2017-12-10.
 */

@Getter
public class SalaryChangedEvent {  //zdanie twierdzące dokonane, taki standard dla eventów

    private Integer empNo;
    private Integer newSalary;
    private LocalDate dateTime = LocalDate.now();

    public SalaryChangedEvent(Integer empNo, Integer newSalary) {
        this.empNo = empNo;
        this.newSalary = newSalary;
    }


}
