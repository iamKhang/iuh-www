package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    private int id;
    private String skillName;
    private String description;
    private String field;

    public Skill(int id) {
        this.id = id;
    }
}

