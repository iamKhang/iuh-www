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
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillLevel;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillType;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.*;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class Week08Lab05LeHoangKhang21083791Application {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobRepository jobRepository;

    public static void main(String[] args) {
        SpringApplication.run(Week08Lab05LeHoangKhang21083791Application.class, args);

    }
    @Bean
    CommandLineRunner initData() {
        return args -> {
            // Kiểm tra và thêm dữ liệu cho Address nếu chưa tồn tại
            if (candidateRepository.count()==0){
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                // Đọc file JSON cho Candidate và Address
                TypeReference<List<Candidate>> candidateTypeReference = new TypeReference<List<Candidate>>() {};
                TypeReference<List<Address>> addressTypeReference = new TypeReference<List<Address>>() {};
                InputStream candidateInputStream = getClass().getResourceAsStream("/scripts-data/candidate.json");
                InputStream addressInputStream = getClass().getResourceAsStream("/scripts-data/address.json");

                try {
                    List<Candidate> candidates = mapper.readValue(candidateInputStream, candidateTypeReference);
                    List<Address> addresses = mapper.readValue(addressInputStream, addressTypeReference);
                    if (candidates.size() > addresses.size()) {
                        System.out.println("Số lượng ứng viên và địa chỉ không khớp nhau!");
                        return;
                    }
                    for (int i = 0; i < candidates.size(); i++) {
                        Candidate candidate = candidates.get(i);
                        Address address = addresses.get(i);
                        candidate.setId(null);
                        address.setId(null);

                        candidate.setAddress(address);
                        candidateRepository.save(candidate);
                    }
                    System.out.println("All candidates and addresses from JSON saved successfully!");

                } catch (Exception e) {
                    System.out.println("Failed to save candidates and addresses: " + e.getMessage());
                    e.printStackTrace();
                }
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

            if (companyRepository.count() == 0){
                System.out.println("No company data found. Initializing company data...");
                companyRepository.saveAll(List.of(
                        new Company("Apple Inc.", "Apple là một tập đoàn công nghệ hàng đầu, nổi tiếng với iPhone và các sản phẩm sáng tạo khác.", "contact@apple.com", "+1-800-692-7753", "https://www.apple.com"),
                        new Company("Microsoft Corporation", "Microsoft cung cấp các phần mềm và dịch vụ đám mây hàng đầu thế giới.", "contact@microsoft.com", "+1-425-882-8080", "https://www.microsoft.com"),
                        new Company("Google LLC", "Google là công ty con của Alphabet Inc., nổi tiếng với công cụ tìm kiếm và các sản phẩm phần mềm khác.", "contact@google.com", "+1-650-253-0000", "https://www.google.com"),
                        new Company("Amazon.com, Inc.", "Amazon là công ty thương mại điện tử và điện toán đám mây hàng đầu.", "contact@amazon.com", "+1-888-280-4331", "https://www.amazon.com"),
                        new Company("Facebook, Inc.", "Facebook, nay là Meta, là mạng xã hội lớn nhất thế giới với các dịch vụ đa dạng như Instagram và WhatsApp.", "contact@meta.com", "+1-650-543-4800", "https://www.meta.com"),
                        new Company("Tesla, Inc.", "Tesla là công ty dẫn đầu về xe điện và các sản phẩm năng lượng sạch.", "contact@tesla.com", "+1-888-518-3752", "https://www.tesla.com"),
                        new Company("IBM", "IBM là một công ty công nghệ lâu đời, nổi tiếng với các giải pháp phần mềm doanh nghiệp và AI.", "contact@ibm.com", "+1-800-426-4968", "https://www.ibm.com"),
                        new Company("Intel Corporation", "Intel là một trong những nhà sản xuất chip bán dẫn lớn nhất thế giới.", "contact@intel.com", "+1-408-765-8080", "https://www.intel.com"),
                        new Company("Oracle Corporation", "Oracle cung cấp các giải pháp phần mềm cơ sở dữ liệu và quản lý dữ liệu cho các doanh nghiệp.", "contact@oracle.com", "+1-650-506-7000", "https://www.oracle.com"),
                        new Company("Samsung Electronics", "Samsung là tập đoàn công nghệ hàng đầu từ Hàn Quốc, nổi tiếng với các sản phẩm điện tử và bán dẫn.", "contact@samsung.com", "+82-2-2255-0114", "https://www.samsung.com")
                ));

                System.out.println("Company data initialized successfully!");
            } else {
                System.out.println("Company data already exists in the database. Skipping company data initialization.");
            }


            List<Company> companies = companyRepository.findAll();
            List<Skill> skills = skillRepository.findAll();
            if (jobRepository.count() == 0) {
                List<Job> jobs = new ArrayList<>();

                for (int i = 0; i < 30; i++) {
                    Job job = new Job();
                    job.setName("Job " + (i + 1));
                    job.setDescription("Description for Job " + (i + 1));

                    // Assign a random company from the list of companies
                    Company company = companies.get(new Random().nextInt(companies.size()));
                    job.setCompany(company);

                    // Assign random skills to the job
                    List<JobSkill> jobSkills = new ArrayList<>();
                    int numberOfSkills = new Random().nextInt(3) + 1; // Randomly choose 1 to 3 skills
                    for (int j = 0; j < numberOfSkills; j++) {
                        Skill skill = skills.get(new Random().nextInt(skills.size()));
                        JobSkill jobSkill = new JobSkill();
                        jobSkill.setSkill(skill);
                        jobSkill.setJob(job);
                        jobSkill.setSkillLevel(SkillLevel.values()[new Random().nextInt(SkillLevel.values().length)]);
                        jobSkill.setMoreInfo("Additional info about skill " + skill.getSkillName());
                        jobSkills.add(jobSkill);
                    }
                    job.setJobSkills(jobSkills);

                    jobs.add(job);
                }

                jobRepository.saveAll(jobs);
            }

        };
    }




}
