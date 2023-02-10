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
CONSTRAINT fk_projetid
FOREIGN KEY (projetid)
REFERENCES projet (idprojet),
CONSTRAINT reponseposid
FOREIGN KEY (reponseposid)
REFERENCES reponsepossible (idreponsepos));

CREATE TABLE IF NOT EXISTS calculoperateur(
idcalculop serial PRIMARY KEY,
operateur VARCHAR(10));

CREATE TABLE IF NOT EXISTS calcul (
idcalcul serial PRIMARY KEY,
calculopid INT NOT NULL,
reponsepossibleid INT NOT NULL,
nbcalcul INT NOT NULL,
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

-- CREATION DES QUESTIONS
INSERT INTO question VALUES
(1, 'Sur la duree totale du projet, combiens de jours avez-vous passer sur place (au bureau) ?', 6, 'NUMERIC', 'HORS_PHASE', 'FIRST', 1),
(2, 'Est-ce que vous vous déplacer à pied pour vous rendre sur place ?', 1, 'QCM', 'HORS_PHASE', 'FIRST', 1),
(3, 'Combiens de KM est-ce que vous parcourrez en voiture pour vous rendre sur place ?', 2, 'QCM', 'HORS_PHASE', 'FIRST', 0),
(4, 'Combiens de salariées ont été mobilisé pour la phase de développements ?', 3, 'NUMERIC', 'DEVELOPPEMENT', 'FIRST', 1),
(5, 'Combiens de jours la phase de développements a-t-elle durée?', 4, 'NUMERIC', 'DEVELOPPEMENT', 'FIRST', 1),
(6, 'Combiens de jours au total le projet a-t-il / vas-t-il duré ?', null, 'NUMERIC', 'HORS_PHASE', 'FIRST', 1);

--- CREATION DES CONSTANTES
INSERT INTO constante VALUES
(1, 0, 'Constante neutre de l addition et la soustraction'),
(2, 1, 'Constante neutre pour la multiplication et la division'),
(3, 3.83, 'https://docs.google.com/document/d/1vlv_qH2_NvRNO2uHRH93mogf9ENM6OsNc3a3wXjNaR8/edit#heading=h.72nwi8b72wp2'),
(4, 0.103, 'Consomation moyenne d une voiture en KG ce CO2/KM : https://carlabelling.ademe.fr/chiffrescles/r/evolutionTauxCo2');


--- CREATION DES REPONSES POSSIBLE
INSERT INTO reponsepossible VALUES
(1, 6, 1, 'Veuillez entrer un entier', 2),
(2, 1, 2, 'Veuillez entrer un entier', 2),
(3, 2, 3, 'OUI', 1),
(4, 2, 5, 'NON', 1),
(5, 3, 4, 'Veuillez entrer un entier', 4),
(6, 4, 5, 'Veuillez entrer un entier', 2),
(7, 5, null, 'Veuillez entrer un entier', 3);

--CREATION REPONSEDONNEE
INSERT INTO reponsedonnee VALUES
(1, 1, 30),
(1, 2, 1),
(1, 4, 3),
(1, 6, 10),
(1, 7, 15);

--CREATION CALCUL TEST
INSERT INTO calcul VALUES
(1, 3, 6, 1),
(2, 3, 5, 1),
(3, 3, 6, 2),
(4, 5, 7, 2);
