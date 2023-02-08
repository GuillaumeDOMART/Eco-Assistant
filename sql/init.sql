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
                questionSuiv INT,
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
                CONSTRAINT calculOpId2
                FOREIGN KEY (calculOpId)
                    REFERENCES CalculOperateur (idCalculOp),
                CONSTRAINT reponsePossibleId
                FOREIGN KEY (reponsePossibleId)
                    REFERENCES ReponsePossible (idReponsePos));

-- CREATION D'UTILISATEURS DE TEST
INSERT INTO Profil VALUES
                       (1, 'admin@demo.fr', 'admin@demo.com', 'DEMO', 'Admin', 1),
                       (2, 'createur-dev@demo.fr', 'createur-dev@demo.fr', 'DEMO', 'Createur Dev', 0),
                       (3, 'createur-support@demo.fr', 'createur-support@demo.fr', 'DEMO', 'Createur Support', 0),
                       (4, 'salarie@demo.fr', 'salarie@demo.fr', 'DEMO', 'Salarie', 0);

-- CREATION DES PROJETS DEVS ET SUPPORT
INSERT INTO Projet VALUES
                       (1, 2, 'QUESTIONAIRE POUR DEVELOPPEURS', 'INPROGRESS'),
                       (2, 3, 'QUESTIONAIRE POUR L EQUIPE SUPPORT', 'INPROGRESS');

-- CREATION DES QUESTIONS
INSERT INTO Question VALUES
                         (1, 'Combien d heures codez-vous par semaine ?', null, 'NUMERIC', 'DEVELOPPEMENT', 'FIRST', 1),
                         (2, 'Utilisez-vous les méthodes agiles au sein de votre equipe ?', 1, 'QCM', 'PLANIFICATION', 'FIRST', 1),
                         (3, 'Combiens de jours durre un sprint dans votre équipe', 2, 'NUMERIC', 'PLANIFICATION', 'FIRST', 0);

--- CREATION DES CONSTANTES
INSERT INTO Constante VALUES
                          (1, 1, 'source1.com'),
                          (2, 1, 'source2.com'),
                          (3, 1, 'source3.com'),
                          (4, 20, 'source4.com');

--- CREATION DES REPONSES POSSIBLE
INSERT INTO ReponsePossible VALUES
                                (1, 1, 2, 'Veuillez entrer un entier', 1),
                                (2, 2, 3, 'Oui',2),
                                (3, 2, null, 'NON',3),
                                (4, 3, null, 'Veuillez entrer un entier',4);

