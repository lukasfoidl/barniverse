use barniverseDB;

# seed user table
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, status)
VALUES (1, 'Lukas', 'Foidl', 'lukasfoidl', 'wi20b044@technikum-wien.at', 'test123', '', true, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, status)
VALUES (2, 'Nils', 'Petsch', 'nilspetsch', 'wi20b062@technikum-wien.at', 'test123', '', true, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, status)
VALUES (3, 'Admin', 'Admin', 'admin', 'admin@barniverse.at', 'admin123', '', true, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, status)
VALUES (4, 'Hugo', 'Martinez', 'hugomartinez', 'hugo.martinez@mail.com', 'test123', '', false, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, status)
VALUES (5, 'Carlos', 'Hernandez', 'carloshernandez', 'carlos.hernandez@mail.com', 'test123', '', false, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, status)
VALUES (6, 'Susanne', 'Lader', 'susannelader', 'susanne.lader@mail.com', 'test123', '', false, '0');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, status)
VALUES (7, 'Jasmin', 'Rotovic', 'jasminrotovic', 'jasmin.rotovic@mail.com', 'test123', '', false, '1');
INSERT INTO user (id, firstname, lastname, username, email, password, picture, is_admin, status)
VALUES (8, 'Wilhelm', 'Nordthal', 'wilhelmnordthal', 'wilhelm.nordthal@mail.com', 'test123', '', false, '2');

# seed product table
INSERT INTO product (id, name, description)
VALUES (1, 'Gin', 'Eine 1 Liter Flasche bester Gin aus dem Jahr 2020');
INSERT INTO product (id, name, description)
VALUES (2, 'Gruener Veltliner', 'Bester Wein aus dem Burgenland (0.75 l)');
INSERT INTO product (id, name, description)
VALUES (3, 'Berliner Luft', '1 Flasche (1 l) Berliner Luft Pfefferminzlikör aus der Metropole Deutschlands');
INSERT INTO product (id, name, description)
VALUES (4, 'Goesser Bier', 'Goesser Bier 0,75 l mit 3 Volumsprozent aus dem Herzen Österreichs');
INSERT INTO product (id, name, description)
VALUES (5, 'Obstler', 'Schnaps der brutal runterbrennt, 0,75l, 40%, VORSICHT: nicht in hohen Mengen zu sich nehmen');

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
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, locked, product_id, user_id)
VALUES (1, 'Gin Auction', 'Looking for good gin', 10.0, 15.0, 50, 60, '2023-05-01', '2023-06-01', '2023-04-01', '2022-04-04', false, 1, 6);
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, locked, product_id, user_id)
VALUES (2, 'Bier Auction', '0.5 bottles for my bar', 80.0, 110.0, 100, 200, '2023-01-01', '2023-01-10', '2022-12-01', '2022-12-10', false, 4, 6);
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, locked, product_id, user_id)
VALUES (3, 'Locked Auction', 'locked Auction test', 160.0, 200.0, 5000, 10000, '2023-02-01', '2023-02-10', '2022-01-01', '2022-01-10', true, 3, 6);
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, locked, product_id, user_id)
VALUES (4, 'Closed Auction', 'closed Auction test', 100.0, 150.0, 1000, 1500, '2023-02-01', '2023-02-10', '2022-10-01', '2022-10-10', false, 4, 6); #winner offer id = 8

# seed offer table
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (1, 12.4, 53.0, '2023-05-10', 0, 4, 1); #Gin Offer good
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (2, 10.5, 59.0, '2023-05-10', 0, 5, 1); #Gin Offer better
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (3, 18.0, 50.0, '2023-05-10', 0, 5, 1); #Gin Offer outside price range
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (4, 12.4, 75.0, '2023-05-10', 0, 5, 1); #Gin Offer outside quantity range
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (5, 12.4, 53.0, '2023-08-20', 0, 5, 1); #Gin Offer outside delivery range
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (6, 100.0, 180, '2023-01-05', 0, 5, 2); #Bier Offer good
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (7, 175.0, 7000, '2022-02-07', 0, 4, 3); #Locked offer good
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (8, 110.0, 1350.0, '2022-02-07', 1, 4, 4); #closed offer good (won)
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (9, 140.0, 1100.0, '2022-02-07', 2, 5, 4); #closed offer bad (rejected)
