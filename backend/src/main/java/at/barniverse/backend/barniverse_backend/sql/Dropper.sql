use barniverseDB;

# drop foreign keys
ALTER TABLE product_image DROP CONSTRAINT FK_productImage_productId;

ALTER TABLE auction DROP CONSTRAINT FK_auction_userId;
ALTER TABLE auction DROP CONSTRAINT FK_auction_productId;
ALTER TABLE auction DROP CONSTRAINT FK_auction_winnerOffer;

ALTER TABLE offer DROP CONSTRAINT FK_offer_userId;
ALTER TABLE offer DROP CONSTRAINT FK_offer_auctionId;

# drop tables
DROP TABLE user;
DROP TABLE product;
DROP TABLE product_image;
DROP TABLE auction;
DROP TABLE offer;
DROP TABLE hibernate_sequence;


