package backend.data.repositories;

import backend.data.entities.ProductPrice;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PriceRepository {
    @PersistenceContext(unitName = "my_pu")
    private EntityManager entityManager;

    public void addPrice(ProductPrice productPrice) {
        entityManager.persist(productPrice);
    }

    public List<ProductPrice> findByProductId(Long productId) {
        return entityManager.createQuery("SELECT pp FROM ProductPrice pp WHERE pp.product.id = :productId", ProductPrice.class)
                .setParameter("productId", productId)
                .getResultList();
    }
}
