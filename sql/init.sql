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
    categorie   VARCHAR(50)  NOT NULL,
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
                      categorie, dependance)
VALUES

    /*HORS_PHASE*/
    ('Votre projet, nécessite-t-il des trajets en avion ?',
     null, 'QCM', 'HORS_PHASE', 'FIRST',
     null),
    ('Combien ?', 1, 'NUMERIC', 'HORS_PHASE',
     'FIRST', 1),
    ('Produisez-vous de l’énergie verte ?',
     2, 'QCM', 'HORS_PHASE', 'FIRST', null),
    ('A quel pourcentage couvre t-elle vos dépenses énergétiques',
     3, 'NUMERIC', 'HORS_PHASE', 'FIRST',
     3),
    ('Combien de jours de télétravail par semaine ?',
     4, 'NUMERIC', 'HORS_PHASE', 'FIRST',
     null),

    /*PLANIFICATION*/
    ('Combien de collaborateurs travaillent sur cette phase ?',
     5, 'NUMERIC', 'PLANIFICATION', 'FIRST',
     null),
    ('Connaissez-vous à peu près la distance de trajet de vos collaborateurs ?',
     6, 'QCM', 'PLANIFICATION', 'FIRST',
     null),
    ('Quelle est la distance moyenne de trajet de vos collaborateurs pour aller au travail ?',
     7, 'NUMERIC', 'PLANIFICATION', 'FIRST',
     7),
    ('Combien viennent en voiture ?',
     8, 'NUMERIC', 'PLANIFICATION', 'FIRST',
     null),
    ('Combien de pages de document avez-vous utilisé ?',
     9, 'NUMERIC', 'PLANIFICATION', 'FIRST',
     null),
    ('Dématérialisez-vous vos documents ?',
     10, 'QCM', 'PLANIFICATION', 'FIRST',
     null),
    ('Quel pourcentage de vos documents est dématérialisé?',
     11, 'NUMERIC', 'PLANIFICATION', 'FIRST',
     11),

    /*DEVELOPPEMENT*/
    ('Combien de collaborateurs travaillent sur cette phase ?',
     12, 'NUMERIC', 'DEVELOPPEMENT', 'FIRST',
     null),
    ('Connaissez-vous à peu près la distance de trajet de vos collaborateurs ?',
     13, 'QCM', 'DEVELOPPEMENT', 'FIRST',
     null),
    ('Quelle est la distance moyenne de trajet de vos collaborateurs pour aller au travail ?',
     14, 'NUMERIC', 'DEVELOPPEMENT', 'FIRST',
     14),
    ('Combien viennent en voiture ?',
     15, 'NUMERIC', 'DEVELOPPEMENT', 'FIRST',
     null),
    ('Combien de PC utilisez-vous pour cette phase ?',
     16, 'NUMERIC', 'DEVELOPPEMENT', 'FIRST',
     null),
    ('Utilisez-vous des machines virtuelles ?',
     17, 'QCM', 'DEVELOPPEMENT', 'FIRST',
     null),
    ('Combien ?', 18, 'NUMERIC', 'DEVELOPPEMENT',
     'FIRST', 17),
    ('Quel langage utilisez-vous pour développer en Back ?',
     19, 'QCM', 'DEVELOPPEMENT', 'FIRST',
     null),
    ('Quel langage utilisez-vous pour développer en Front ?',
     20, 'QCM', 'DEVELOPPEMENT', 'FIRST',
     null),

    /*TEST*/
    ('Combien de collaborateurs travaillent sur cette phase ?',
     21, 'NUMERIC', 'TEST', 'FIRST', null),
    ('Connaissez-vous à peu prés la distance de trajet de vos collaborateurs ?',
     22, 'QCM', 'TEST', 'FIRST', null),
    ('Quelle est la distance moyenne de trajet de vos collaborateurs pour aller au travail ?',
     23, 'NUMERIC', 'TEST', 'FIRST', 23),
    ('Combien viennent en voiture ?',
     24, 'NUMERIC', 'TEST', 'FIRST', null),
    ('Combien de PC utilisez-vous pour cette phase ?',
     25, 'NUMERIC', 'TEST', 'FIRST', null),

    /*DEPLOIEMENT*/
    ('Combien de collaborateurs travaillent sur cette phase ?',
     26, 'NUMERIC', 'DEPLOIEMENT', 'FIRST',
     null),
    ('Connaissez-vous à peu près la distance de trajet de vos collaborateurs ?',
     27, 'QCM', 'DEPLOIEMENT', 'FIRST',
     null),
    ('Quelle est la distance moyenne de trajet de vos collaborateurs pour aller au travail ?',
     28, 'NUMERIC', 'DEPLOIEMENT', 'FIRST',
     28),
    ('Combien viennent en voiture ?',
     29, 'NUMERIC', 'DEPLOIEMENT', 'FIRST',
     null),
    ('Combien de PC utilisez-vous pour cette phase ?',
     30, 'NUMERIC', 'DEPLOIEMENT', 'FIRST',
     null),
    ('Avez-vous une base de données ?',
     31, 'QCM', 'DEPLOIEMENT', 'FIRST',
     null),
    ('Quelle taille fait-elle ?', 32,
     'NUMERIC', 'DEPLOIEMENT', 'FIRST',
     32),
    ('Est-elle stockée dans le cloud ?',
     33, 'QCM', 'DEPLOIEMENT', 'FIRST',
     32),
    ('Savez-vous quelle énergie alimente votre cloud ?',
     34, 'QCM', 'DEPLOIEMENT', 'FIRST',
     34),
    ('Citez quelle énergie alimente votre Cloud ?',
     35, 'QCM', 'DEPLOIEMENT', 'FIRST',
     34),

    /*MAINTENANCE*/
    ('Faites-vous une phase de maintenance ?',
     36, 'QCM', 'MAINTENANCE', 'FIRST',
     null),
    ('Combien de collaborateurs travaillent sur cette phase ?',
     37, 'NUMERIC', 'MAINTENANCE', 'FIRST',
     37),
    ('Connaissez-vous à peu près la distance de trajet de vos collaborateurs ?',
     38, 'QCM', 'MAINTENANCE', 'FIRST',
     37),
    ('Quelle est la distance moyenne de trajet de vos collaborateurs pour aller au travail ?',
     39, 'NUMERIC', 'MAINTENANCE', 'FIRST',
     39),
    ('Combien viennent en voiture ?',
     37, 'NUMERIC', 'MAINTENANCE', 'FIRST',
     null);
--- CREATION DES CONSTANTES
INSERT INTO constante (constante, tracabilite)
VALUES (0, 'Constante neutre de l addition et la soustraction'),
       (1, 'Constante neutre pour la multiplication et la division'),
       (3.83,
        'https://docs.google.com/document/d/1vlv_qH2_NvRNO2uHRH93mogf9ENM6OsNc3a3wXjNaR8/edit#heading=h.72nwi8b72wp2'),
       (0.103,
        'Consommation moyenne d une voiture en KG de CO2/KM : https://carlabelling.ademe.fr/chiffrescles/r/evolutionTauxCo2');
--- CREATION DES REPONSES POSSIBLE
INSERT INTO reponsepossible (questionasso, questionsuiv, intitule,
                             constanteid)
VALUES

    /*HORS_PHASE*/
    (1, 2, 'OUI', 2),
    (1, 3, 'NON', 2),
    (2, 3, 'Veuillez entrer un entier',
     2),
    (3, 4, 'OUI', 2),
    (3, 5, 'NON', 2),
    (4, 5, 'Veuillez entrer un entier',
     2),
    (5, 6, 'Veuillez entrer un entier',
     2),

    /*PLANIFICATION*/
    (6, 7, 'Veuillez entrer un entier',
     2),
    (7, 8, 'OUI', 2),
    (7, 9, 'NON', 2),
    (8, 9, 'Veuillez entrer un entier',
     2),
    (9, 10, 'Veuillez entrer un entier',
     2),
    (10, 11, 'Veuillez entrer un entier',
     2),
    (11, 12, 'OUI', 2),
    (11, 13, 'NON', 2),
    (12, 13, 'Veuillez entrer un entier',
     2),

    /*DEVELOPPEMENT*/
    (13, 14, 'Veuillez entrer un entier',
     2),
    (14, 15, 'OUI', 2),
    (14, 16, 'NON', 2),
    (15, 16, 'Veuillez entrer un entier',
     2),
    (16, 17, 'Veuillez entrer un entier',
     2),
    (17, 18, 'Veuillez entrer un entier',
     2),
    (18, 19, 'OUI', 2),
    (18, 19, 'NON', 2),
    (19, 20, 'Veuillez entrer un entier', 2),
    (20, 21, 'C', 2),
    (20, 21, 'C++', 2),
    (20, 21, 'Java', 2),
    (20, 21, 'Python', 2),
    (20, 21, 'Autre', 2),
    (21, 22, 'PHP', 2),
    (21, 22, 'JavaScript', 2),
    (21, 22, 'TypeScript', 2),
    (21, 22, 'Autre', 2),

    /*TEST*/
    (22, 23, 'Veuillez entrer un entier', 2),
    (23, 24, 'OUI', 2),
    (23, 25, 'NON', 2),
    (24, 25, 'Veuillez entrer un entier',
     2),
    (25, 26, 'Veuillez entrer un entier',
     2),
    (26, 27, 'Veuillez entrer un entier',
     2),

    /*DEPLOIEMENT*/
    (27, 28, 'Veuillez entrer un entier',
     2),
    (28, 29, 'OUI', 2),
    (28, 30, 'NON', 2),
    (29, 30, 'Veuillez entrer un entier',
     2),
    (30, 31, 'Veuillez entrer un entier',
     2),
    (31, 32, 'Veuillez entrer un entier',
     2),
    (32, 33, 'OUI', 2),
    (32, 36, 'NON', 2),
    (33, 34, 'Veuillez entrer un entier',
     2),
    (34, 35, 'OUI', 2),
    (34, 36, 'NON', 2),
    (35, 36, 'NUCLEAIRE', 2),
    (35, 36, 'HYDRAULIQUE', 2),
    (35, 36, 'CHARBON', 2),
    (35, 36, 'GEOTHERMIQUE', 2),
    (35, 36, 'ENERGIE RENOUVELABLE', 2),

    /*MAINTENANCE*/
    (37, 38, 'OUI', 2),
    (37, null, 'NON', 2),
    (38, 39, 'Veuillez entrer un entier',
     2),
    (39, 40, 'OUI', 2),
    (39, 41, 'NON', 2),
    (40, 41, 'Veuillez entrer un entier',
     2),
    (41, null, 'Veuillez entrer un entier',
     2);
--CREATION REPONSEDONNEE
INSERT INTO reponsedonnee
VALUES (1, 1, 30),
       (1, 2, 1),
       (1, 4, 3),
       (1, 6, 10),
       (1, 7, 15);
--CREATION CALCUL TEST
INSERT INTO calcul(calculopid, reponsepossibleid, nbcalcul,
                   phase)
VALUES (5, 11, 1, 'DEVELOPPEMENT'),
       (1, 1, 2, 'DEVELOPPEMENT'),
       (3, 2, 2, 'DEVELOPPEMENT'),
       (5, 4, 2, 'DEVELOPPEMENT'),
       (3, 6, 3, 'DEVELOPPEMENT'),
       (5, 7, 3, 'DEVELOPPEMENT');
