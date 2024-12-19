package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.enums.AccountRole;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    @Enumerated(EnumType.STRING)
    private AccountRole role;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    private Company company;

    public String getEmail() {
        if (role == AccountRole.CANDIDATE && candidate != null) {
            return candidate.getEmail();
        } else if (role == AccountRole.EMPLOYER && company != null) {
            return company.getEmail();
        }
        return null;
    }

    public String getDisplayName() {
        if (role == AccountRole.CANDIDATE && candidate != null) {
            return candidate.getFullName();
        } else if (role == AccountRole.EMPLOYER && company != null) {
            return company.getName();
        }
        return getEmail();
    }
}
