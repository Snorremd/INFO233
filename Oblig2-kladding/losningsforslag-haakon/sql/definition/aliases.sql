CREATE TABLE Aliases (
       letter CHAR(1) NOT NULL,
       tilename VARCHAR(128) NOT NULL,
       PRIMARY KEY(letter),
       FOREIGN KEY (tilename) REFERENCES StandardTiles (tilename)
       )
