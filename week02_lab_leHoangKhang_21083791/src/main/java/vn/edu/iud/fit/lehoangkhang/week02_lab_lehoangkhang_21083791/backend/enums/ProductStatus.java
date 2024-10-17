package vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.enums;

public enum ProductStatus {
    AVAILABLE(1),
    SUSPENDED(0),
    DISCONTINUED(-1);

    private final int value;

    ProductStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ProductStatus fromValue(int value) {
        for (ProductStatus status : ProductStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown ProductStatus value: " + value);
    }
}
