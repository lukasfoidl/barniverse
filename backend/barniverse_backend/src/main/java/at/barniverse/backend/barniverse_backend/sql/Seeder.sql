use barniverseDB;

# seed user table
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, state)
VALUES (1, 'Lukas', 'Foidl', 'lukasfoidl', 'wi20b044@technikum-wien.at', '$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC', '', true, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, state)
VALUES (2, 'Nils', 'Petsch', 'nilspetsch', 'wi20b062@technikum-wien.at', '$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC', '', true, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, state)
VALUES (3, 'Admin', 'Admin', 'admin', 'admin@barniverse.at', '$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC', '', true, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, state)
VALUES (4, 'Hugo', 'Martinez', 'hugomartinez', 'hugo.martinez@mail.com', '$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC', '', false, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, state)
VALUES (5, 'Carlos', 'Hernandez', 'carloshernandez', 'carlos.hernandez@mail.com', '$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC', '', false, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, state)
VALUES (6, 'Susanne', 'Lader', 'susannelader', 'susanne.lader@mail.com', '$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC', '', false, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, state)
VALUES (7, 'Jasmin', 'Rotovic', 'jasminrotovic', 'jasmin.rotovic@mail.com', '$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC', '', false, '1');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, state)
VALUES (8, 'Wilhelm', 'Nordthal', 'wilhelmnordthal', 'wilhelm.nordthal@mail.com', '$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC', '', false, '2');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, state)
VALUES (9, 'Admin', 'Admin', 'adminadmin', 'admin@admin.com', '$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC', '', true, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, state)
VALUES (10, 'Maria', 'Stamp', 'mariastamp', 'maria.stamp@mail.com', '$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC', '', false, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, state)
VALUES (11, 'Clara', 'Massov', 'claramassov', 'clara.massov@mail.com', '$2a$10$VCK9hiTv.NKG7tXSXc4DLO5h4iJ00ZjjJQJdfZCH08WrKXxr5chwC', '', false, '0');

# seed product table
INSERT INTO product (id, title, state, description)
VALUES (1, 'Gin', '0', 'Eine 1 Liter Flasche bester Gin aus dem Jahr 2020');
INSERT INTO product (id, title, state, description)
VALUES (2, 'Gruener Veltliner', '0', 'Bester Wein aus dem Burgenland (0.75 l)');
INSERT INTO product (id, title, state, description)
VALUES (3, 'Berliner Luft', '0', '1 Flasche (1 l) Berliner Luft Pfefferminzlikör aus der Metropole Deutschlands');
INSERT INTO product (id, title, state, description)
VALUES (4, 'Goesser Bier', '0', 'Goesser Bier 0,75 l mit 3 Volumsprozent aus dem Herzen Österreichs');
INSERT INTO product (id, title, state, description)
VALUES (5, 'Obstler', '0', 'Schnaps der brutal runterbrennt, 0,75l, 40%, VORSICHT: nicht in hohen Mengen zu sich nehmen');
INSERT INTO product (id, title, state, description)
VALUES (6, 'abgelaufener Ayran', '1', 'Nicht mehr trinken, der bröckelt schon => deswegen gelöscht');

# seed product_image table
INSERT INTO product_image (id, file, product_id)
VALUES (1, 'Gin01.jpeg', '1');
INSERT INTO product_image (id, file, product_id)
VALUES (2, 'Gin02.jpg', '1');
INSERT INTO product_image (id, file, product_id)
VALUES (3, 'Gin03.jpg', '1');
INSERT INTO product_image (id, file, product_id)
VALUES (4, 'Wein01.jpeg', '2');
INSERT INTO product_image (id, file, product_id)
VALUES (5, 'BerlinerLuft01.jpg', '3');
INSERT INTO product_image (id, file, product_id)
VALUES (6, 'BerlinerLuft02.jpg', '3');
INSERT INTO product_image (id, file, product_id)
VALUES (7, 'Bier01.jpg', '4');

# seed auction table
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, state, product_id, user_id)
VALUES (1, 'Gin Auction', 'Looking for good gin', 10.0, 15.0, 50, 60, '2023-05-01', '2023-06-01', '2023-01-01', '2023-03-30', '0', 1, 6);
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, state, product_id, user_id)
VALUES (2, 'Bier Auction', '0.5 bottles for my bar', 80.0, 110.0, 100, 200, '2023-01-01', '2023-01-10', '2023-01-20', '2023-02-01', '0', 4, 6);
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, state, product_id, user_id)
VALUES (3, 'Locked Auction', 'locked Auction test', 160.0, 200.0, 5000, 10000, '2023-02-01', '2023-02-10', '2023-01-10', '2023-01-28', '1', 3, 6);
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, state, product_id, user_id)
VALUES (4, 'Closed Auction', 'closed Auction test', 100.0, 150.0, 1000, 1500, '2023-02-01', '2023-02-10', '2023-01-01', '2023-01-10', '0', 4, 6); #winner offer id = 9
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, state, product_id, user_id)
VALUES (5, 'Februar Obstler Auction', 'Obstler muss man im Februar trinken, damit einem warm bleibt.', 550, 600, 3000, 3500, '2023-02-15', '2023-02-20', '2023-02-01', '2023-02-10', '0', 5, 10);
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, state, product_id, user_id)
VALUES (6, 'März Obstler Auction', 'Obstler muss man auch im März trinken, damit einem warm bleibt, weil es da immer noch kalt ist meistens.',
        550, 600, 3000, 3500, '2023-03-15', '2023-03-20', '2023-03-01', '2023-03-10', '0', 5, 10);
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, state, product_id, user_id)
VALUES (7, 'Frische Luft', 'Wer frische Luft atmen will, muss sie auch trinken.',
        20, 60, 12, 15, '2023-02-10', '2023-02-20', '2023-01-01', '2023-01-31', '0', 3, 11);

# seed offer table
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (1, 12.4, 53.0, '2023-05-10', 0, 4, 1); #Gin Offer good
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (2, 10.5, 59.0, '2023-05-10', 0, 5, 1); #Gin Offer better
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (3, 18.0, 50.0, '2023-05-10', 0, 5, 1); #Gin Offer outside price range
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (4, 12.4, 75.0, '2023-05-10', 0, 5, 1); #Gin Offer outside quantity range
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (5, 12.4, 53.0, '2023-08-20', 0, 5, 1); #Gin Offer outside delivery range
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (6, 100.0, 180, '2023-01-05', 0, 5, 2); #Bier Offer good
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (7, 96.50, 155, '2023-02-10', 0, 4, 2); #Bier Offer good
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (8, 175.0, 7000, '2023-02-07', 0, 4, 3); #Locked offer good
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (9, 110.0, 1350.0, '2023-02-07', 1, 4, 4); #closed offer good (won)
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (10, 140.0, 1100.0, '2023-02-07', 2, 5, 4); #closed offer bad (rejected)
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (11, 18, 80, '2023-05-01', 0, 11, 1);
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (12, 60, 16, '2023-02-20', 0, 10, 7);
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (13, 60, 15, '2023-02-20', 0, 10, 7);
INSERT INTO offer (id, price, quantity, delivery_date, state, user_id, auction_id)
VALUES (14, 45.78, 14, '2023-02-18', 0, 6, 7);