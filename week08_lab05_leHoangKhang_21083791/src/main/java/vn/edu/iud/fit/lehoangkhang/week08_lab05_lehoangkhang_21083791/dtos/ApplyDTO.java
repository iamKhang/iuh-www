package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.dtos;

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
public class ApplyDTO {
    private Long id;
    private Long candidate_id;
    private Long job_id;
    private String applyDate;
}