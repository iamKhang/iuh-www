package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.JobType;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.ProgramSkillType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String name;
    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String description;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobSkill> jobSkills;
    private LocalDate deadline;

    private boolean active;
    @Column(name = "number_of_applicants")
    private int numberOfApplicants;
    private boolean negotiable;
    @Column(name = "salary_from")
    private double salaryFrom;
    @Column(name = "salary_to")
    private double salaryTo;
    @Column(name = "required_experience_years")
    private int requiredExperienceYears;
    @ElementCollection
    @CollectionTable(name = "required_skills", joinColumns = @JoinColumn(name = "job_id"))
    private Set<ProgramSkillType> requiredSkills;
    @Enumerated(EnumType.STRING)
    private JobType type;

}