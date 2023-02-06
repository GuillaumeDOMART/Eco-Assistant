/**
    Initialisation DataBase
 */
CREATE TABLE IF NOT EXISTS Profil (
                idProfil serial PRIMARY KEY,
                mail VARCHAR (140) NOT NULL,
                password VARCHAR (140) NOT NULL,
                nom VARCHAR (50) NOT NULL,
                prenom VARCHAR (50) NOT NULL
                isAdmin INT NOT NULL);

CREATE TABLE IF NOT EXISTS Projet (
                idProjet serial PRIMARY KEY,
                profilId INT NOT NULL,
                nomProjet VARCHAR (50) NOT NULL,
                etat VARCHAR (50) NOT NULL,
                FOREIGN KEY (profilId)
                    REFERENCES Profil (idProfil));

CREATE TABLE IF NOT EXISTS Question (
                idQuestion serial PRIMARY KEY,
                intitule VARCHAR (255) NOT NULL,
                questionPre INT,
                typeQ VARCHAR (140) NOT NULL,
                phase VARCHAR (50) NOT NULL,
                categorie VARCHAR (50) NOT NULL,
                visibilite INT,
                FOREIGN KEY (questionPre)
                    REFERENCES Question (idQuestion));

CREATE TABLE IF NOT EXISTS Constante (
                idConstante serial PRIMARY KEY,
                constante INT,
                tracabilite VARCHAR (255)
                );

CREATE TABLE IF NOT EXISTS ReponsePossible (
                idReponsePos serial PRIMARY KEY,
                questionAsso INT NOT NULL,
                questionSuiv INT NOT NULL,
                intitule VARCHAR (140) NOT NULL,
                constanteId INT NOT NULL,
                FOREIGN KEY (questionAsso)
                    REFERENCES Question (idQuestion),
                FOREIGN KEY (questionSuiv)
                    REFERENCES Question (idQuestion));

CREATE TABLE IF NOT EXISTS ReponseDonnee (
                projetId INT NOT NULL,
                reponsePosId INT NOT NULL,
                entry INT NOT NULL,
                PRIMARY KEY (projetId, reponsePosId),
                FOREIGN KEY (projetId)
                    REFERENCES Projet (idProjet),
                FOREIGN KEY (reponsePosId)
                    REFERENCES ReponsePossible (idReponsePos));

CREATE TABLE IF NOT EXISTES Calcul (
                calculOpId INT NOT NULL,
                reponsePossibleId INT NOT NULL,
                nbCalcul INT NOT NULL,
                PRIMARY KEY (calculOpId, reponsePossibleId),
                FOREIGN KEY (calculOpId)
                    REFERENCES CalculOperateur (idCalculOp),
                FOREIGN KEY (reponsePossibleId)
                    REFERENCES ReponsePossible (idReponsePos));

CREATE TABLE IF NOT EXISTES CalculOperateur(
                idCalcul SERIEAL PRIMARY KEY,
                operateur VARCHAR(5))