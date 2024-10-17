package vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.entities.Account;

import java.util.List;

public class AccountRepository {

    private EntityManager em;

    public AccountRepository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_Maria");
        em = emf.createEntityManager();
    }

    public int checkLogin(String accountId, String password) {

        try {
            Account account = em.find(Account.class, accountId);
            System.out.println(account);
            if (account != null && account.getPassword().equals(password)) {
                return 1;
            }else if (account != null && !account.getPassword().equals(password)) {
                return 2;
            }else  {
                return 3;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 4;
        }
    }

    public void addAccount(Account account) {
        try {
            em.getTransaction().begin();
            em.persist(account);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public Account getAccount(String accountId) {
        return em.find(Account.class, accountId);
    }

    public List<Account> getAllAccounts() {
        return em.createQuery("SELECT a FROM Account a", Account.class).getResultList();
    }
}