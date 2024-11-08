package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Skill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.SkillRepository;

import java.util.List;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public List<Skill> getSkillsByType(String type) {
        return skillRepository.findBySkillName(type);
    }
}
