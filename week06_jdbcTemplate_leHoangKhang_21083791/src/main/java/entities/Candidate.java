package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    private int id;
    private String lastName;
    private String middleName;
    private String firstName;
    private Date dob;
    private String email;
    private String address;
    private String phone;

    public Candidate(int id) {
        this.id = id;
    }
}
