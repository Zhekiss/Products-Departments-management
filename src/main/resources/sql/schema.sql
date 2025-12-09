CREATE TABLE IF NOT EXISTS department (
    department_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    working_hours VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS product (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    department_id INT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES department(department_id) ON DELETE CASCADE
);