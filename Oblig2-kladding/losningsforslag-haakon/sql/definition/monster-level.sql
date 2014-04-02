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
       
