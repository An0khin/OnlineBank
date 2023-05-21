DELETE FROM debit_cards;
DELETE FROM savings;
DELETE FROM credit_loans;
DELETE FROM credit_cards;
DELETE FROM passports;
DELETE FROM credit_requests;
DELETE FROM accounts;

INSERT INTO accounts(id, login, password, role) VALUES
(1, 'owner', 'owner', 'OWNER'),
(2, 'vinograde@gmail.com', 'pass', 'ADMIN'),
(3, 'user', 'user', 'USER');

alter sequence accounts_id_seq restart with 4;