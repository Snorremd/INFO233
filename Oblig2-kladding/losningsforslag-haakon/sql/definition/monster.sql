CREATE TABLE Monster (
       monstername VARCHAR(128) NOT NULL,
       sheetrow INT NOT NULL,
       spritesheet VARCHAR(128) NOT NULL,
       priority SMALLINT NOT NULL,
       PRIMARY KEY (monstername),
       FOREIGN KEY (spritesheet) REFERENCES Spritesheets (sheetname)
       )
