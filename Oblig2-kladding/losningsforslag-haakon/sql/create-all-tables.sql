CREATE TABLE StandardTiles (
       tilename VARCHAR(128) NOT NULL,
       walkable BOOLEAN NOT NULL,
       lethal BOOLEAN NOT NULL,
       spriteCol INT NOT NULL,
       spriteRow INT NOT NULL,
       PRIMARY KEY(tilename)
       )
       
CREATE TABLE Aliases (
       letter CHAR(1) NOT NULL,
       tilename VARCHAR(128) NOT NULL,
       PRIMARY KEY(letter),
       FOREIGN KEY (tilename) REFERENCES StandardTile (tilename)
       )

CREATE TABLE Spritesheets (
       sheetname VARCHAR(128) NOT NULL,
       location VARCHAR(128) NOT NULL,
       tilesize INT,
       PRIMARY KEY (sheetname)
       )

CREATE TABLE Levels (
       levelnumber INT NOT NULL,
       levelname VARCHAR(128) NOT NULL,
       file VARCHAR(128) NOT NULL,
       PRIMARY KEY (levelname)
       )

CREATE TABLE Monster (
       monstername VARCHAR(128) NOT NULL,
       sheetrow INT NOT NULL,
       spritesheet VARCHAR(128) NOT NULL,
       priority SMALLINT NOT NULL,
       PRIMARY KEY (monstername),
       FOREIGN KEY (spritesheet) REFERENCES Spritesheets (sheetname)
       )

CREATE TABLE MonsterLevel (
       monstername VARCHAR(128) NOT NULL,
       levelname VARCHAR(128) NOT NULL,
       monsterCol INT NOT NULL,
       monsterRow INT NOT NULL,
       patrol VARCHAR(128) NOT NULL,
       PRIMARY KEY (monstername, levelname, monsterCol, monsterRow),
       FOREIGN KEY (monstername) REFERENCES Monster (monstername),
       FOREIGN KEY (levelname) REFERENCES Levels (levelname)
       )
       
