// SimilarityCalculator.java
package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.utils;

import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.CandidateSkill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Job;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.JobSkill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Skill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimilarityCalculator {

    /**
     * Tạo một vector kỹ năng cho ứng viên hoặc công việc.
     *
     * @param allSkills Danh sách tất cả các kỹ năng.
     * @param skills    Các kỹ năng của ứng viên hoặc công việc.
     * @return Mảng double đại diện cho vector kỹ năng.
     */
    public static double[] createSkillVector(List<Skill> allSkills, List<? extends Object> skills) {
        double[] vector = new double[allSkills.size()];
        Map<String, Integer> skillIndexMap = new HashMap<>();

        // Tạo bản đồ từ tên kỹ năng đến chỉ số
        for (int i = 0; i < allSkills.size(); i++) {
            skillIndexMap.put(allSkills.get(i).getSkillName(), i);
        }

        // Đánh dấu kỹ năng trong vector
        for (Object obj : skills) {
            String skillName = "";
            if (obj instanceof CandidateSkill) {
                skillName = ((CandidateSkill) obj).getSkill().getSkillName();
            } else if (obj instanceof JobSkill) {
                skillName = ((JobSkill) obj).getSkill().getSkillName();
            }
            if (skillIndexMap.containsKey(skillName)) {
                int index = skillIndexMap.get(skillName);
                vector[index] = 1.0; // Bạn có thể điều chỉnh giá trị này dựa trên mức độ kỹ năng
            }
        }

        return vector;
    }

    /**
     * Tính Cosine Similarity giữa hai vector.
     *
     * @param vectorA Vector thứ nhất.
     * @param vectorB Vector thứ hai.
     * @return Giá trị Cosine Similarity.
     */
    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for(int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }

        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
