package backend.business;

import backend.data.entities.Product;
import backend.data.entities.ProductPrice;
import backend.data.repositories.ProductRepository;
import backend.data.repositories.PriceRepository;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class ProductBean implements ProductLocal {
    @Inject
    private ProductRepository productRepository;

    @Inject
    private PriceRepository priceRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public List<ProductPrice> getPricesByProductId(Long productId) {
        return priceRepository.findByProductId(productId);
    }

    @Override
    public void addProduct(Product product, ProductPrice price) {
        // Lưu sản phẩm vào cơ sở dữ liệu
        productRepository.addProduct(product);

        // Lưu giá của sản phẩm vào cơ sở dữ liệu
        price.setProduct(product); // Gán sản phẩm cho giá
        priceRepository.addPrice(price);
    }
}