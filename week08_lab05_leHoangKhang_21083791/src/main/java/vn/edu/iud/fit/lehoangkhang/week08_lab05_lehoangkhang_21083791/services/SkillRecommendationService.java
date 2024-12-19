package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.DenseInstance;
import weka.core.Attribute;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.*;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SkillRecommendationService {

    @Autowired
    private JobSkillRepository jobSkillRepository;
    
    @Autowired
    private SkillRepository skillRepository;

    public List<SkillRecommendation> getRecommendedSkills(Candidate candidate) {
        try {
            // 1. Chuẩn bị dữ liệu training
            ArrayList<Attribute> attributes = prepareAttributes();
            Instances trainingData = prepareTrainingData(attributes);
            
            // 2. Train model Random Forest
            RandomForest model = new RandomForest();
            model.buildClassifier(trainingData);

            // 3. Lấy các kỹ năng hiện tại của ứng viên
            Set<Long> candidateSkillIds = candidate.getCandidateSkills().stream()
                .map(cs -> cs.getSkill().getId())
                .collect(Collectors.toSet());

            // 4. Dự đoán và tạo recommendations cho các kỹ năng còn lại
            List<SkillRecommendation> recommendations = new ArrayList<>();
            List<Skill> allSkills = skillRepository.findAll();
            
            for (Skill skill : allSkills) {
                if (!candidateSkillIds.contains(skill.getId())) {
                    double[] instanceValue = createInstanceValue(candidateSkillIds, skill);
                    DenseInstance instance = new DenseInstance(1.0, instanceValue);
                    instance.setDataset(trainingData);
                    
                    // Dự đoán xác suất thành công
                    double[] distribution = model.distributionForInstance(instance);
                    double confidenceScore = distribution[1]; // Xác suất cho class positive
                    
                    if (confidenceScore > 0.5) { // Chỉ recommend kỹ năng có độ tin cậy > 50%
                        String reasoning = generateReasoning(skill, confidenceScore);
                        recommendations.add(new SkillRecommendation(skill, confidenceScore, reasoning));
                    }
                }
            }

            // Sắp xếp theo độ tin cậy giảm dần
            recommendations.sort((r1, r2) -> 
                Double.compare(r2.getConfidenceScore(), r1.getConfidenceScore()));
            
            return recommendations;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private ArrayList<Attribute> prepareAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        
        // Thêm attributes cho mỗi kỹ năng có thể có
        List<Skill> allSkills = skillRepository.findAll();
        for (Skill skill : allSkills) {
            attributes.add(new Attribute("skill_" + skill.getId()));
        }
        
        // Thêm attribute cho kết quả (success/failure)
        ArrayList<String> classValues = new ArrayList<>();
        classValues.add("failure");
        classValues.add("success");
        attributes.add(new Attribute("class", classValues));
        
        return attributes;
    }

    private Instances prepareTrainingData(ArrayList<Attribute> attributes) {
        Instances trainingData = new Instances("SkillRecommendation", attributes, 0);
        trainingData.setClassIndex(trainingData.numAttributes() - 1);

        // Lấy tất cả JobSkill combinations và kết quả tuyển dụng
        List<Job> allJobs = jobSkillRepository.findAll().stream()
            .map(JobSkill::getJob)
            .distinct()
            .collect(Collectors.toList());

        for (Job job : allJobs) {
            // Tạo instance cho mỗi job
            Set<Long> jobSkillIds = job.getJobSkills().stream()
                .map(js -> js.getSkill().getId())
                .collect(Collectors.toSet());

            double[] values = new double[attributes.size()];
            
            // Set giá trị 1 cho các kỹ năng có trong job
            for (int i = 0; i < attributes.size() - 1; i++) {
                Attribute att = attributes.get(i);
                Long skillId = Long.parseLong(att.name().substring(6)); // Extract skill_id
                values[i] = jobSkillIds.contains(skillId) ? 1.0 : 0.0;
            }
            
            // Set class value (success/failure) dựa trên số lượng ứng viên đã apply
            values[attributes.size() - 1] = job.getNumberOfApplicants() > 0 ? 1.0 : 0.0;
            
            trainingData.add(new DenseInstance(1.0, values));
        }

        return trainingData;
    }

    private double[] createInstanceValue(Set<Long> candidateSkillIds, Skill newSkill) {
        List<Skill> allSkills = skillRepository.findAll();
        double[] values = new double[allSkills.size() + 1]; // +1 for class attribute
        
        for (int i = 0; i < allSkills.size(); i++) {
            Skill skill = allSkills.get(i);
            if (candidateSkillIds.contains(skill.getId()) || skill.getId().equals(newSkill.getId())) {
                values[i] = 1.0;
            }
        }
        
        return values;
    }

    private String generateReasoning(Skill skill, double confidence) {
        return String.format(
            "Kỹ năng %s được đề xuất với độ tin cậy %.2f%% dựa trên phân tích " +
            "các công việc tương tự và tỷ lệ thành công của ứng viên có kỹ năng này.",
            skill.getSkillName(),
            confidence * 100
        );
    }
} 