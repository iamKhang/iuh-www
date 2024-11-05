package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillType;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;
    private String skillDescription;

    @Enumerated(EnumType.STRING)
    private SkillType type;

    @OneToMany(mappedBy = "skill")
    private List<JobSkill> jobSkills;

    @OneToMany(mappedBy = "skill")
    private List<CandidateSkill> candidateSkills;

    public Skill(Long id, String skillName, String skillDescription, SkillType type) {
        this.id = id;
        this.skillName = skillName;
        this.skillDescription = skillDescription;
        this.type = type;
    }
}