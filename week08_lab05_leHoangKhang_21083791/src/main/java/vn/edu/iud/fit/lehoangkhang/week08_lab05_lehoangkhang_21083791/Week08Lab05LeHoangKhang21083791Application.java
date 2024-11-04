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
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Address;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Candidate;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.AddressRepository;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.CandidateRepository;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class Week08Lab05LeHoangKhang21083791Application {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    public static void main(String[] args) {
        SpringApplication.run(Week08Lab05LeHoangKhang21083791Application.class, args);

    }
    @Bean
    CommandLineRunner initData() {
        return args -> {
            // Kiểm tra và thêm dữ liệu cho Address
            if (addressRepository.count() == 0) {
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<List<Address>> addressTypeReference = new TypeReference<List<Address>>() {};
                InputStream addressInputStream = getClass().getResourceAsStream("/scripts-data/address.json");

                try {
                    List<Address> addresses = mapper.readValue(addressInputStream, addressTypeReference);
                    for (Address address : addresses) {
                        address.setId(null); // Đặt id là null để tránh lỗi trùng lặp id
                        addressRepository.save(address);
                        System.out.println("Saved address: " + address.getCity());
                    }
                    System.out.println("All addresses from JSON saved successfully!");
                } catch (Exception e) {
                    System.out.println("Failed to save addresses: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Addresses already exist in the database. Skipping address initialization.");
            }
            if (candidateRepository.count() == 0) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                TypeReference<List<Candidate>> candidateTypeReference = new TypeReference<List<Candidate>>() {};
                InputStream candidateInputStream = getClass().getResourceAsStream("/scripts-data/candidate.json");

                try {
                    List<Candidate> candidates = mapper.readValue(candidateInputStream, candidateTypeReference);
                    long addressId = 1;
                    for (Candidate candidate : candidates) {
                        candidate.setId(null);
                        Address address = addressRepository.findById(addressId).orElse(null);
                        candidate.setAddress(address);

                        candidateRepository.save(candidate);
                        System.out.println("Saved candidate: " + candidate.getFullName());
                        addressId = (addressId % 200) + 1;
                    }
                    System.out.println("All candidates from JSON saved successfully!");
                } catch (Exception e) {
                    System.out.println("Failed to save candidates: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Candidates already exist in the database. Skipping candidate initialization.");
            }
        };
    }


}
