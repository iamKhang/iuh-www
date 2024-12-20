DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS department;

CREATE TABLE department (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE employee (
    id BIGINT NOT NULL AUTO_INCREMENT,
    date_of_birth DATE NOT NULL,
    email VARCHAR(255) NOT NULL,
    employee_code VARCHAR(255),
    full_name VARCHAR(100) NOT NULL,
    gender BIT(1),
    phone_number VARCHAR(255),
    department_id BIGINT,
    PRIMARY KEY (id),
    UNIQUE KEY UK_employee_code (employee_code),
    FOREIGN KEY (department_id) REFERENCES department(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
