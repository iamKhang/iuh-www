package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillType;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.CandidateRepository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.CandidateSkillRepository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.JobSkillRepository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @GetMapping
    public String showStatistics(Model model) {
        // 1. Top 10 Kỹ Năng Theo Mỗi Loại
        Map<SkillType, List<Map<String, Object>>> topSkills = new HashMap<>();
        for (SkillType type : SkillType.values()) {
            List<Object[]> results = jobSkillRepository.findTopSkillsByType(type, PageRequest.of(0, 10));
            List<Map<String, Object>> skills = results.stream()
                    .map(record -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("skillName", record[0]);
                        map.put("count", record[1]);
                        return map;
                    })
                    .collect(Collectors.toList());
            topSkills.put(type, skills);
        }
        System.out.println(topSkills);
        model.addAttribute("topSkills", topSkills);

        List<Object[]> results = candidateRepository.countCandidatesByAgeGroup();

        // Xử lý kết quả trả về thành một Map với các nhóm tuổi và số lượng ứng viên
        Map<String, Long> ageData = new HashMap<>();
        for (Object[] result : results) {
            String ageGroup = (String) result[0];
            Long count = ((Number) result[1]).longValue(); // Đảm bảo kiểu dữ liệu là Long
            ageData.put(ageGroup, count);
        }

        model.addAttribute("ageData", ageData);

         // Lấy thống kê giới tính
        List<Object[]> genderStatistics = candidateRepository.countCandidatesByGender();

        // Chuyển đổi dữ liệu để hiển thị
        Map<String, Long> genderCounts = genderStatistics.stream()
                .collect(Collectors.toMap(
                        record -> {
                            Boolean genderBoolean = (Boolean) record[0];
                            return genderBoolean != null ? (genderBoolean ? "Nam" : "Nữ") : "Khác"; // Điều chỉnh tên giới tính dựa trên giá trị Boolean
                        },
                        record -> (Long) record[1]
                ));

        model.addAttribute("genderCounts", genderCounts);


        return "statistic";
    }
}
