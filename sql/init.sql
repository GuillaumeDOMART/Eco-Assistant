/**
    Initialisation DataBase
 */

CREATE TABLE IF NOT EXISTS profil (
                idprofil serial PRIMARY KEY,
                mail VARCHAR (140) NOT NULL,
                mdp VARCHAR (140) NOT NULL,
                nom VARCHAR (50) NOT NULL,
                prenom VARCHAR (50) NOT NULL,
                isadmin INT NOT NULL);

CREATE TABLE IF NOT EXISTS projet (
                idprojet serial PRIMARY KEY,
                profilid INT NOT NULL,
                nomprojet VARCHAR (50) NOT NULL,
                etat VARCHAR (50) NOT NULL,
                CONSTRAINT profilid
                FOREIGN KEY (profilid)
                    REFERENCES profil (idprofil));

CREATE TABLE IF NOT EXISTS question (
                idquestion serial PRIMARY KEY,
                intitule VARCHAR (255) NOT NULL,
                questionpre INT,
                typeq VARCHAR (140) NOT NULL,
                phase VARCHAR (50) NOT NULL,
                categorie VARCHAR (50) NOT NULL,
                visibilite INT,
                CONSTRAINT questionpre
                FOREIGN KEY (questionpre)
                    REFERENCES question (idquestion));

CREATE TABLE IF NOT EXISTS constante (
                idconstante serial PRIMARY KEY,
                constante INT,
                tracabilite VARCHAR (255)
                );

CREATE TABLE IF NOT EXISTS reponsepossible (
                idreponsepos serial PRIMARY KEY,
                questionasso INT NOT NULL,
                questionsuiv INT,
                intitule VARCHAR (140) NOT NULL,
                constanteid INT NOT NULL,
                CONSTRAINT questionasso
                FOREIGN KEY (questionasso)
                    REFERENCES question (idquestion),
                CONSTRAINT questionsuiv
                FOREIGN KEY (questionsuiv)
                    REFERENCES question (idquestion));

CREATE TABLE IF NOT EXISTS reponsedonnee (
                projetid INT NOT NULL,
                reponseposid INT NOT NULL,
                entry INT NOT NULL,
                CONSTRAINT projetid
                PRIMARY KEY (projetid, reponseposid),
                CONSTRAINT projetid
                FOREIGN KEY (projetid)
                    REFERENCES projet (idprojet),
                CONSTRAINT reponseposid
                FOREIGN KEY (reponseposid)
                    REFERENCES reponsepossible (idreponsepos));

CREATE TABLE IF NOT EXISTS calculoperateur(
                idcalculop serial PRIMARY KEY,
                operateur VARCHAR(10));

CREATE TABLE IF NOT EXISTS calcul (
                calculopid INT NOT NULL,
                reponsepossibleid INT NOT NULL,
                nbcalcul INT NOT NULL,
                CONSTRAINT calculopid
                PRIMARY KEY (calculopid, reponsepossibleid),
                CONSTRAINT calculopid2
                FOREIGN KEY (calculopid)
                    REFERENCES calculoperateur (idcalculop),
                CONSTRAINT reponsepossibleid
                FOREIGN KEY (reponsepossibleid)
                    REFERENCES reponsepossible (idreponsepos));


--CREATION CALCULOPERATEUR
INSERT INTO calculoperateur VALUES
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
INSERT INTO reponsepossible VALUES
                                (1, 1, 2, 'Veuillez entrer un entier', 1),
                                (2, 2, 3, 'Oui',2),
                                (3, 2, null, 'NON',3),
                                (4, 3, null, 'Veuillez entrer un entier',4);

--CREATION REPONSEDONNEE
INSERT INTO reponsedonnee VALUES
                              (1, 1, 30),
                              (1, 2, 1),
                              (1, 4, 3);

--CREATION CALCUL TEST
INSERT INTO calcul VALUES
                       (1, 1, 1),
                       (3, 2, 1),
                       (5, 4, 1);
