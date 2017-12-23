package pl.com.bottega.hrs.model.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeSalaryCommand implements Command  {

    private Integer empNo;
    private Integer amount;

    public void validate(ValidationErrors errors){
        validatePresence(errors,"empNo", empNo);
        validatePresence(errors,"amount", amount);
    }
}
