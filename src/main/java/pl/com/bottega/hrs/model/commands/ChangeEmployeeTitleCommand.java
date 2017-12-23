package pl.com.bottega.hrs.model.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeEmployeeTitleCommand implements Command  {
    private Integer empNo;
    private String title;

    public void validate(ValidationErrors errors){
        validatePresence(errors,"empNo", empNo);
        validatePresence(errors,"title", title);
    }
}
