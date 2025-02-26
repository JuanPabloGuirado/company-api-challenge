-- Insert some sample companies for testing
INSERT INTO "company" (cuit, name, registration_date) VALUES
('30-12345678-9', 'My Company S.A.', '2025-01-15'),
('30-87654321-0', 'My Other Company S.A.', '2025-01-20');

-- Insert some sample transactions (last month period)
INSERT INTO "transaction" (company_id, amount, debit_account, credit_account, transaction_date) VALUES
(1, 5000.00, '12345678', '87654321', '2025-01-16 14:30:00'),
(2, 7500.50, '87654321', '12345678', '2025-01-25 09:15:00'),
(1, 3200.75, '56781234', '43218765', '2025-01-20 18:45:00');
