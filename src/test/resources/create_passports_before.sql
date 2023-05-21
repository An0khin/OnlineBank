DELETE FROM passports;

INSERT INTO passports(id, name, surname, datebirth, series, number, account_id) VALUES
(1, 'Artem', 'Vinogradnikov', '1994-06-13', '1234', '123455', '2');

alter sequence passports_id_seq restart with 2;