-- ========================================
-- Database Setup Script for wholeSaleEngineering2
-- PostgreSQL 18+
-- ========================================

-- Step 1: Create Database (run this in postgres default database)
-- Note: You need to be connected to the 'postgres' database to create a new database
DROP DATABASE IF EXISTS "wholeSaleEngineering2";
CREATE DATABASE "wholeSaleEngineering2"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE "wholeSaleEngineering2" IS 'Wholesale Engineering Application Database';

-- Connect to the new database
\c wholeSaleEngineering2

-- Step 2: Create Tables (these will also be auto-created by Spring Boot JPA)

-- Accounts Table
CREATE TABLE IF NOT EXISTS accounts (
    id BIGSERIAL PRIMARY KEY,
    account_no VARCHAR(20) NOT NULL UNIQUE,
    account_name VARCHAR(100) NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    balance_date DATE NOT NULL,
    currency VARCHAR(3) NOT NULL,
    opening_avail_bal NUMERIC(15, 2) NOT NULL,
    CONSTRAINT chk_currency CHECK (LENGTH(currency) = 3)
);

-- Create indexes for better query performance
CREATE INDEX idx_accounts_account_no ON accounts(account_no);
CREATE INDEX idx_accounts_account_type ON accounts(account_type);
CREATE INDEX idx_accounts_currency ON accounts(currency);

-- Account Transactions Table
CREATE TABLE IF NOT EXISTS account_transactions (
    id BIGSERIAL PRIMARY KEY,
    account_no VARCHAR(20) NOT NULL,
    account_name VARCHAR(100) NOT NULL,
    value_date DATE NOT NULL,
    currency VARCHAR(3) NOT NULL,
    debit_amt NUMERIC(15, 2),
    credit_amt NUMERIC(15, 2),
    tx_type VARCHAR(50) NOT NULL,
    tx_narrative VARCHAR(500),
    CONSTRAINT chk_tx_currency CHECK (LENGTH(currency) = 3),
    CONSTRAINT chk_tx_type CHECK (tx_type IN ('Debit', 'Credit'))
);

-- Create indexes for better query performance
CREATE INDEX idx_transactions_account_no ON account_transactions(account_no);
CREATE INDEX idx_transactions_value_date ON account_transactions(value_date DESC);
CREATE INDEX idx_transactions_tx_type ON account_transactions(tx_type);
CREATE INDEX idx_transactions_currency ON account_transactions(currency);

-- Step 3: Insert Sample Data

-- Clear existing data (if any)
TRUNCATE TABLE account_transactions CASCADE;
TRUNCATE TABLE accounts CASCADE;

-- Reset sequences
ALTER SEQUENCE accounts_id_seq RESTART WITH 1;
ALTER SEQUENCE account_transactions_id_seq RESTART WITH 1;

-- Insert Accounts
INSERT INTO accounts (account_no, account_name, account_type, balance_date, currency, opening_avail_bal) VALUES
('585309209', 'SGSavings726', 'Savings', '2018-11-08', 'SGD', 84327.51),
('791066619', 'AUSavings933', 'Savings', '2018-11-08', 'AUD', 88005.93),
('321143048', 'AUCurrent433', 'Current', '2018-11-08', 'AUD', 38010.62),
('347786244', 'SGCurrent166', 'Current', '2018-11-08', 'SGD', 50664.65),
('680168913', 'AUCurrent374', 'Current', '2018-11-08', 'AUD', 41327.28);

-- Insert Account Transactions
INSERT INTO account_transactions (account_no, account_name, value_date, currency, debit_amt, credit_amt, tx_type, tx_narrative) VALUES
-- Transactions for SGSavings726 (585309209)
('585309209', 'SGSavings726', '2018-11-08', 'SGD', NULL, 9540.48, 'Credit', 'Salary deposit'),
('585309209', 'SGSavings726', '2018-10-15', 'SGD', 1200.00, NULL, 'Debit', 'ATM withdrawal'),
('585309209', 'SGSavings726', '2018-09-20', 'SGD', NULL, 5564.79, 'Credit', 'Transfer from savings'),
('585309209', 'SGSavings726', '2018-08-10', 'SGD', 850.00, NULL, 'Debit', 'Online shopping'),

-- Transactions for AUSavings933 (791066619)
('791066619', 'AUSavings933', '2018-11-05', 'AUD', NULL, 7497.82, 'Credit', 'Interest payment'),
('791066619', 'AUSavings933', '2018-10-22', 'AUD', 3200.50, NULL, 'Debit', 'Bill payment'),
('791066619', 'AUSavings933', '2018-09-15', 'AUD', NULL, 12000.00, 'Credit', 'Bonus payment'),

-- Transactions for AUCurrent433 (321143048)
('321143048', 'AUCurrent433', '2018-11-03', 'AUD', NULL, 15000.00, 'Credit', 'Customer payment'),
('321143048', 'AUCurrent433', '2018-10-28', 'AUD', 8500.00, NULL, 'Debit', 'Supplier payment'),
('321143048', 'AUCurrent433', '2018-10-10', 'AUD', 2100.00, NULL, 'Debit', 'Office supplies'),
('321143048', 'AUCurrent433', '2018-09-25', 'AUD', NULL, 20000.00, 'Credit', 'Invoice payment'),

-- Transactions for SGCurrent166 (347786244)
('347786244', 'SGCurrent166', '2018-11-07', 'SGD', 2100.00, NULL, 'Debit', 'Rent payment'),
('347786244', 'SGCurrent166', '2018-10-30', 'SGD', NULL, 12000.00, 'Credit', 'Invoice payment'),
('347786244', 'SGCurrent166', '2018-10-15', 'SGD', 1500.00, NULL, 'Debit', 'Utility bills'),

-- Transactions for AUCurrent374 (680168913)
('680168913', 'AUCurrent374', '2018-11-06', 'AUD', NULL, 6500.00, 'Credit', 'Revenue deposit'),
('680168913', 'AUCurrent374', '2018-10-20', 'AUD', 3200.00, NULL, 'Debit', 'Tax payment'),
('680168913', 'AUCurrent374', '2018-09-18', 'AUD', NULL, 8900.00, 'Credit', 'Service payment');

-- Step 4: Verify Data
SELECT 'Accounts Count:' AS info, COUNT(*) AS count FROM accounts
UNION ALL
SELECT 'Transactions Count:', COUNT(*) FROM account_transactions;

-- Display sample records
SELECT * FROM accounts ORDER BY account_no;
SELECT * FROM account_transactions ORDER BY account_no, value_date DESC LIMIT 10;

-- Step 5: Grant Permissions (if needed)
GRANT ALL PRIVILEGES ON DATABASE "wholeSaleEngineering2" TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO postgres;

-- Display connection information
SELECT 'Database Setup Complete!' AS message;
SELECT 'Database Name: wholeSaleEngineering2' AS info
UNION ALL
SELECT 'Username: postgres'
UNION ALL
SELECT 'Password: daminda@77 (should be encrypted in application.properties)';
