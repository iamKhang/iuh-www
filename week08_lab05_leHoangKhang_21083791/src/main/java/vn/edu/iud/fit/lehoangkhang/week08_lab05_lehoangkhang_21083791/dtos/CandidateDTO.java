package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateDTO {
    private Long id;
    private String fullName;
    private String phone;
    private boolean gender;

    private String email;
    private String dob;
    private AddressDTO address;
    private List<CandidateSkillDTO> candidateSkills;
    private List<ExperienceDTO> experiences;
}