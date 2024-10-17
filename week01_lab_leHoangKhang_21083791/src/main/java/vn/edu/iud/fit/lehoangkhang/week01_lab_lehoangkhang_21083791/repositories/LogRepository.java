package vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.entities.Log;

import java.time.Instant;
import java.util.List;

public class LogRepository {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_Maria");
    private EntityManager em;

    public LogRepository() {
        em = emf.createEntityManager();
        System.out.println("LogRepository created");
    }

    //    Write log in current time
    public Long writeLog(String accountId) {

        try {
            Instant loginTime = Instant.now();
            Log log = new Log();
            log.setAccountId(accountId);
            log.setLoginTime(loginTime);
            log.setLogoutTime(loginTime);
            log.setNotes("Dang dang nhap");
            em.getTransaction().begin();
            em.persist(log);
            em.getTransaction().commit();
            return log.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //    Update log with logout time
    public void updateLog(Long logId) {
        Log log = em.find(Log.class, logId);
        log.setLogoutTime(Instant.now());
        log.setNotes("");
        em.getTransaction().begin();
        em.persist(log);
        em.getTransaction().commit();
    }

    public List<Log> getAllLogs() {
        return em.createQuery("SELECT l FROM Log l", Log.class).getResultList();
    }
}
