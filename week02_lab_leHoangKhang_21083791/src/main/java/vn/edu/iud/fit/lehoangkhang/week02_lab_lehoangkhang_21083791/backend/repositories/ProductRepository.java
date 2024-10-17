package vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.entites.Product;

public class ProductRepository {

    @PersistenceContext(unitName = "week02_lab_lehoangkhang_21083791")
    private EntityManager entityManager;

    @Transactional
    public void addProduct(Product product) {
        try {
            entityManager.persist(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateProduct(Product product) {
        try {
            entityManager.merge(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
