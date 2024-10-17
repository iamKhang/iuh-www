package vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.entities.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleRepostory {
    private EntityManager em;

    public RoleRepostory() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_Maria");
        em = emf.createEntityManager();
    }

    public List<Role> getAllRolesByAccountId(String accountId) {

        String jpql = "SELECT r FROM Role r JOIN GrantAccess ga ON r.roleId = ga.id.roleId WHERE ga.id.accountId = :accountId";

        List<Role> roles = em.createQuery(jpql, Role.class)
                .setParameter("accountId", accountId)
                .getResultList();
        return roles;


    }
}
