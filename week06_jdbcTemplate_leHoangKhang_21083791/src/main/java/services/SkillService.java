package services;

import entities.Skill;
import repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;


    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public void addSkill(Skill skill) {
        skillRepository.save(skill);
    }


    public void updateSkill(Skill skill) {
        skillRepository.update(skill);
    }

    public void deleteSkill(int id) {
        skillRepository.delete(id);
    }

    public Skill getSkillById(int id) {
        return skillRepository.findAll().stream()
                .filter(skill -> skill.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
