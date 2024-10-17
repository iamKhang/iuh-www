package vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.business;

import jakarta.inject.Inject;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.entites.Product;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.repositories.ProductRepository;

public class ProductBO {

    @Inject
    private ProductRepository productRepository;

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }
}
