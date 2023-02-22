/**
Initialisation DataBase
*/
CREATE SEQUENCE profil_sequence START WITH 1;
CREATE TABLE IF NOT EXISTS profil
(
    idprofil INTEGER PRIMARY KEY DEFAULT nextval('profil_sequence'),
    mail     VARCHAR(140) NOT NULL,
    mdp      VARCHAR(140) NOT NULL,
    nom      VARCHAR(50)  NOT NULL,
    prenom   VARCHAR(50)  NOT NULL,
    isadmin  INT          NOT NULL
);
CREATE SEQUENCE project_sequence START WITH 1;
CREATE TABLE IF NOT EXISTS projet
(
    idprojet  INTEGER PRIMARY KEY DEFAULT nextval('project_sequence'),
    profilid  INT         NOT NULL,
    nomprojet VARCHAR(50) NOT NULL,
    etat      VARCHAR(50) NOT NULL,
    CONSTRAINT profilid FOREIGN KEY (profilid) REFERENCES profil (idprofil)
);
CREATE TABLE IF NOT EXISTS question
(
    idquestion  serial PRIMARY KEY,
    intitule    VARCHAR(255) NOT NULL,
    questionpre INT,
    typeq       VARCHAR(140) NOT NULL,
    phase       VARCHAR(50)  NOT NULL,
    dependance  INT,
    CONSTRAINT questionpre FOREIGN KEY (questionpre) REFERENCES question (idquestion),
    CONSTRAINT dependance FOREIGN KEY(dependance) REFERENCES question (idquestion)
);
CREATE TABLE IF NOT EXISTS constante
(
    idconstante serial PRIMARY KEY,
    constante   INT,
    tracabilite VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS reponsepossible
(
    idreponsepos serial PRIMARY KEY,
    questionasso INT          NOT NULL,
    questionsuiv INT,
    intitule     VARCHAR(140) NOT NULL,
    constanteid  INT          NOT NULL,
    CONSTRAINT questionasso FOREIGN KEY (questionasso) REFERENCES question (idquestion),
    CONSTRAINT questionsuiv FOREIGN KEY (questionsuiv) REFERENCES question (idquestion)
);
CREATE TABLE IF NOT EXISTS reponsedonnee
(
    projetid     INT NOT NULL,
    reponseposid INT NOT NULL,
    entry        INT NOT NULL,
    CONSTRAINT projetid PRIMARY KEY (projetid, reponseposid),
    CONSTRAINT fk_projetid FOREIGN KEY (projetid) REFERENCES projet (idprojet),
    CONSTRAINT reponseposid FOREIGN KEY (reponseposid) REFERENCES reponsepossible (idreponsepos)
);
CREATE TABLE IF NOT EXISTS calculoperateur
(
    idcalculop serial PRIMARY KEY,
    operateur  VARCHAR(10)
);
CREATE TABLE IF NOT EXISTS calcul
(
    idcalcul          SERIAL PRIMARY KEY,
    calculopid        INT         NOT NULL,
    reponsepossibleid INT         NOT NULL,
    nbcalcul          INT         NOT NULL,
    priorite          INT         NOT NULL,
    phase             VARCHAR(50) NOT NULL,
    CONSTRAINT calculopid FOREIGN KEY (calculopid) REFERENCES calculoperateur (idcalculop),
    CONSTRAINT reponsepossibleid FOREIGN KEY (reponsepossibleid) REFERENCES reponsepossible (idreponsepos)
);
CREATE TABLE IF NOT EXISTS questionpropose
(
    idquestion SERIAL PRIMARY KEY,
    intitule   VARCHAR(255) NOT NULL,
    phase      VARCHAR(50)  NOT NULL,
    vote       INT,
    isapprove  INT          NOT NULL
);
--CREATION CALCULOPERATEUR
INSERT INTO calculoperateur (operateur)
VALUES ('ADD'),
       ('SUB'),
       ('MULT'),
       ('DIV'),
       ('NOTHING');
INSERT INTO profil(mail, mdp, nom, prenom, isadmin)
VALUES ('admin@demo.fr', '$2a$10$b8qqjDh64vjz2/KsV9Yc8uYKMDTatn3cL6Bp7Uuhcwg/N0lKPxf2m',
        'DEMO', 'Admin', 1),
       ('salarie@demo.fr', '$2a$10$5FQSn68f5IddpjRlc0nxguBSRCLpnMfEsbzqhlK5UPP.GHngu8ADe',
        'DEMO', 'Salarie', 0);
-- CREATION DES PROJETS DEVS ET SUPPORT
INSERT INTO projet(profilid, nomprojet, etat)
VALUES (1, 'QUESTIONAIRE POUR Administrateur',
        'INPROGRESS'),
       (2, 'QUESTIONAIRE POUR Salarie 1',
        'INPROGRESS'),
       (2, 'QUESTIONAIRE POUR Salarie 2',
        'INPROGRESS');
-- CREATION DES QUESTIONS
INSERT INTO question (intitule, questionpre, typeq, phase,
                      dependance)
VALUES

    /*HORS_PHASE*/
    ('Votre projet nécessite-t-il des trajets en avion ?',null, 'QCM','HORS_PHASE', null),
    ('Combien de km en avion sont effectués pour le projet ?',1,'NUMERIC', 'HORS_PHASE', 1),

    /*PLANIFICATION*/
    ('Veux-tu remplir cette phase?', 2,'QCM', 'PLANIFICATION', null),
    ('Combien de jours dure cette phase ?', 3, 'NUMERIC', 'PLANIFICATION', 3),
    ('Combien de collaborateurs travaillent sur cette phase ?', 4, 'NUMERIC', 'PLANIFICATION', 3),
    ('Combien de jours de télétravail par semaine ?',5, 'NUMERIC', 'PLANIFICATION', 3),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 6, 'QCM', 'PLANIFICATION', 3),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 7, 'NUMERIC', 'PLANIFICATION', 7),
    ('Combien viennent en voiture ?', 8, 'NUMERIC', 'PLANIFICATION', null),
    ('Combien viennent en vélo ou à pied ?', 9, 'NUMERIC', 'PLANIFICATION', null),
    ('Combien viennent en transport en commun ?', 10, 'NUMERIC', 'PLANIFICATION', null),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 11, 'NUMERIC', 'PLANIFICATION', null),
    ('Combien de PC portables utilises-tu pour cette phase ?', 12, 'NUMERIC', 'PLANIFICATION', null),

    /*DEVELOPPEMENT*/
    ('Veux-tu remplir cette phase?', 13,'QCM', 'DEVELOPPEMENT', null),
    ('Combien de jours dure cette phase ?', 14, 'NUMERIC', 'DEVELOPPEMENT', 14),
    ('Combien de collaborateurs travaillent sur cette phase ?', 15, 'NUMERIC', 'DEVELOPPEMENT', 14),
    ('Combien de jours de télétravail par semaine ?',16, 'NUMERIC', 'DEVELOPPEMENT', 14),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 17, 'QCM', 'DEVELOPPEMENT', 14),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 18, 'NUMERIC', 'DEVELOPPEMENT', 18),
    ('Combien viennent en voiture ?', 19, 'NUMERIC', 'DEVELOPPEMENT', null),
    ('Combien viennent en vélo ou à pied ?', 20, 'NUMERIC', 'DEVELOPPEMENT', null),
    ('Combien viennent en transport en commun ?', 21, 'NUMERIC', 'DEVELOPPEMENT', null),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 22, 'NUMERIC', 'DEVELOPPEMENT', null),
    ('Combien de PC portables utilises-tu pour cette phase ?', 23, 'NUMERIC', 'DEVELOPPEMENT', null),
    ('Quel langage utilise tu majoritairement pour le back ?', 24, 'QCM', 'DEVELOPPEMENT', null),
    ('Combien de lignes de code ?', 25, 'NUMERIC', 'DEVELOPPEMENT', 25),
    ('Quel langage utilise tu majoritairement pour le front ?', 26, 'QCM', 'DEVELOPPEMENT', null),
    ('Combien de lignes de code ?', 27, 'NUMERIC', 'DEVELOPPEMENT', 27),

    /*TEST*/
    ('Veux-tu remplir cette phase?', 28,'QCM', 'TEST', null),
    ('Combien de jours dure cette phase ?', 29, 'NUMERIC', 'TEST', 29),
    ('Combien de collaborateurs travaillent sur cette phase ?', 30, 'NUMERIC', 'TEST', 29),
    ('Combien de jours de télétravail par semaine ?',31, 'NUMERIC', 'TEST', 29),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 32, 'QCM', 'TEST', 29),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 33, 'NUMERIC', 'TEST', 33),
    ('Combien viennent en voiture ?', 34, 'NUMERIC', 'TEST', null),
    ('Combien viennent en vélo ou à pied ?', 35, 'NUMERIC', 'TEST', null),
    ('Combien viennent en transport en commun ?', 36, 'NUMERIC', 'TEST', null),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 37, 'NUMERIC', 'TEST', null),
    ('Combien de PC portables utilises-tu pour cette phase ?', 38, 'NUMERIC', 'TEST', null),

    /*DEPLOIEMENT*/
    ('Veux-tu remplir cette phase?', 39,'QCM', 'DEPLOIEMENT', null),
    ('Combien de jours dure cette phase ?', 40, 'NUMERIC', 'DEPLOIEMENT', 40),
    ('Combien de collaborateurs travaillent sur cette phase ?', 41, 'NUMERIC', 'DEPLOIEMENT', 40),
    ('Combien de jours de télétravail par semaine ?',42, 'NUMERIC', 'DEPLOIEMENT', 40),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 43, 'QCM', 'DEPLOIEMENT', 40),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 44, 'NUMERIC', 'DEPLOIEMENT', 44),
    ('Combien viennent en voiture ?', 45, 'NUMERIC', 'DEPLOIEMENT', null),
    ('Combien viennent en vélo ou à pied ?', 46, 'NUMERIC', 'DEPLOIEMENT', null),
    ('Combien viennent en transport en commun ?', 47, 'NUMERIC', 'DEPLOIEMENT', null),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 48, 'NUMERIC', 'DEPLOIEMENT', null),
    ('Combien de PC portables utilises-tu pour cette phase ?', 49, 'NUMERIC', 'DEPLOIEMENT', null),
    ('Utilise-tu un DataCenter ?', 50, 'QCM', 'DEPLOIEMENT', null),
    ('Combien d’énergie votre Datacenter consomme-t-il (en kWh) ?', 51, 'NUMERIC', 'DEPLOIEMENT', 51),
    ('Sais-tu comment est produite l énergie qui alimente majoritairement ton DataCenter ?', 52, 'QCM', 'DEPLOIEMENT', 51),
    ('Quelle énergie alimente majoritairement ton DataCenter ?', 53, 'QCM', 'DEPLOIEMENT', 53),

    /*MAINTENANCE*/
    ('Veux-tu remplir cette phase?', 54,'QCM', 'DEPLOIEMENT', null),
    ('Combien de jours dure cette phase ?', 55, 'NUMERIC', 'DEPLOIEMENT', 55),
    ('Combien de collaborateurs travaillent sur cette phase ?', 56, 'NUMERIC', 'DEPLOIEMENT', 55),
    ('Combien de jours de télétravail par semaine ?',57, 'NUMERIC', 'DEPLOIEMENT', 55),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 58, 'QCM', 'DEPLOIEMENT', 55),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 59, 'NUMERIC', 'DEPLOIEMENT', 59),
    ('Combien viennent en voiture ?', 60, 'NUMERIC', 'DEPLOIEMENT', null),
    ('Combien viennent en vélo ou à pied ?', 61, 'NUMERIC', 'DEPLOIEMENT', null),
    ('Combien viennent en transport en commun ?', 62, 'NUMERIC', 'DEPLOIEMENT', null),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 63, 'NUMERIC', 'DEPLOIEMENT', null),
    ('Combien de PC portables utilises-tu pour cette phase ?', 64, 'NUMERIC', 'DEPLOIEMENT', null);

--- CREATION DES CONSTANTES
INSERT INTO constante (constante, tracabilite)
VALUES (1, 'Constante neutre'),

--- CREATION DES REPONSES POSSIBLE
INSERT INTO reponsepossible (questionasso, questionsuiv, intitule,
                             constanteid)
VALUES

    /*HORS_PHASE*/
    (1, 2, 'OUI', 1),
    (1, 3, 'NON', 1),
    (2, 3, 'Veuillez entrer un entier', 1),

    /*PLANIFICATION*/
    (3, 4, 'OUI', 1),
    (3, 14, 'NON', 1),
    (4, 5, 'Veuillez entrer un entier', 1),
    (5, 6, 'Veuillez entrer un entier', 1),

    (6, 7, 'Veuillez entrer un entier', 2),
    (7, 8, 'OUI', 2),
    (7, 9, 'NON', 2),
    (8, 9, 'Veuillez entrer un entier', 2),
    (9, 10, 'Veuillez entrer un entier', 2),
    (10, 11, 'Veuillez entrer un entier', 2),
    (11, 12, 'Veuillez entrer un entier', 2),
    (12, 13, 'Veuillez entrer un entier', 2),
    (13, 14, 'Veuillez entrer un entier', 2),

    /*DEVELOPPEMENT*/
    (14, 15, 'OUI', 2),
    (14, 29, 'NON', 2),
    (15, 16, 'Veuillez entrer un entier', 2),
    (16, 17, 'Veuillez entrer un entier', 2),
    (17, 18, 'Veuillez entrer un entier', 2),
    (18, 19, 'OUI', 2),
    (18, 19, 'NON', 2),
    (19, 20, 'Veuillez entrer un entier', 2),
    (20, 21, 'Veuillez entrer un entier', 2),
    (21, 22, 'Veuillez entrer un entier', 2),
    (22, 23, 'Veuillez entrer un entier', 2),
    (23, 24, 'Veuillez entrer un entier', 2),
    (24, 25, 'Veuillez entrer un entier', 2),
    (25, 26, 'C', 2),
    (25, 26, 'C++', 2),
    (25, 26, 'Java', 2),
    (25, 26, 'Python', 2),
    (25, 26, 'Autre', 2),
    (26, 27, 'Veuillez entrer un entier', 2),
    (27, 28, 'PHP', 2),
    (27, 28, 'JavaScript', 2),
    (27, 28, 'TypeScript', 2),
    (27, 28, 'Autre', 2),
    (28, 29, 'Veuillez entrer un entier', 2),

    /*TEST*/
    (29, 30, 'OUI', 2),
    (29, 40, 'NON', 2),
    (30, 31, 'Veuillez entrer un entier', 2),
    (31, 32, 'Veuillez entrer un entier', 2),
    (32, 33, 'Veuillez entrer un entier', 2),
    (33, 34, 'OUI', 2),
    (33, 35, 'NON', 2),
    (34, 35, 'Veuillez entrer un entier', 2),
    (35, 36, 'Veuillez entrer un entier', 2),
    (36, 37, 'Veuillez entrer un entier', 2),
    (37, 38, 'Veuillez entrer un entier', 2),
    (38, 39, 'Veuillez entrer un entier', 2),
    (39, 40, 'Veuillez entrer un entier', 2),

    /*DEPLOIEMENT*/
    (40, 41, 'OUI', 2),
    (40, 55, 'NON', 2),
    (41, 42, 'Veuillez entrer un entier', 2),
    (42, 43, 'Veuillez entrer un entier', 2),
    (43, 44, 'Veuillez entrer un entier', 2),
    (44, 45, 'OUI', 2),
    (44, 46, 'NON', 2),
    (45, 46, 'Veuillez entrer un entier', 2),
    (46, 47, 'Veuillez entrer un entier', 2),
    (47, 48, 'Veuillez entrer un entier', 2),
    (48, 49, 'Veuillez entrer un entier', 2),
    (49, 50, 'Veuillez entrer un entier', 2),
    (50, 51, 'Veuillez entrer un entier', 2),
    (51, 52, 'OUI', 2),
    (51, 55, 'NON', 2),
    (52, 53, 'Veuillez entrer un entier', 2),
    (53, 54, 'OUI', 2),
    (53, 55, 'NON', 2),
    (54, 55, 'NUCLEAIRE', 2),
    (54, 55, 'HYDRAULIQUE', 2),
    (54, 55, 'CHARBON', 2),
    (54, 55, 'GEOTHERMIQUE', 2),
    (54, 55, 'FIOUL', 2),
    (54, 55, 'ENERGIE RENOUVELABLE', 2),

    /*MAINTENANCE*/
    (55, 56, 'OUI', 2),
    (55, null, 'NON', 2),
    (56, 57, 'Veuillez entrer un entier', 2),
    (57, 58, 'Veuillez entrer un entier', 2),
    (58, 59, 'Veuillez entrer un entier', 2),
    (59, 60, 'OUI', 2),
    (59, 60, 'NON', 2),
    (60, 61, 'Veuillez entrer un entier', 2),
    (61, 62, 'Veuillez entrer un entier', 2),
    (62, 63, 'Veuillez entrer un entier', 2),
    (63, 64, 'Veuillez entrer un entier', 2),
    (64, 65, 'Veuillez entrer un entier', 2),
    (65, null, 'Veuillez entrer un entier', 2),
--CREATION REPONSEDONNEE
INSERT INTO reponsedonnee
VALUES (1, 1, 30),
       (1, 2, 1),
       (1, 4, 3),
       (1, 6, 10),
       (1, 7, 15);
--CREATION CALCUL TEST
INSERT INTO calcul(calculopid, reponsepossibleid, nbcalcul,priorite,
                   phase)
VALUES (5, 11, 1, 'DEVELOPPEMENT'),
       (1, 1, 2, 'DEVELOPPEMENT'),
       (3, 2, 2, 'DEVELOPPEMENT'),
       (5, 4, 2, 'DEVELOPPEMENT'),
       (3, 6, 3, 'DEVELOPPEMENT'),
       (5, 7, 3, 'DEVELOPPEMENT');
