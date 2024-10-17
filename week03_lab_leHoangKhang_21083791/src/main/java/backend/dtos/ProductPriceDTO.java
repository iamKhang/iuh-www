package backend.dtos;

import backend.data.entities.ProductPrice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class ProductPriceDTO {
    private Long id;
    private Double value;
    private Timestamp applyDate;
    private String note;
    private Byte state;

    // Constructor
    public ProductPriceDTO(Long id, Double value, Timestamp applyDate, String note, Byte state) {
        this.id = id;
        this.value = value;
        this.applyDate = applyDate;
        this.note = note;
        this.state = state;
    }

    // Method to convert from entity to DTO
    public static ProductPriceDTO fromEntity(ProductPrice productPrice) {
        return new ProductPriceDTO(
                productPrice.getId(),
                productPrice.getValue(),
                productPrice.getApplyDate(),
                productPrice.getNote(),
                productPrice.getState()
        );
    }
}