package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateSkill {
    private int candidateId;
    private int skillId;
    private int level;

    public CandidateSkill(int candidateId, int skillId) {
        this.candidateId = candidateId;
        this.skillId = skillId;
    }
}