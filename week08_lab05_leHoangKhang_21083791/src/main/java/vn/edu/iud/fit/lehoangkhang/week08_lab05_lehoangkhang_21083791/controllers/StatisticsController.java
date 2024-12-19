package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.JobType;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillType;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.CandidateRepository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.CandidateSkillRepository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.JobRepository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.JobSkillRepository;

import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    @Autowired
    private JobRepository jobRepository; // Thêm dòng này

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

        // 2. Thống Kê Theo Độ Tuổi Ứng Viên
        List<Object[]> results = candidateRepository.countCandidatesByAgeGroup();

        // Xử lý kết quả trả về thành một Map với các nhóm tuổi và số lượng ứng viên
        Map<String, Long> ageData = new HashMap<>();
        for (Object[] result : results) {
            String ageGroup = (String) result[0];
            Long count = ((Number) result[1]).longValue(); // Đảm bảo kiểu dữ liệu là Long
            ageData.put(ageGroup, count);
        }

        model.addAttribute("ageData", ageData);

        // 3. Thống Kê Giới Tính Ứng Viên
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

        // 4. Thống Kê Dựa Trên JobType - Top 14
        List<Object[]> jobTypeResults = jobRepository.countTopJobTypes();

        // Giới hạn top 14
        List<Object[]> topJobTypeResults = jobTypeResults.stream().limit(14).collect(Collectors.toList());

        // Xử lý kết quả trả về thành một Map với JobType và số lượng công việc
        Map<String, Long> jobTypeData = new HashMap<>();
        for (Object[] result : topJobTypeResults) {
            JobType jobType = (JobType) result[0];
            Long count = ((Number) result[1]).longValue();
            jobTypeData.put(jobType.name(), count);
        }

        model.addAttribute("jobTypeData", jobTypeData);

        // Thêm thống kê xu hướng tuyển dụng theo thời gian
        List<Object[]> jobPostings = jobRepository.findAll().stream()
            .sorted((j1, j2) -> j1.getStartDate().compareTo(j2.getStartDate()))
            .collect(Collectors.groupingBy(
                job -> job.getStartDate().format(DateTimeFormatter.ofPattern("MM/yyyy")),
                LinkedHashMap::new,
                Collectors.counting()
            ))
            .entrySet().stream()
            .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
            .collect(Collectors.toList());

        model.addAttribute("jobPostingTrends", jobPostings);
        
        return "statistic";
    }
}
