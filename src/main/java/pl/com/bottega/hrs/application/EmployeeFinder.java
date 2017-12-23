package pl.com.bottega.hrs.application;

import pl.com.bottega.hrs.application.dtos.DetailedEmployeeDto;

public interface EmployeeFinder {

    EmployeeSearchResults search(EmployeeSearchCriteria criteria);

    DetailedEmployeeDto getEmployeeDetails(Integer empNo);

}
