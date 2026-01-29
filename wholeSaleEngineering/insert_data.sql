-- Insert sample accounts
INSERT INTO accounts (account_no, account_name, account_type, balance_date, currency, opening_avail_bal, created_at, updated_at) 
VALUES ('ACC001', 'John Smith Savings', 'Savings', '2026-01-29', 'USD', 5000.00, '2026-01-29', '2026-01-29');

INSERT INTO accounts (account_no, account_name, account_type, balance_date, currency, opening_avail_bal, created_at, updated_at) 
VALUES ('ACC002', 'Jane Doe Checking', 'Checking', '2026-01-29', 'USD', 15000.00, '2026-01-29', '2026-01-29');

INSERT INTO accounts (account_no, account_name, account_type, balance_date, currency, opening_avail_bal, created_at, updated_at) 
VALUES ('ACC003', 'Business Account', 'Business', '2026-01-29', 'SGD', 50000.00, '2026-01-29', '2026-01-29');

-- Insert sample transactions for ACC001
INSERT INTO account_transactions (account_no, account_name, value_date, currency, debit_amt, credit_amt, tx_type, tx_narrative, created_at, updated_at) 
VALUES ('ACC001', 'John Smith Savings', '2026-01-25', 'USD', NULL, 1000.00, 'CREDIT', 'Direct Deposit - Salary', '2026-01-25', '2026-01-25');

INSERT INTO account_transactions (account_no, account_name, value_date, currency, debit_amt, credit_amt, tx_type, tx_narrative, created_at, updated_at) 
VALUES ('ACC001', 'John Smith Savings', '2026-01-27', 'USD', 500.00, NULL, 'DEBIT', 'ATM Withdrawal', '2026-01-27', '2026-01-27');

-- Insert sample transactions for ACC002
INSERT INTO account_transactions (account_no, account_name, value_date, currency, debit_amt, credit_amt, tx_type, tx_narrative, created_at, updated_at) 
VALUES ('ACC002', 'Jane Doe Checking', '2026-01-20', 'USD', NULL, 5000.00, 'CREDIT', 'Transfer from Savings', '2026-01-20', '2026-01-20');

INSERT INTO account_transactions (account_no, account_name, value_date, currency, debit_amt, credit_amt, tx_type, tx_narrative, created_at, updated_at) 
VALUES ('ACC002', 'Jane Doe Checking', '2026-01-28', 'USD', 200.00, NULL, 'DEBIT', 'Bill Payment - Electricity', '2026-01-28', '2026-01-28');

-- Insert sample transactions for ACC003
INSERT INTO account_transactions (account_no, account_name, value_date, currency, debit_amt, credit_amt, tx_type, tx_narrative, created_at, updated_at) 
VALUES ('ACC003', 'Business Account', '2026-01-15', 'SGD', NULL, 10000.00, 'CREDIT', 'Client Payment', '2026-01-15', '2026-01-15');

INSERT INTO account_transactions (account_no, account_name, value_date, currency, debit_amt, credit_amt, tx_type, tx_narrative, created_at, updated_at) 
VALUES ('ACC003', 'Business Account', '2026-01-22', 'SGD', 3000.00, NULL, 'DEBIT', 'Supplier Payment', '2026-01-22', '2026-01-22');
