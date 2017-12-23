package pl.com.bottega.hrs.model.commands;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Gender;

import java.time.LocalDate;

@Getter
@Setter
public class AddEmployeeCommand implements Command {

    private String firstName, lastName;

    private LocalDate birthDate;

    private Address address;

    private Gender gender;

    private Integer salary;

    private String title;

    private String deptNo;

    public void validate(ValidationErrors errors) {
        validatePresence(errors, "firstName", firstName);
        validatePresence(errors, "lastName", lastName);
        validatePresence(errors, "birthDate", birthDate);
        validatePresence(errors, "address.street", address.getStreet());
        validatePresence(errors, "address.city", address.getCity());
        validatePresence(errors, "gender", gender);
        validatePresence(errors, "salary", salary);
        validatePresence(errors, "title", title);
        validatePresence(errors, "deptNo", deptNo);

        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            errors.add("birthDate", "mast be in the past");
        }
    }
}
