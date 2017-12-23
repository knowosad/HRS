package pl.com.bottega.hrs.application.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class DetailedEmployeeDto extends BasicEmployeeDto {

    private LocalDate birthDate, hireDate;

    private Gender gender;

    private Address address;

    private Optional<Integer> salary;

    private Optional<String> title;

    private List<String> departmentNumbers;

    private List<SalaryDto> salaryHistory;

    private List<DepartmentDto> departmentHistory;

    private List<TitleDto> titleHistory;

    public DetailedEmployeeDto(Employee employee) {
        super(employee.getEmpNo(), employee.getFirstName(), employee.getLastName());
        this.birthDate = employee.getBirthDate();
        this.hireDate = employee.getHireDate();
        this.address = employee.getAddress();
        this.salary = employee.getCurrentSalary().map(Salary::getValue);
        this.title = employee.getCurrentTitle().map(Title::getName);
        this.gender = employee.getGender();
        this.departmentNumbers = employee.getCurrentDepartments().stream().
                map(Department::getDeptNo).
                collect(Collectors.toList());
        this.salaryHistory = employee.getSalaries().stream().
                map(SalaryDto::new).collect(Collectors.toList());
        this.departmentHistory = employee.getDepartmentAssignments().stream().
                map(DepartmentDto::new).collect(Collectors.toList());
        this.titleHistory = employee.getTitles().stream().
                map(TitleDto::new).collect(Collectors.toList());
    }
}
