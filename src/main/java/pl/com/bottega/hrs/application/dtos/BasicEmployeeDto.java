package pl.com.bottega.hrs.application.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicEmployeeDto {

    private Integer empNo;

    private String firstName, lastName;

    public BasicEmployeeDto(Integer empNo, String firstName, String lastName) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
