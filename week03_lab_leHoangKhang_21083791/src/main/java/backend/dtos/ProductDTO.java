package backend.dtos;


import backend.data.entities.Product;
import backend.data.entities.ProductPrice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private String imgPath;
    private List<ProductPriceDTO> prices;

    // Constructor
    public ProductDTO(Long id, String name, String description, String imgPath, List<ProductPriceDTO> prices) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
        this.prices = prices;
    }

    // Method to convert from entity to DTO
    public static ProductDTO fromEntity(Product product, List<ProductPrice> prices) {
        List<ProductPriceDTO> priceDTOs = prices.stream().map(ProductPriceDTO::fromEntity).collect(Collectors.toList());
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getImgPath(), priceDTOs);
    }

    // Method to convert from DTO to entity
    public static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setImgPath(productDTO.getImgPath());
        return product;
    }

    public static ProductPrice toPriceEntity(ProductPriceDTO priceDTO) {
        return new ProductPrice(null, null, priceDTO.getValue(), priceDTO.getApplyDate(), priceDTO.getNote(), priceDTO.getState());
    }
}