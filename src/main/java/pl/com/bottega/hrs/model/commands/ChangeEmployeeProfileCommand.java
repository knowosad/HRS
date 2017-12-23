
package pl.com.bottega.hrs.model.commands;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Gender;

import java.time.LocalDate;

@Getter
@Setter
public class ChangeEmployeeProfileCommand implements Command {

    private String firstName, lastName;

    private LocalDate birthDate;

    private Address address;

    private Gender gender;

    private Integer empNo;

    public void validate(ValidationErrors errors){
        validatePresence(errors,"firstName", firstName);
        validatePresence(errors,"lastName", lastName);
        validatePresence(errors,"birthDate", birthDate);
        validatePresence(errors,"address.street",address.getStreet());
        validatePresence(errors,"address.city", address.getCity());
        validatePresence(errors,"gender",gender);
        validatePresence(errors,"empNo",empNo);

        if(birthDate != null && birthDate.isAfter(LocalDate.now())){
            errors.add("birthDate", "mast be in the past");
        }
    }

}
