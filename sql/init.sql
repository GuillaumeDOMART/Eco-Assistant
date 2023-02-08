/**
    Initialisation DataBase
 */
CREATE TABLE IF NOT EXISTS profil (
                idProfil serial PRIMARY KEY,
                mail VARCHAR (140) NOT NULL,
                mdp VARCHAR (140) NOT NULL,
                nom VARCHAR (50) NOT NULL,
                prenom VARCHAR (50) NOT NULL,
                isAdmin INT NOT NULL);

CREATE TABLE IF NOT EXISTS projet (
                idProjet serial PRIMARY KEY,
                profilId INT NOT NULL,
                nomProjet VARCHAR (50) NOT NULL,
                etat VARCHAR (50) NOT NULL,
                CONSTRAINT profilId
                FOREIGN KEY (profilId)
                    REFERENCES profil (idProfil));

CREATE TABLE IF NOT EXISTS question (
                idQuestion serial PRIMARY KEY,
                intitule VARCHAR (255) NOT NULL,
                questionPre INT,
                typeQ VARCHAR (140) NOT NULL,
                phase VARCHAR (50) NOT NULL,
                categorie VARCHAR (50) NOT NULL,
                visibilite INT,
                CONSTRAINT questionPre
                FOREIGN KEY (questionPre)
                    REFERENCES question (idQuestion));

CREATE TABLE IF NOT EXISTS constante (
                idConstante serial PRIMARY KEY,
                constante INT,
                tracabilite VARCHAR (255)
                );

CREATE TABLE IF NOT EXISTS reponsePossible (
                idReponsePos serial PRIMARY KEY,
                questionAsso INT NOT NULL,
                questionSuiv INT,
                intitule VARCHAR (140) NOT NULL,
                constanteId INT NOT NULL,
                CONSTRAINT questionAsso
                FOREIGN KEY (questionAsso)
                    REFERENCES question (idQuestion),
                CONSTRAINT questionSuiv
                FOREIGN KEY (questionSuiv)
                    REFERENCES question (idQuestion));

CREATE TABLE IF NOT EXISTS reponseDonnee (
                projetId INT NOT NULL,
                reponsePosId INT NOT NULL,
                entry INT NOT NULL,
                CONSTRAINT projetId
                PRIMARY KEY (projetId, reponsePosId),
                FOREIGN KEY (projetId)
                    REFERENCES projet (idProjet),
                CONSTRAINT reponsePosId
                FOREIGN KEY (reponsePosId)
                    REFERENCES reponsePossible (idReponsePos));

CREATE TABLE IF NOT EXISTS calculOperateur(
                idCalculOp serial PRIMARY KEY,
                operateur VARCHAR(5));

CREATE TABLE IF NOT EXISTS calcul (
                calculOpId INT NOT NULL,
                reponsePossibleId INT NOT NULL,
                nbCalcul INT NOT NULL,
                CONSTRAINT calculOpId
                PRIMARY KEY (calculOpId, reponsePossibleId),
                CONSTRAINT calculOpId2
                FOREIGN KEY (calculOpId)
                    REFERENCES calculOperateur (idCalculOp),
                CONSTRAINT reponsePossibleId
                FOREIGN KEY (reponsePossibleId)
                    REFERENCES reponsePossible (idReponsePos));

