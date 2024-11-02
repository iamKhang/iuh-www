package vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791.entities.Account;
import vn.edu.iud.fit.lehoangkhang.week07_springdata_lehoangkhang_21083791.repositories.AccountRepository;

import java.util.Random;

@SpringBootApplication
public class Week07SpringDataLeHoangKhang21083791Application {
    public static void main(String[] args) {
        SpringApplication.run(Week07SpringDataLeHoangKhang21083791Application.class, args);
    }

}
