DELETE FROM credit_loans;
DELETE FROM credit_cards;

alter sequence credit_cards_id_seq restart with 1;