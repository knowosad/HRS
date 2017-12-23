package pl.com.bottega.hrs.application.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.model.DepartmentAssignment;

import java.time.LocalDate;

@Getter
@Setter
public class DepartmentDto {

    private LocalDate fromDate, toDate;

    private String deptNo;

    public DepartmentDto(DepartmentAssignment assignment) {
        this.fromDate = assignment.getFromDate();
        this.toDate = assignment.getToDate();
        this.deptNo = assignment.getDepartment().getDeptNo();
    }
}
