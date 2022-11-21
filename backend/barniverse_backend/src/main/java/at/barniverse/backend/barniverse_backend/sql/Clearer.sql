use barniverseDB;

# drop foreign keys
ALTER TABLE product_image DROP CONSTRAINT FK_productImage_productId;

ALTER TABLE auction DROP CONSTRAINT FK_auction_userId;
ALTER TABLE auction DROP CONSTRAINT FK_auction_productId;

ALTER TABLE offer DROP CONSTRAINT FK_offer_userId;
ALTER TABLE offer DROP CONSTRAINT FK_offer_auctionId;

# clear tables
DELETE FROM user;
DELETE FROM product;
DELETE FROM product_image;
DELETE FROM auction;
DELETE FROM offer;
