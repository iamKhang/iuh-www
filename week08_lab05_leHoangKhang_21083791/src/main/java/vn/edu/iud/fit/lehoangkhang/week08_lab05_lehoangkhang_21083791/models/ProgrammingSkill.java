package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.ProgramSkillType;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillLevel;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "programming_skills")
public class ProgrammingSkill {
    private Long id;
    private String name;
    private ProgramSkillType programSkill;
    private SkillLevel skillLevel;
}
