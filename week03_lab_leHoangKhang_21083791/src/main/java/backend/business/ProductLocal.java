package backend.business;

import backend.data.entities.Product;
import backend.data.entities.ProductPrice;

import jakarta.ejb.Local;
import java.util.List;

@Local
public interface ProductLocal {
    List<Product> getAllProducts();
    List<ProductPrice> getPricesByProductId(Long productId); // Phương thức để lấy danh sách giá của sản phẩm
    void addProduct(Product product, ProductPrice price); // Phương thức để thêm sản phẩm và giá vào cơ sở dữ liệu
}
