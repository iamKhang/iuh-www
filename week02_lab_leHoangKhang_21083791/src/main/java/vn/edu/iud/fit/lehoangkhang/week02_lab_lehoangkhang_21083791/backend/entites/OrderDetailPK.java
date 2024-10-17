package vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.entites;

import java.io.Serializable;
import java.util.Objects;

public class OrderDetailPK implements Serializable {

    private Long order;
    private Long product;

    public OrderDetailPK() {
    }

    public OrderDetailPK(Long order, Long product) {
        this.order = order;
        this.product = product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        OrderDetailPK that = (OrderDetailPK) obj;
        return Objects.equals(order, that.order) && Objects.equals(product, that.product);
    }
}
