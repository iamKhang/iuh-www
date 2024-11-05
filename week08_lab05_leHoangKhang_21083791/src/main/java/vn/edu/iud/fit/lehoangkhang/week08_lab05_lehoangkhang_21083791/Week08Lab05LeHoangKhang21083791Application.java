package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillType;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Address;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Skill;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.AddressRepository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.CandidateRepository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.SkillRepository;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class Week08Lab05LeHoangKhang21083791Application {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private SkillRepository skillRepository;

    public static void main(String[] args) {
        SpringApplication.run(Week08Lab05LeHoangKhang21083791Application.class, args);

    }
    @Bean
    CommandLineRunner initData() {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            // Đọc file JSON cho Candidate và Address
            TypeReference<List<Candidate>> candidateTypeReference = new TypeReference<List<Candidate>>() {};
            TypeReference<List<Address>> addressTypeReference = new TypeReference<List<Address>>() {};
            InputStream candidateInputStream = getClass().getResourceAsStream("/scripts-data/candidate.json");
            InputStream addressInputStream = getClass().getResourceAsStream("/scripts-data/address.json");

            try {
                // Lấy danh sách ứng viên và địa chỉ từ các file JSON
                List<Candidate> candidates = mapper.readValue(candidateInputStream, candidateTypeReference);
                List<Address> addresses = mapper.readValue(addressInputStream, addressTypeReference);

                // Kiểm tra xem số lượng Candidate và Address có khớp nhau không
                if (candidates.size() > addresses.size()) {
                    System.out.println("Số lượng ứng viên và địa chỉ không khớp nhau!");
                    return;
                }

                // Lặp qua các ứng viên và gán địa chỉ tương ứng
                for (int i = 0; i < candidates.size(); i++) {
                    Candidate candidate = candidates.get(i);
                    Address address = addresses.get(i);

                    // Đặt ID là null để tránh lỗi trùng lặp ID
                    candidate.setId(null);
                    address.setId(null);

                    candidate.setAddress(address);

                    // Lưu ứng viên
                    candidateRepository.save(candidate);
                }
                System.out.println("All candidates and addresses from JSON saved successfully!");

            } catch (Exception e) {
                System.out.println("Failed to save candidates and addresses: " + e.getMessage());
                e.printStackTrace();
            }

            // Kiểm tra và thêm dữ liệu cho Skill nếu chưa tồn tại
            if (skillRepository.count() == 0) {
                List<Skill> skills = List.of(
                        new Skill(null, "Communication", "Ability to communicate effectively", SkillType.SOFT_SKILL),
                        new Skill(null, "Java Programming", "Expertise in Java programming", SkillType.TECHNICAL_SKILL),
                        new Skill(null, "Teamwork", "Ability to work effectively in a team", SkillType.SOFT_SKILL),
                        new Skill(null, "Project Management", "Experience managing projects", SkillType.UNSPECIFIC),
                        new Skill(null, "Problem Solving", "Ability to solve complex problems", SkillType.SOFT_SKILL),
                        new Skill(null, "Python Programming", "Expertise in Python programming", SkillType.TECHNICAL_SKILL),
                        new Skill(null, "Critical Thinking", "Ability to think critically and analytically", SkillType.SOFT_SKILL),
                        new Skill(null, "Data Analysis", "Experience in analyzing data", SkillType.TECHNICAL_SKILL),
                        new Skill(null, "Time Management", "Ability to manage time effectively", SkillType.SOFT_SKILL),
                        new Skill(null, "Leadership", "Ability to lead teams and projects", SkillType.SOFT_SKILL),
                        new Skill(null, "SQL", "Expertise in SQL and database management", SkillType.TECHNICAL_SKILL),
                        new Skill(null, "Creativity", "Ability to think creatively", SkillType.SOFT_SKILL),
                        new Skill(null, "Machine Learning", "Experience with machine learning techniques", SkillType.TECHNICAL_SKILL),
                        new Skill(null, "Adaptability", "Ability to adapt to changing environments", SkillType.SOFT_SKILL),
                        new Skill(null, "Cloud Computing", "Experience with cloud platforms such as AWS, Azure", SkillType.TECHNICAL_SKILL),
                        new Skill(null, "Negotiation", "Skill in negotiating and reaching agreements", SkillType.SOFT_SKILL),
                        new Skill(null, "Networking", "Ability to build professional relationships", SkillType.UNSPECIFIC),
                        new Skill(null, "JavaScript", "Expertise in JavaScript programming", SkillType.TECHNICAL_SKILL),
                        new Skill(null, "Customer Service", "Experience in customer service", SkillType.SOFT_SKILL),
                        new Skill(null, "Public Speaking", "Ability to speak in public confidently", SkillType.SOFT_SKILL)
                );

                try {
                    for (Skill skill : skills) {
                        skillRepository.save(skill);
                        System.out.println("Saved skill: " + skill.getSkillName());
                    }
                    System.out.println("All skills saved successfully!");
                } catch (Exception e) {
                    System.out.println("Failed to save skills: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Skills already exist in the database. Skipping skill initialization.");
            }
        };
    }




}
