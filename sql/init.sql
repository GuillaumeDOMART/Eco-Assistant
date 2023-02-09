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
                operateur VARCHAR(10));

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


--CREATION CALCULOPERATEUR
INSERT INTO calculOperateur VALUES
                                (1, 'ADD'),
                                (2, 'SUB'),
                                (3, 'MULT'),
                                (4, 'DIV'),
                                (5, 'NOTHING');

INSERT INTO profil VALUES
                       (1, 'admin@demo.fr', 'admin@demo.com', 'DEMO', 'Admin', 1),
                       (2, 'createur-dev@demo.fr', 'createur-dev@demo.fr', 'DEMO', 'Createur Dev', 0),
                       (3, 'createur-support@demo.fr', 'createur-support@demo.fr', 'DEMO', 'Createur Support', 0),
                       (4, 'salarie@demo.fr', 'salarie@demo.fr', 'DEMO', 'Salarie', 0);

-- CREATION DES PROJETS DEVS ET SUPPORT
INSERT INTO projet VALUES
                       (1, 2, 'QUESTIONAIRE POUR DEVELOPPEURS', 'INPROGRESS'),
                       (2, 3, 'QUESTIONAIRE POUR L EQUIPE SUPPORT', 'INPROGRESS');

--- CREATION DES CONSTANTES
INSERT INTO constante VALUES
                          (1, 1, 'source1.com'),
                          (2, 1, 'source2.com'),
                          (3, 1, 'source3.com'),
                          (4, 20, 'source4.com');

-- CREATION DES QUESTIONS
INSERT INTO question VALUES
                         (1, 'Combien d heures codez-vous par semaine ?', null, 'NUMERIC', 'DEVELOPPEMENT', 'FIRST', 1),
                         (2, 'Utilisez-vous les méthodes agiles au sein de votre equipe ?', 1, 'QCM', 'PLANIFICATION', 'FIRST', 1),
                         (3, 'Combiens de jours durre un sprint dans votre équipe', 2, 'NUMERIC', 'PLANIFICATION', 'FIRST', 0);


--- CREATION DES REPONSES POSSIBLE
INSERT INTO reponsePossible VALUES
                                (1, 1, 2, 'Veuillez entrer un entier', 1),
                                (2, 2, 3, 'Oui',2),
                                (3, 2, null, 'NON',3),
                                (4, 3, null, 'Veuillez entrer un entier',4);

--CREATION REPONSEDONNEE
INSERT INTO reponseDonnee VALUES
                              (1, 1, 30),
                              (1, 2, 1),
                              (1, 4, 3);

--CREATION CALCUL TEST
INSERT INTO calcul VALUES
                       (1, 1, 1),
                       (3, 2, 1),
                       (5, 4, 1);
