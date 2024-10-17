package vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.entites;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ProductPricePK implements Serializable {

    private Long product;
    private LocalDateTime priceDateTime;

    public ProductPricePK() {
    }

    public ProductPricePK(Long product, LocalDateTime priceDateTime) {
        this.product = product;
        this.priceDateTime = priceDateTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, priceDateTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ProductPricePK that = (ProductPricePK) obj;
        return Objects.equals(product, that.product) && Objects.equals(priceDateTime, that.priceDateTime);
    }
}
