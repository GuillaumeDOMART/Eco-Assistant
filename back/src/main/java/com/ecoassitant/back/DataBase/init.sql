/**
    Initialisation DataBase
 */
CREATE TABLE IF NOT EXISTS Profil (
                idProfil serial PRIMARY KEY,
                mail VARCHAR (140) NOT NULL,
                mdp VARCHAR (140) NOT NULL,
                nom VARCHAR (50) NOT NULL,
                prenom VARCHAR (50) NOT NULL,
                isAdmin INT NOT NULL);

CREATE TABLE IF NOT EXISTS Projet (
                idProjet serial PRIMARY KEY,
                profilId INT NOT NULL,
                nomProjet VARCHAR (50) NOT NULL,
                etat VARCHAR (50) NOT NULL,
                CONSTRAINT profilId
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
                CONSTRAINT questionPre
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
                CONSTRAINT questionAsso
                FOREIGN KEY (questionAsso)
                    REFERENCES Question (idQuestion),
                CONSTRAINT questionSuiv
                FOREIGN KEY (questionSuiv)
                    REFERENCES Question (idQuestion));

CREATE TABLE IF NOT EXISTS ReponseDonnee (
                projetId INT NOT NULL,
                reponsePosId INT NOT NULL,
                entry INT NOT NULL,
                CONSTRAINT projetId
                PRIMARY KEY (projetId, reponsePosId),
                FOREIGN KEY (projetId)
                    REFERENCES Projet (idProjet),
                CONSTRAINT reponsePosId
                FOREIGN KEY (reponsePosId)
                    REFERENCES ReponsePossible (idReponsePos));

CREATE TABLE IF NOT EXISTS CalculOperateur(
                idCalculOp serial PRIMARY KEY,
                operateur VARCHAR(5));

CREATE TABLE IF NOT EXISTS Calcul (
                calculOpId INT NOT NULL,
                reponsePossibleId INT NOT NULL,
                nbCalcul INT NOT NULL,
                CONSTRAINT calculOpId
                PRIMARY KEY (calculOpId, reponsePossibleId),
                CONSTRAINT calculOpId
                FOREIGN KEY (calculOpId)
                    REFERENCES CalculOperateur (idCalculOp),
                CONSTRAINT reponsePossibleId
                FOREIGN KEY (reponsePossibleId)
                    REFERENCES ReponsePossible (idReponsePos));

