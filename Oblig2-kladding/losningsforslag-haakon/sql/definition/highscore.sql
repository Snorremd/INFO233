CREATE TABLE HighScore (
       autokey   INT NOT NULL PRIMARY KEY
       	         GENERATED ALWAYS AS IDENTITY
		 (START WITH 1, INCREMENT BY 1),  
       levelname 	 VARCHAR(128) NOT NULL,
       tenthsofseconds INT NOT NULL,
       FOREIGN KEY (levelname) REFERENCES Levels (levelname)
       )
