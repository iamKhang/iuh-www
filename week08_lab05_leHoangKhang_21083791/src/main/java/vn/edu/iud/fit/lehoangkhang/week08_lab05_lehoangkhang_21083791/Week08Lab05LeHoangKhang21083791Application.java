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
import org.springframework.core.io.ClassPathResource;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.dtos.*;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.AccountRole;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillLevel;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.SkillType;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.*;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.*;

import java.io.InputStream;
import java.time.LocalDate;
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
    @Autowired
    private JobSkillRepository jobSkillRepository;
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CandidateApplyJobRepository candidateApplyJobRepository;

    public static void main(String[] args) {
        SpringApplication.run(Week08Lab05LeHoangKhang21083791Application.class, args);

    }

    @Bean
    CommandLineRunner initData() {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();

            // 1. Khởi tạo Skills
            if (skillRepository.count() == 0) {
                try {
                    InputStream skillsStream = new ClassPathResource("scripts-data/skills.json").getInputStream();
                    List<SkillDTO> skillsDTO = mapper.readValue(skillsStream, new TypeReference<List<SkillDTO>>() {
                    });
                    for (SkillDTO skillDTO : skillsDTO) {
                        Skill skill = new Skill();
                        skill.setSkillName(skillDTO.getSkillName());
                        skill.setSkillDescription(skillDTO.getSkillDescription());
                        skill.setType(SkillType.valueOf(skillDTO.getType()));
                        skillRepository.save(skill);
                    }
                    System.out.println("Skills data initialized.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 2. Khởi tạo Companies và Addresses
            // 2. Khởi tạo Companies và Addresses
            if (companyRepository.count() == 0) {
                try {
                    InputStream companiesStream = new ClassPathResource("scripts-data/companies.json").getInputStream();
                    List<CompanyDTO> companiesDTO = mapper.readValue(companiesStream, new TypeReference<List<CompanyDTO>>() {
                    });
                    for (CompanyDTO companyDTO : companiesDTO) {
                        // Tạo Address
                        Address address = new Address();
                        address.setCountry(companyDTO.getAddress().getCountry());
                        address.setCity(companyDTO.getAddress().getCity());
                        address.setStreet(companyDTO.getAddress().getStreet());
                        address.setNumber(companyDTO.getAddress().getNumber());
                        address.setZipcode(companyDTO.getAddress().getZipcode());

                        // Tạo Company và thiết lập mối quan hệ
                        Company company = new Company(companyDTO.getName(), companyDTO.getAbout(),
                                companyDTO.getEmail(), companyDTO.getPhone(), companyDTO.getWebUrl());
                        company.setAddress(address);
                        address.setCompany(company); // Thiết lập mối quan hệ ngược lại nếu cần

                        // Lưu Company (Address sẽ được lưu tự động)
                        companyRepository.save(company);
                    }
                    System.out.println("Companies and Addresses data initialized.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            // 3. Khởi tạo Jobs và JobSkills
            if (jobRepository.count() == 0) {
                try {
                    InputStream jobsStream = new ClassPathResource("scripts-data/jobs.json").getInputStream();
                    List<JobDTO> jobsDTO = mapper.readValue(jobsStream, new TypeReference<List<JobDTO>>() {
                    });
                    for (JobDTO jobDTO : jobsDTO) {
                        // Lấy Company
                        Company company = companyRepository.findById(jobDTO.getCompany())
                                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + jobDTO.getCompany()));

                        // Lưu Job
                        Job job = new Job();
                        job.setName(jobDTO.getName());
                        job.setDescription(jobDTO.getDescription());
                        job.setCompany(company);
                        job.setDeadline(LocalDate.parse(jobDTO.getDeadline()));
                        job.setActive(jobDTO.isActive());
                        job.setNumberOfApplicants(jobDTO.getNumberOfApplicants());
                        job.setNegotiable(jobDTO.isNegotiable());
                        job.setSalaryFrom(jobDTO.getSalaryFrom());
                        job.setSalaryTo(jobDTO.getSalaryTo());
                        job.setRequiredExperienceYears(jobDTO.getRequiredExperienceYears());

                        List<JobSkill> jobSkills = new ArrayList<>();
                        // Lưu JobSkills
                        for (JobSkillDTO jobSkillDTO : jobDTO.getJobSkills()) {
                            Skill skill = skillRepository.findById(jobSkillDTO.getSkill_id())
                                    .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + jobSkillDTO.getSkill_id()));
                            JobSkill jobSkill = new JobSkill();
                            jobSkill.setSkillLevel(SkillLevel.valueOf(jobSkillDTO.getSkillLevel()));
                            jobSkill.setMoreInfo(jobSkillDTO.getMoreInfo());
                            jobSkill.setJob(job);
                            jobSkill.setSkill(skill);
                            jobSkills.add(jobSkill);
                        }
                        job.setJobSkills(jobSkills);

                        // Lưu Job cùng với JobSkills
                        jobRepository.save(job);
                    }
                    System.out.println("Jobs and JobSkills data initialized.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 4. Khởi tạo Candidates, Addresses, CandidateSkills và Experiences
            if (candidateRepository.count() == 0) {
                try {
                    InputStream candidatesStream = new ClassPathResource("scripts-data/candidates.json").getInputStream();
                    List<CandidateDTO> candidatesDTO = mapper.readValue(candidatesStream, new TypeReference<List<CandidateDTO>>() {
                    });
                    for (CandidateDTO candidateDTO : candidatesDTO) {

                        // Lưu Address
                        Address address = new Address();
                        address.setCountry(candidateDTO.getAddress().getCountry());
                        address.setCity(candidateDTO.getAddress().getCity());
                        address.setStreet(candidateDTO.getAddress().getStreet());
                        address.setNumber(candidateDTO.getAddress().getNumber());
                        address.setZipcode(candidateDTO.getAddress().getZipcode());

                        // Lưu Candidate
                        Candidate candidate = new Candidate();
                        candidate.setFullName(candidateDTO.getFullName());
                        candidate.setPhone(candidateDTO.getPhone());
                        candidate.setEmail(candidateDTO.getEmail());
                        candidate.setDob(LocalDate.parse(candidateDTO.getDob()));
                        candidate.setAddress(address);
                        candidate.setGender(candidateDTO.isGender());

                        // Lưu CandidateSkills
                        List<CandidateSkill> candidateSkills = new ArrayList<>();
                        if (candidateDTO.getCandidateSkills() != null) {
                            for (CandidateSkillDTO candidateSkillDTO : candidateDTO.getCandidateSkills()) {
                                Skill skill = skillRepository.findById(candidateSkillDTO.getSkill_id())
                                        .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + candidateSkillDTO.getSkill_id()));
                                CandidateSkill candidateSkill = new CandidateSkill();
                                candidateSkill.setSkillLevel(SkillLevel.valueOf(candidateSkillDTO.getSkillLevel()));
                                candidateSkill.setMoreInfo(candidateSkillDTO.getMoreInfo());
                                candidateSkill.setCandidate(candidate);
                                candidateSkill.setSkill(skill);
                                candidateSkills.add(candidateSkill);
                            }
                            candidate.setCandidateSkills(candidateSkills);
                        }

                        // Lưu Experiences
                        if (candidateDTO.getExperiences() != null) {
                            List<Experience> experiences = new ArrayList<>();
                            for (ExperienceDTO experienceDTO : candidateDTO.getExperiences()) {
                                Experience experience = new Experience();
                                experience.setFromDate(LocalDate.parse(experienceDTO.getFromDate()));
                                experience.setToDate(LocalDate.parse(experienceDTO.getToDate()));
                                experience.setCompanyName(experienceDTO.getCompanyName());
                                experience.setRole(experienceDTO.getRole());
                                experience.setWorkDescription(experienceDTO.getWorkDescription());
                                experience.setCandidate(candidate);
                                experiences.add(experience);
                            }
                            candidate.setExperiences(experiences);
                        }

                        Account account = new Account();
                        account.setCandidate(candidate);
                        account.setPassword("$2a$10$ymdWR.EG0gCL9YtacQjmteGTCthfMdVAB8FZiha0s2N5bhxhx4wMa");
                        account.setRole(AccountRole.CANDIDATE);

                        accountRepository.save(account);
                    }
                    System.out.println("Candidates, Addresses, CandidateSkills và Experiences data initialized.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            // 6. Khởi tạo CandidateApplyJob
            if (candidateApplyJobRepository.count() == 0) {
                try {
                    InputStream applyStream = new ClassPathResource("scripts-data/apply.json").getInputStream();
                    List<ApplyDTO> applyDTOs = mapper.readValue(applyStream, new TypeReference<List<ApplyDTO>>() {
                    });
                    for (ApplyDTO applyDTO : applyDTOs) {
                        Candidate candidate = candidateRepository.findById(applyDTO.getCandidate_id())
                                .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + applyDTO.getCandidate_id()));
                        Job job = jobRepository.findById(applyDTO.getJob_id())
                                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + applyDTO.getJob_id()));
                        CandidateApplyJob applyJob = new CandidateApplyJob();
                        applyJob.setCandidate(candidate);
                        applyJob.setJob(job);
                        applyJob.setApplyDate(LocalDate.parse(applyDTO.getApplyDate()));
                        candidateApplyJobRepository.save(applyJob);
                    }
                    System.out.println("CandidateApplyJob data initialized.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Dữ liệu đã được khởi tạo thành công.");
        };
    }

}
