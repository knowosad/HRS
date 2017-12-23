package pl.com.bottega.hrs.application.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.model.Salary;

import java.time.LocalDate;

@Getter
@Setter
public class SalaryDto {

    private LocalDate fromDate, toDate;

    private Integer salary;

    public SalaryDto(Salary salary) {
        this.fromDate = salary.getFromDate();
        this.toDate = salary.getToDate();
        this.salary = salary.getValue();
    }
}
