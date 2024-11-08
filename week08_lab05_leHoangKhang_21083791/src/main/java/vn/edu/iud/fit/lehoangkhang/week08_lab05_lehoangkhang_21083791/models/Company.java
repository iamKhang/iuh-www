package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String name;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String about;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String email;

    @Column(columnDefinition = "VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String phone;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String webUrl;

    @OneToOne(mappedBy = "company")
    private Address address;

    @OneToMany(mappedBy = "company")
    private List<Job> jobs;

    public Company(String name, String about, String email, String phone, String webUrl) {
        this.name = name;
        this.about = about;
        this.email = email;
        this.phone = phone;
        this.webUrl = webUrl;
    }
}