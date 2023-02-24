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
    CONSTRAINT questionpre FOREIGN KEY (questionpre) REFERENCES question (idquestion)
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

--ALTER QUESTION

ALTER TABLE question
    ADD CONSTRAINT dependance FOREIGN KEY(dependance) REFERENCES reponsepossible (idreponsepos);

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
INSERT INTO question (intitule, questionpre, typeq, phase)
VALUES

    /*HORS_PHASE*/
    ('Votre projet nécessite-t-il des trajets en avion ?',null, 'QCM','HORS_PHASE'),
    ('Combien de km en avion sont effectués pour le projet ?',1,'NUMERIC', 'HORS_PHASE'),

    /*PLANIFICATION*/
    ('Veux-tu remplir cette phase?', 2,'QCM', 'PLANIFICATION'),
    ('Combien de jours dure cette phase ?', 3, 'NUMERIC', 'PLANIFICATION'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 4, 'NUMERIC', 'PLANIFICATION'),
    ('Combien de jours de télétravail vos collaborateur ont pour cette phase ?',5, 'NUMERIC', 'PLANIFICATION'),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 6, 'QCM', 'PLANIFICATION'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 7, 'NUMERIC', 'PLANIFICATION'),
    ('Combien viennent en voiture ?', 8, 'NUMERIC', 'PLANIFICATION'),
    ('Combien viennent en vélo ou à pied ?', 9, 'NUMERIC', 'PLANIFICATION'),
    ('Combien viennent en transport en commun ?', 10, 'NUMERIC', 'PLANIFICATION'),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 11, 'NUMERIC', 'PLANIFICATION'),
    ('Combien de PC portables utilises-tu pour cette phase ?', 12, 'NUMERIC', 'PLANIFICATION'),

    /*DEVELOPPEMENT*/
    ('Veux-tu remplir cette phase?', 13,'QCM', 'DEVELOPPEMENT'),
    ('Combien de jours dure cette phase ?', 14, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 15, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien de jours de télétravail vos collaborateur ont pour cette phase ?',16, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 17, 'QCM', 'DEVELOPPEMENT'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 18, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien viennent en voiture ?', 19, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien viennent en vélo ou à pied ?', 20, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien viennent en transport en commun ?', 21, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 22, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien de PC portables utilises-tu pour cette phase ?', 23, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Quel langage utilise tu majoritairement pour le back ?', 24, 'QCM', 'DEVELOPPEMENT'),
    ('Combien de lignes de code ?', 25, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Quel langage utilise tu majoritairement pour le front ?', 26, 'QCM', 'DEVELOPPEMENT'),
    ('Combien de lignes de code ?', 27, 'NUMERIC', 'DEVELOPPEMENT'),

    /*TEST*/
    ('Veux-tu remplir cette phase?', 28,'QCM', 'TEST'),
    ('Combien de jours dure cette phase ?', 29, 'NUMERIC', 'TEST'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 30, 'NUMERIC', 'TEST'),
    ('Combien de jours de télétravail vos collaborateur ont pour cette phase ?',31, 'NUMERIC', 'TEST'),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 32, 'QCM', 'TEST'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 33, 'NUMERIC', 'TEST'),
    ('Combien viennent en voiture ?', 34, 'NUMERIC', 'TEST'),
    ('Combien viennent en vélo ou à pied ?', 35, 'NUMERIC', 'TEST'),
    ('Combien viennent en transport en commun ?', 36, 'NUMERIC', 'TEST'),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 37, 'NUMERIC', 'TEST'),
    ('Combien de PC portables utilises-tu pour cette phase ?', 38, 'NUMERIC', 'TEST'),

    /*DEPLOIEMENT*/
    ('Veux-tu remplir cette phase?', 39,'QCM', 'DEPLOIEMENT'),
    ('Combien de jours dure cette phase ?', 40, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 41, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de jours de télétravail vos collaborateur ont pour cette phase ?',42, 'NUMERIC', 'DEPLOIEMENT'),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 43, 'QCM', 'DEPLOIEMENT'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 44, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien viennent en voiture ?', 45, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien viennent en vélo ou à pied ?', 46, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien viennent en transport en commun ?', 47, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 48, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de PC portables utilises-tu pour cette phase ?', 49, 'NUMERIC', 'DEPLOIEMENT'),
    ('Utilise-tu un DataCenter ?', 50, 'QCM', 'DEPLOIEMENT'),
    ('Combien d’énergie votre Datacenter consomme-t-il (en kWh) ?', 51, 'NUMERIC', 'DEPLOIEMENT'),
    ('Sais-tu comment est produite l énergie qui alimente majoritairement ton DataCenter ?', 52, 'QCM', 'DEPLOIEMENT'),
    ('Quelle énergie alimente majoritairement ton DataCenter ?', 53, 'QCM', 'DEPLOIEMENT'),

    /*MAINTENANCE*/
    ('Veux-tu remplir cette phase?', 54,'QCM', 'DEPLOIEMENT'),
    ('Combien de jours dure cette phase ?', 55, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 56, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de jours de télétravail vos collaborateur ont pour cette phase ?',57, 'NUMERIC', 'DEPLOIEMENT'),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 58, 'QCM', 'DEPLOIEMENT'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 59, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien viennent en voiture ?', 60, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien viennent en vélo ou à pied ?', 61, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien viennent en transport en commun ?', 62, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 63, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de PC portables utilises-tu pour cette phase ?', 64, 'NUMERIC', 'DEPLOIEMENT');

--- CREATION DES CONSTANTES
INSERT INTO constante (constante, tracabilite)
VALUES          (0, 'Constante neutre'),
                (1, 'Constante neutre'),
                (0.178, 'https://datagir.ademe.fr/apps/mon-impact-transport/ '),
 /*C*/          (1, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langage. C étant la valeur 1'),
 /*C++*/        (1.34 , 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langage. C étant la valeur 1'),
 /*Java*/       (1.98, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langage. C étant la valeur 1'),
 /*Python*/     (75.88, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langage. C étant la valeur 1'),
 /*Moyenne*/    (16.12, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langage. C étant la valeur 1'),
 /*PHP*/        (29.30, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langage. C étant la valeur 1'),
 /*JavaScript*/ (4.45, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langage. C étant la valeur 1'),
 /*TypeScript*/ (21.50, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langage. C étant la valeur 1'),
                (0.2, 'Voiture par kilometre, https://datagir.ademe.fr/apps/mon-impact-transport/'),
                (0.0168, 'Transport en commun, https://datagir.ademe.fr/apps/mon-impact-transport/'),
                (7.66, 'Moyenne des transport d un salarié en une journée,https://culture-rh.com/empreinte-carbone-salarie/'),
                (0.67, 'Empreinte carbonne d un ordinateur fixe pour 1 journée, https://impactco2.fr/usagenumerique '),
                (0.25, 'Empreinte carbonne d un ordinateur portable pour 1 journée, https://impactco2.fr/usagenumerique '),
                (0.006, 'Émissions de CO2 (kgCO2e/kWh) du nucleaire, https://climate.selectra.com/fr/empreinte-carbone/energie'),
                (0.025, 'Émissions de CO2 (kgCO2e/kWh) de energie renouvlable, https://climate.selectra.com/fr/empreinte-carbone/energie'),
                (0.6515, 'Émissions de CO2 (kgCO2e/kWh) de energie fossile, https://climate.selectra.com/fr/empreinte-carbone/energie');




--- CREATION DES REPONSES POSSIBLE
INSERT INTO reponsepossible (questionasso, questionsuiv, intitule,
                             constanteid)
VALUES

    /*HORS_PHASE*/
    (1, 2, 'OUI', 2),
    (1, 3, 'NON', 2),
    (2, 3, 'Veuillez entrer un entier', 3),

    /*PLANIFICATION*/
    (3, 4, 'OUI', 2),
    (3, 14, 'NON', 2),
    (4, 5, 'Veuillez entrer un entier', 2),
    (5, 6, 'Veuillez entrer un entier', 14),
    (6, 7, 'Veuillez entrer un entier', 2),
    (7, 8, 'OUI', 2),
    (7, 9, 'NON', 2),
    (8, 9, 'Veuillez entrer un entier', 2),
    (9, 10, 'Veuillez entrer un entier', 12),
    (10, 11, 'Veuillez entrer un entier', 2),
    (11, 12, 'Veuillez entrer un entier', 13),
    (12, 13, 'Veuillez entrer un entier', 15),
    (13, 14, 'Veuillez entrer un entier', 16),

    /*DEVELOPPEMENT*/
    (14, 15, 'OUI', 2),
    (14, 29, 'NON', 2),
    (15, 16, 'Veuillez entrer un entier', 2),
    (16, 17, 'Veuillez entrer un entier', 14),
    (17, 18, 'Veuillez entrer un entier', 2),
    (18, 19, 'OUI', 2),
    (18, 19, 'NON', 2),
    (19, 20, 'Veuillez entrer un entier', 2),
    (20, 21, 'Veuillez entrer un entier', 12),
    (21, 22, 'Veuillez entrer un entier', 2),
    (22, 23, 'Veuillez entrer un entier', 13),
    (23, 24, 'Veuillez entrer un entier', 15),
    (24, 25, 'Veuillez entrer un entier', 16),
    (25, 26, 'C', 3),
    (25, 26, 'C++', 4),
    (25, 26, 'Java', 5),
    (25, 26, 'Python', 6),
    (25, 26, 'Autre', 7),
    (26, 27, 'Veuillez entrer un entier', 2),
    (27, 28, 'PHP', 8),
    (27, 28, 'JavaScript', 9),
    (27, 28, 'TypeScript', 10),
    (27, 28, 'Autre', 7),
    (28, 29, 'Veuillez entrer un entier', 2),

    /*TEST*/
    (29, 30, 'OUI', 2),
    (29, 40, 'NON', 2),
    (30, 31, 'Veuillez entrer un entier', 2),
    (31, 32, 'Veuillez entrer un entier', 14),
    (32, 33, 'Veuillez entrer un entier', 2),
    (33, 34, 'OUI', 2),
    (33, 35, 'NON', 2),
    (34, 35, 'Veuillez entrer un entier', 2),
    (35, 36, 'Veuillez entrer un entier', 12),
    (36, 37, 'Veuillez entrer un entier', 2),
    (37, 38, 'Veuillez entrer un entier', 13),
    (38, 39, 'Veuillez entrer un entier', 15),
    (39, 40, 'Veuillez entrer un entier', 16),

    /*DEPLOIEMENT*/
    (40, 41, 'OUI', 2),
    (40, 55, 'NON', 2),
    (41, 42, 'Veuillez entrer un entier', 2),
    (42, 43, 'Veuillez entrer un entier', 14),
    (43, 44, 'Veuillez entrer un entier', 2),
    (44, 45, 'OUI', 2),
    (44, 46, 'NON', 2),
    (45, 46, 'Veuillez entrer un entier', 2),
    (46, 47, 'Veuillez entrer un entier', 12),
    (47, 48, 'Veuillez entrer un entier', 2),
    (48, 49, 'Veuillez entrer un entier', 13),
    (49, 50, 'Veuillez entrer un entier', 15),
    (50, 51, 'Veuillez entrer un entier', 16),
    (51, 52, 'OUI', 2),
    (51, 55, 'NON', 2),
    (52, 53, 'Veuillez entrer un entier', 2),
    (53, 54, 'OUI', 2),
    (53, 55, 'NON', 2),
    (54, 55, 'NUCLEAIRE', 17),
    (54, 55, 'FOSSILE', 19),
    (54, 55, 'ENERGIE RENOUVELABLE', 18),

    /*MAINTENANCE*/
    (55, 56, 'OUI', 2),
    (55, null, 'NON', 2),
    (56, 57, 'Veuillez entrer un entier', 2),
    (57, 58, 'Veuillez entrer un entier', 14),
    (58, 59, 'Veuillez entrer un entier', 2),
    (59, 60, 'OUI', 2),
    (59, 60, 'NON', 2),
    (60, 61, 'Veuillez entrer un entier', 2),
    (61, 62, 'Veuillez entrer un entier', 12),
    (62, 63, 'Veuillez entrer un entier', 2),
    (63, 64, 'Veuillez entrer un entier', 13),
    (64, 65, 'Veuillez entrer un entier', 15),
    (65, null, 'Veuillez entrer un entier', 16);


--CREATION CALCUL
INSERT INTO calcul(calculopid, reponsepossibleid, nbcalcul,priorite,
                   phase)
VALUES  (5, 3, 1, 1, 'HORS_PHASE'),

        (3, 12, 2, 1, 'PLANIFICATION'),--a*
        (3, 11, 2, 1, 'PLANIFICATION'),--y*
        (1, 6, 2, 1, 'PLANIFICATION'),--z+
        (3, 14, 2, 1, 'PLANIFICATION'),--c*
        (3, 11, 2, 1, 'PLANIFICATION'),--y*
        (2, 6, 2, 1, 'PLANIFICATION'),--z-
        (3, 8, 2, 1, 'PLANIFICATION'),--x*
        (3, 12, 2, 1, 'PLANIFICATION'),--a*
        (2, 11, 2, 1, 'PLANIFICATION'),--y-
        (3, 8, 2, 1, 'PLANIFICATION'),--x*
        (3, 14, 2, 1, 'PLANIFICATION'),--c*
        (5, 11, 2, 1, 'PLANIFICATION'),--yf

        (3, 12, 2, 2, 'PLANIFICATION'),--a*
        (3, 11, 2, 2, 'PLANIFICATION'),--y*
        (1, 6, 2, 2, 'PLANIFICATION'),--z+
        (3, 14, 2, 2, 'PLANIFICATION'),--c*
        (3, 11, 2, 2, 'PLANIFICATION'),--y*
        (5, 6, 2, 2, 'PLANIFICATION'),--zf

        (3, 7, 2, 3, 'PLANIFICATION'),
        (5, 6, 2, 3, 'PLANIFICATION'),

        (3, 15, 3, 1, 'PLANIFICATION'),
        (5, 6, 3, 1, 'PLANIFICATION'),

        (3, 16, 4, 1, 'PLANIFICATION'),
        (5, 6, 4, 1, 'PLANIFICATION'),

        (3, 26, 5, 1, 'DEVELOPPEMENT'),--a*
        (3, 25, 5, 1, 'DEVELOPPEMENT'),--y*
        (1, 19, 5, 1, 'DEVELOPPEMENT'),--z+
        (3, 28, 5, 1, 'DEVELOPPEMENT'),--c*
        (3, 25, 5, 1, 'DEVELOPPEMENT'),--y*
        (2, 19, 5, 1, 'DEVELOPPEMENT'),--z-
        (3, 21, 5, 1, 'DEVELOPPEMENT'),--x*
        (3, 26, 5, 1, 'DEVELOPPEMENT'),--a*
        (2, 25, 5, 1, 'DEVELOPPEMENT'),--y-
        (3, 21, 5, 1, 'DEVELOPPEMENT'),--x*
        (3, 28, 5, 1, 'DEVELOPPEMENT'),--c*
        (5, 25, 5, 1, 'DEVELOPPEMENT'),--yf

        (3, 26, 5, 2, 'DEVELOPPEMENT'),--a*
        (3, 25, 5, 2, 'DEVELOPPEMENT'),--y*
        (1, 19, 5, 2, 'DEVELOPPEMENT'),--z+
        (3, 28, 5, 2, 'DEVELOPPEMENT'),--c*
        (3, 25, 5, 2, 'DEVELOPPEMENT'),--y*
        (5, 19, 5, 2, 'DEVELOPPEMENT'),--zf

        (3, 20, 5, 3, 'DEVELOPPEMENT'),
        (5, 19, 5, 3, 'DEVELOPPEMENT'),

        (3, 29, 6, 1, 'DEVELOPPEMENT'),
        (5, 19, 6, 1, 'DEVELOPPEMENT'),

        (3, 30, 7, 1, 'DEVELOPPEMENT'),
        (5, 19, 7, 1, 'DEVELOPPEMENT'),

        (3, 31, 8, 1, 'DEVELOPPEMENT'),
        (5, 36, 8, 1, 'DEVELOPPEMENT'),

        (3, 32, 9, 1, 'DEVELOPPEMENT'),
        (5, 36, 9, 1, 'DEVELOPPEMENT'),

        (3, 33, 10, 1, 'DEVELOPPEMENT'),
        (5, 36, 10, 1, 'DEVELOPPEMENT'),

        (3, 34, 11, 1, 'DEVELOPPEMENT'),
        (5, 36, 11, 1, 'DEVELOPPEMENT'),

        (3, 35, 12, 1, 'DEVELOPPEMENT'),
        (5, 36, 12, 1, 'DEVELOPPEMENT'),

        (3, 37, 13, 1, 'DEVELOPPEMENT'),
        (5, 41, 13, 1, 'DEVELOPPEMENT'),

        (3, 38, 14, 1, 'DEVELOPPEMENT'),
        (5, 41, 14, 1, 'DEVELOPPEMENT'),

        (3, 39, 15, 1, 'DEVELOPPEMENT'),
        (5, 41, 15, 1, 'DEVELOPPEMENT'),

        (3, 40, 16, 1, 'DEVELOPPEMENT'),
        (5, 41, 16, 1, 'DEVELOPPEMENT'),

        (3, 50, 10, 1, 'TEST'),
        (3, 49, 10, 1, 'TEST'),
        (1, 44, 10, 1, 'TEST'),
        (3, 52, 10, 1, 'TEST'),
        (3, 49, 10, 1, 'TEST'),
        (2, 44, 10, 1, 'TEST'),
        (3, 46, 10, 1, 'TEST'),
        (3, 50, 10, 1, 'TEST'),
        (2, 49, 10, 1, 'TEST'),
        (3, 46, 10, 1, 'TEST'),
        (3, 52, 10, 1, 'TEST'),
        (5, 49, 10, 1, 'TEST'),

        (3, 50, 10, 2, 'TEST'),
        (3, 49, 10, 2, 'TEST'),
        (1, 44, 10, 2, 'TEST'),
        (3, 52, 10, 2, 'TEST'),
        (3, 49, 10, 2, 'TEST'),
        (5, 44, 10, 2, 'TEST'),

        (3, 45, 10, 3, 'TEST'),
        (5, 44, 10, 3, 'TEST'),

        (3, 53, 11, 1, 'TEST'),
        (5, 44, 11, 1, 'TEST'),

        (3, 54, 12, 1, 'TEST'),
        (5, 44, 12, 1, 'TEST'),

        (3, 63, 13, 1, 'DEPLOIEMENT'),
        (3, 62, 13, 1, 'DEPLOIEMENT'),
        (1, 57, 13, 1, 'DEPLOIEMENT'),
        (3, 65, 13, 1, 'DEPLOIEMENT'),
        (3, 62, 13, 1, 'DEPLOIEMENT'),
        (2, 57, 13, 1, 'DEPLOIEMENT'),
        (3, 59, 13, 1, 'DEPLOIEMENT'),
        (3, 63, 13, 1, 'DEPLOIEMENT'),
        (2, 62, 13, 1, 'DEPLOIEMENT'),
        (3, 59, 13, 1, 'DEPLOIEMENT'),
        (3, 65, 13, 1, 'DEPLOIEMENT'),
        (5, 62, 13, 1, 'DEPLOIEMENT'),

        (3, 63, 13, 2, 'DEPLOIEMENT'),
        (3, 62, 13, 2, 'DEPLOIEMENT'),
        (1, 57, 13, 2, 'DEPLOIEMENT'),
        (3, 65, 13, 2, 'DEPLOIEMENT'),
        (3, 62, 13, 2, 'DEPLOIEMENT'),
        (5, 57, 13, 2, 'DEPLOIEMENT'),

        (3, 58, 13, 3, 'DEPLOIEMENT'),
        (5, 57, 13, 3, 'DEPLOIEMENT'),

        (3, 66, 14, 1, 'DEPLOIEMENT'),
        (5, 57, 14, 1, 'DEPLOIEMENT'),

        (3, 67, 15, 1, 'DEPLOIEMENT'),
        (5, 57, 15, 1, 'DEPLOIEMENT'),

        (3, 58, 16, 1, 'DEPLOIEMENT'),
        (5, 73, 16, 1, 'DEPLOIEMENT'),

        (3, 58, 16, 1, 'DEPLOIEMENT'),
        (5, 74, 16, 1, 'DEPLOIEMENT'),

        (3, 58, 16, 1, 'DEPLOIEMENT'),
        (5, 75, 16, 1, 'DEPLOIEMENT'),

        (3, 82, 17, 1, 'MAINTENANCE'),
        (3, 81, 17, 1, 'MAINTENANCE'),
        (1, 76, 17, 1, 'MAINTENANCE'),
        (3, 84, 17, 1, 'MAINTENANCE'),
        (3, 81, 17, 1, 'MAINTENANCE'),
        (2, 76, 17, 1, 'MAINTENANCE'),
        (3, 78, 17, 1, 'MAINTENANCE'),
        (3, 82, 17, 1, 'MAINTENANCE'),
        (2, 81, 17, 1, 'MAINTENANCE'),
        (3, 76, 17, 1, 'MAINTENANCE'),
        (3, 84, 17, 1, 'MAINTENANCE'),
        (5, 81, 17, 1, 'MAINTENANCE'),

        (3, 82, 17, 2, 'MAINTENANCE'),
        (3, 81, 17, 2, 'MAINTENANCE'),
        (1, 76, 17, 2, 'MAINTENANCE'),
        (3, 84, 17, 2, 'MAINTENANCE'),
        (3, 81, 17, 2, 'MAINTENANCE'),
        (5, 76, 17, 2, 'MAINTENANCE'),

        (3, 77, 17, 3, 'MAINTENANCE'),
        (5, 76, 17, 3, 'MAINTENANCE'),

        (3, 85, 18, 1, 'MAINTENANCE'),
        (5, 76, 18, 1, 'MAINTENANCE'),

        (3, 86, 19, 1, 'MAINTENANCE'),
        (5, 76, 19, 1, 'MAINTENANCE');

UPDATE question
SET dependance = null
    WHERE idquestion = 1;
UPDATE question
SET dependance = 1
    WHERE idquestion = 2;

UPDATE question
SET dependance = null
    WHERE idquestion = 3;
UPDATE question
SET dependance = 4
    WHERE idquestion = 4;
UPDATE question
SET dependance = 4
    WHERE idquestion = 5;
UPDATE question
SET dependance = 4
    WHERE idquestion = 6;
UPDATE question
SET dependance = 9
    WHERE idquestion = 7;
UPDATE question
SET dependance = null
    WHERE idquestion = 8;
UPDATE question
SET dependance = null
    WHERE idquestion = 9;
UPDATE question
SET dependance = null
    WHERE idquestion = 10;
UPDATE question
SET dependance = null
    WHERE idquestion = 11;
UPDATE question
SET dependance = null
    WHERE idquestion = 12;
UPDATE question
SET dependance = null
    WHERE idquestion = 13;

UPDATE question
SET dependance = null
    WHERE idquestion = 14;
UPDATE question
SET dependance = 17
    WHERE idquestion = 15;
UPDATE question
SET dependance = 17
    WHERE idquestion = 16;
UPDATE question
SET dependance = 17
    WHERE idquestion = 17;
UPDATE question
SET dependance = 17
    WHERE idquestion = 18;
UPDATE question
SET dependance = 23
    WHERE idquestion = 19;
UPDATE question
SET dependance = null
    WHERE idquestion = 20;
UPDATE question
SET dependance = null
    WHERE idquestion = 21;
UPDATE question
SET dependance = null
    WHERE idquestion = 22;
UPDATE question
SET dependance = null
    WHERE idquestion = 23;
UPDATE question
SET dependance = null
    WHERE idquestion = 24;
UPDATE question
SET dependance = null
    WHERE idquestion = 25;
UPDATE question
SET dependance = null
    WHERE idquestion = 26;
UPDATE question
SET dependance = null
    WHERE idquestion = 27;
UPDATE question
SET dependance = null
    WHERE idquestion = 28;

UPDATE question
SET dependance = null
    WHERE idquestion = 29;
UPDATE question
SET dependance = 42
    WHERE idquestion = 30;
UPDATE question
SET dependance = 42
    WHERE idquestion = 31;
UPDATE question
SET dependance = 42
    WHERE idquestion = 32;
UPDATE question
SET dependance = 42
    WHERE idquestion = 33;
UPDATE question
SET dependance = 47
    WHERE idquestion = 34;
UPDATE question
SET dependance = null
    WHERE idquestion = 35;
UPDATE question
SET dependance = null
    WHERE idquestion = 36;
UPDATE question
SET dependance = null
    WHERE idquestion = 37;
UPDATE question
SET dependance = null
    WHERE idquestion = 38;
UPDATE question
SET dependance = null
    WHERE idquestion = 39;

UPDATE question
SET dependance = null
    WHERE idquestion = 40;
UPDATE question
SET dependance = 55
    WHERE idquestion = 41;
UPDATE question
SET dependance = 55
    WHERE idquestion = 42;
UPDATE question
SET dependance = 55
    WHERE idquestion = 43;
UPDATE question
SET dependance = 55
    WHERE idquestion = 44;
UPDATE question
SET dependance = 60
    WHERE idquestion = 45;
UPDATE question
SET dependance = null
    WHERE idquestion = 46;
UPDATE question
SET dependance = null
    WHERE idquestion = 47;
UPDATE question
SET dependance = null
    WHERE idquestion = 48;
UPDATE question
SET dependance = null
    WHERE idquestion = 49;
UPDATE question
SET dependance = null
    WHERE idquestion = 50;
UPDATE question
SET dependance = null
    WHERE idquestion = 51;
UPDATE question
SET dependance = 68
    WHERE idquestion = 52;
UPDATE question
SET dependance = 68
    WHERE idquestion = 53;
UPDATE question
SET dependance = 71
    WHERE idquestion = 54;

UPDATE question
SET dependance = null
    WHERE idquestion = 55;
UPDATE question
SET dependance = 76
    WHERE idquestion = 56;
UPDATE question
SET dependance = 76
    WHERE idquestion = 57;
UPDATE question
SET dependance = 76
    WHERE idquestion = 58;
UPDATE question
SET dependance = 76
    WHERE idquestion = 59;
UPDATE question
SET dependance = 81
    WHERE idquestion = 60;
UPDATE question
SET dependance = null
    WHERE idquestion = 61;
UPDATE question
SET dependance = null
    WHERE idquestion = 62;
UPDATE question
SET dependance = null
    WHERE idquestion = 63;
UPDATE question
SET dependance = null
    WHERE idquestion = 64;
UPDATE question
SET dependance = null
    WHERE idquestion = 65;