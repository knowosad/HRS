package pl.com.bottega.hrs.application;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
public class EmployeeSearchCriteria {

    private Integer empNo;

    private String lastNameQuery, firstNameQuery;

    private LocalDate birthDateFrom, birthDateTo;

    private LocalDate hireDateFrom, hireDateTo;

    private Integer salaryFrom, salaryTo;

    private Collection<String> titles;

    private Collection<String> departmentNumbers;

    private int pageSize = 20;

    private int pageNumber = 1;
}
