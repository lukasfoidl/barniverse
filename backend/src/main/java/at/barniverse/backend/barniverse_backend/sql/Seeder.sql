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

# seed product_image table
INSERT INTO product_image (id, path, product_id)
VALUES (1, 'sdfjlahsudfhakljsdfasd/Gin', '1');
INSERT INTO product_image (id, path, product_id)
VALUES (2, 'f4whfowi4hfwiu4hwererf/Gin', '1');
INSERT INTO product_image (id, path, product_id)
VALUES (3, 'jcvjr8e4jfa4jfo84jfawz/Gin', '1');
INSERT INTO product_image (id, path, product_id)
VALUES (4, 'dfafjoeifaouefawdujfse/GruenerVeltliner', '2');
INSERT INTO product_image (id, path, product_id)
VALUES (5, 'trorjefoeirjfsjfwerwe7/BerlinerLuft', '3');
INSERT INTO product_image (id, path, product_id)
VALUES (6, 'fnauiserhfi8w4riawhefb/BerlinerLuft', '3');
INSERT INTO product_image (id, path, product_id)
VALUES (7, 'fdauhrualseiufhssdftrz/GoesserBier', '4');

# seed auction table
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, locked, user_id, winner_offer_id)
VALUES (1, 'Gin Auction', 'Looking for good gin', 10.0, 15.0, 50, 60, '2023-05-01', '2023-06-01', '2023-04-01', '2022-04-04', false, 3, null);
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, locked, user_id, winner_offer_id)
VALUES (2, 'Bier Auction', '0.5 bottles for my bar', 80.0, 110.0, 100, 200, '2023-01-01', '2023-01-10', '2022-12-01', '2022-12-10', false, 3, null);
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, locked, user_id, winner_offer_id)
VALUES (3, 'LockedAuction', 'lockedAuction testing', 160.0, 200.0, 5000, 10000, '2023-02-01', '2023-02-10', '2022-01-01', '2022-01-10', true, 3, null);
INSERT INTO auction (id, title, description, min_price, max_price, min_quantity, max_quantity, start_delivery_date, end_delivery_date, start_date, end_date, locked, user_id, winner_offer_id)
VALUES (4, 'Won Auction', 'wonAuction testing', 160.0, 200.0, 5000, 10000, '2023-02-01', '2023-02-10', '2022-01-01', '2022-01-10', false, 3, 4);

# seed offer table
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (1, 12.4, 53, '2023-05-10',0, 4, 1); #Gin Offer inside price range
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (2, 18.0, 50.0, '2023-05-10', 0, 5, 1); #Gin Offer outside price range
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (3, 100.0, 180, '2022-12-05', 0, 5, 2); #Bier Offer inside quantity range
INSERT INTO offer (id, price, quantity, delivery_date, status, user_id, auction_id)
VALUES (4, 100.0, 350, '2022-12-05', 0, 5, 2); #Bier Offer outside quantity range

# set foreign keys
ALTER TABLE product_image ADD CONSTRAINT FK_productImage_productId FOREIGN KEY (product_id) REFERENCES product(id);

ALTER TABLE auction ADD CONSTRAINT FK_auction_userId FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE auction ADD CONSTRAINT FK_auction_productId FOREIGN KEY (product_id) REFERENCES product(id);
ALTER TABLE auction ADD CONSTRAINT FK_auction_winnerOffer FOREIGN KEY (winner_offer_id) REFERENCES offer(id);

ALTER TABLE offer ADD CONSTRAINT FK_offer_userId FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE offer ADD CONSTRAINT FK_offer_auctionId FOREIGN KEY (auction_id) REFERENCES auction(id);
