package vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.enums;

public enum EmployeeStatus {
    ACTIVE(1),
    ON_LEAVE(0),
    TERMINATED(-1);

    private final int value;

    EmployeeStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EmployeeStatus fromValue(int value) {
        for (EmployeeStatus status : EmployeeStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown EmployeeStatus value: " + value);
    }
}

