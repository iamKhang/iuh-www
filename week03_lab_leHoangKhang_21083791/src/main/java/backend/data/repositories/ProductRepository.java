package backend.data.repositories;

import backend.data.entities.Product;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductRepository {
    @PersistenceContext(unitName = "my_pu")
    private EntityManager entityManager;

    public List<Product> getAllProducts() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public void addProduct(Product product) {
        entityManager.persist(product);
    }
}
