DELETE FROM debit_cards;
DELETE FROM savings;
DELETE FROM credit_loans;
DELETE FROM credit_cards;
DELETE FROM passports;
DELETE FROM credit_requests;
DELETE FROM accounts;
alter sequence accounts_id_seq restart with 1;