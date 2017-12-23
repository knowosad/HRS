package pl.com.bottega.hrs.model.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddDepartmentCommand implements Command {

    private String number, name;

    public void validate(ValidationErrors errors){
        validatePresence(errors,"number", number);
        validatePresence(errors,"name", name);
    }

}
