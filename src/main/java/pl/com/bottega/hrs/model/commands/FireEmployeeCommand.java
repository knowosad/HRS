package pl.com.bottega.hrs.model.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FireEmployeeCommand implements Command  {

    private int empNo;

    public void validate(ValidationErrors errors){
        validatePresence(errors,"empNo", empNo);
    }
}