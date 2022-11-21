use barniverseDB;

# set foreign keys
ALTER TABLE product_image ADD CONSTRAINT FK_productImage_productId FOREIGN KEY (product_id) REFERENCES product(id);

ALTER TABLE auction ADD CONSTRAINT FK_auction_userId FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE auction ADD CONSTRAINT FK_auction_productId FOREIGN KEY (product_id) REFERENCES product(id);

ALTER TABLE offer ADD CONSTRAINT FK_offer_userId FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE offer ADD CONSTRAINT FK_offer_auctionId FOREIGN KEY (auction_id) REFERENCES auction(id);