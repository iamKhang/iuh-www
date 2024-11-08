package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillLevel;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;
    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String moreInfo;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;
}