DELETE FROM credit_loans;
DELETE FROM credit_cards;

INSERT INTO credit_cards(id, current_money, date, money_limit, percent, return_money, account_id) VALUES
(1, 0, '2023-05-22', 10000, 10, 0, 3);
alter sequence credit_cards_id_seq restart with 2;