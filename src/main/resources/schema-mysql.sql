CREATE TABLE IF NOT EXISTS location (
  locationId tinyint NOT NULL AUTO_INCREMENT,
  storageName VARCHAR(100) NOT NULL,
);
CREATE TABLE IF NOT EXISTS item (
  itemId tinyint NOT NULL AUTO_INCREMENT,
  itemName VARCHAR(100) NOT NULL,
  itemNumber smallint NOT NULL,
  itemCondition VARCHAR(100),
  itemQuantity smallint NOT NULL,
  comments VARCHAR(100),
  detail VARCHAR(100),
  type VARCHAR(100) NOT NULL,
  storageLocation VARCHAR(100)
);