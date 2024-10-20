package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobSkill {
    private int jobId;
    private int skillId;
    private int levelRequired;

    public JobSkill(int jobId, int skillId) {
        this.jobId = jobId;
        this.skillId = skillId;
    }
}
