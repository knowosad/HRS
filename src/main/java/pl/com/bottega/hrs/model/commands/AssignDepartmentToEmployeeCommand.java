package pl.com.bottega.hrs.model.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignDepartmentToEmployeeCommand implements Command  {

    private Integer empNo;
    private String deptNo;

    public void validate(ValidationErrors errors){
        validatePresence(errors,"empNo", empNo);
        validatePresence(errors,"deptNo", deptNo);
    }
}
