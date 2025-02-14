CREATE TABLE IF NOT EXISTS "company" (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cuit VARCHAR(20) UNIQUE NOT NULL,
    corporate_name VARCHAR(255) NOT NULL,
    registration_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS "transaction" (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id BIGINT NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    debit_account VARCHAR(50) NOT NULL,
    credit_account VARCHAR(50) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES "company"(id) ON DELETE CASCADE
);