DELETE FROM accounts;

INSERT INTO accounts(id, login, password, role) VALUES
(1, 'owner', 'owner', 'OWNER'),
(2, 'vinograd@gmail.com', 'pass', 'ADMIN');