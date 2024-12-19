package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobDTO {
    private Long id;
    private String name;
    private String description;
    private Long company;
    private List<JobSkillDTO> jobSkills;
    private String deadline;
    private boolean active;
    private int numberOfApplicants;
    private boolean negotiable;
    private double salaryFrom;
    private double salaryTo;
    private int requiredExperienceYears;
    private String type;
}