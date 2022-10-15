use barniverseDB;

# seed user table
DELETE FROM user;
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
DELETE FROM product;
INSERT INTO product (id, name, description)
VALUES (1, 'Gin', 'Eine 1 Liter Flasche bester Gin aus dem Jahr 2020');
INSERT INTO product (id, name, description)
VALUES (2, 'Gruener Veltliner', 'Bester Wein aus dem Burgenland (0.75 l)');
INSERT INTO product (id, name, description)
VALUES (3, 'Berliner Luft', '1 Flasche (1 l) Berliner Luft Pfefferminzlikör aus der Metropole Deutschlands');
INSERT INTO product (id, name, description)
VALUES (4, 'Goesser Bier', 'Goesser Bier 0,75 l mit 3 Volumsprozent aus dem Herzen Österreichs');

# seed product images
DELETE FROM product_image;
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
