package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DataInitializer {

    @Autowired
    private DataSource dataSource;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                // Đọc file data.sql từ classpath và chạy script
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("data.sql"));
                System.out.println("Data initialization completed!");
            } catch (SQLException e) {
                System.err.println("Failed to initialize data: " + e.getMessage());
                throw new RuntimeException(e);
            }
        };
    }
}