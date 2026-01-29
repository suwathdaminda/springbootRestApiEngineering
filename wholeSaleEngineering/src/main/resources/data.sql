-- Sample data initialization for wholeSaleEngineering2 database
-- This script will populate initial data for testing

-- Clear existing data (if any)
DELETE FROM account_transactions;
DELETE FROM accounts;

-- Insert sample accounts
INSERT INTO accounts (id, account_no, account_name, account_type, balance_date, currency, opening_avail_bal) VALUES
(1, '585309209', 'SGSavings726', 'Savings', '2018-11-08', 'SGD', 84327.51),
(2, '791066619', 'AUSavings933', 'Savings', '2018-11-08', 'AUD', 88005.93),
(3, '321143048', 'AUCurrent433', 'Current', '2018-11-08', 'AUD', 38010.62),
(4, '347786244', 'SGCurrent166', 'Current', '2018-11-08', 'SGD', 50664.65),
(5, '680168913', 'AUCurrent374', 'Current', '2018-11-08', 'AUD', 41327.28);

-- Insert sample transactions
INSERT INTO account_transactions (id, account_no, account_name, value_date, currency, debit_amt, credit_amt, tx_type, tx_narrative) VALUES
(1, '585309209', 'SGSavings726', '2018-11-08', 'SGD', NULL, 9540.48, 'Credit', 'Salary deposit'),
(2, '585309209', 'SGSavings726', '2018-10-15', 'SGD', 1200.00, NULL, 'Debit', 'ATM withdrawal'),
(3, '585309209', 'SGSavings726', '2018-09-20', 'SGD', NULL, 5564.79, 'Credit', 'Transfer from savings'),
(4, '791066619', 'AUSavings933', '2018-11-05', 'AUD', NULL, 7497.82, 'Credit', 'Interest payment'),
(5, '791066619', 'AUSavings933', '2018-10-22', 'AUD', 3200.50, NULL, 'Debit', 'Bill payment'),
(6, '321143048', 'AUCurrent433', '2018-11-03', 'AUD', NULL, 15000.00, 'Credit', 'Customer payment'),
(7, '321143048', 'AUCurrent433', '2018-10-28', 'AUD', 8500.00, NULL, 'Debit', 'Supplier payment'),
(8, '347786244', 'SGCurrent166', '2018-11-07', 'SGD', 2100.00, NULL, 'Debit', 'Rent payment'),
(9, '347786244', 'SGCurrent166', '2018-10-30', 'SGD', NULL, 12000.00, 'Credit', 'Invoice payment'),
(10, '680168913', 'AUCurrent374', '2018-11-06', 'AUD', NULL, 6500.00, 'Credit', 'Revenue deposit');

-- Reset sequences (adjust the sequence value based on the number of records inserted)
SELECT setval('accounts_id_seq', (SELECT MAX(id) FROM accounts));
SELECT setval('account_transactions_id_seq', (SELECT MAX(id) FROM account_transactions));
