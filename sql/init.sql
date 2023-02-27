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
    isadmin  INT          NOT NULL,
    UNIQUE(mail)
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
    constante   FLOAT,
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
    questionid   INT NOT NULL,
    reponseposid INT NOT NULL,
    entry        INT NOT NULL,
    CONSTRAINT projetid PRIMARY KEY (projetid, questionid),
    CONSTRAINT fk_projetid FOREIGN KEY (projetid) REFERENCES projet (idprojet),
    CONSTRAINT questionid FOREIGN KEY (questionid) REFERENCES question (idquestion),
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
        'DEMO', 'Salarie', 0),
       ('anonyme@demo.fr', 'anonyme',
        'DEMO', 'Ano', 0);
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
    ('Veux-tu remplir cette phase ?', 2,'QCM', 'PLANIFICATION'),
    ('Combien de jours dure cette phase ?', 3, 'NUMERIC', 'PLANIFICATION'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 4, 'NUMERIC', 'PLANIFICATION'),
    ('Combien de jours de télé-travail vos collaborateur ont pour cette phase ?',5, 'NUMERIC', 'PLANIFICATION'),
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
    ('Combien de jours de télé-travail vos collaborateur ont pour cette phase ?',16, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 17, 'QCM', 'DEVELOPPEMENT'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 18, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien viennent en voiture ?', 19, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien viennent en vélo ou à pied ?', 20, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien viennent en transport en commun ?', 21, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 22, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien de PC portables utilises-tu pour cette phase ?', 23, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Quel langage utilises-tu majoritairement pour le back ?', 24, 'QCM', 'DEVELOPPEMENT'),
    ('Combien de lignes de code ?', 25, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Quel langage utilises-tu majoritairement pour le front ?', 26, 'QCM', 'DEVELOPPEMENT'),
    ('Combien de lignes de code ?', 27, 'NUMERIC', 'DEVELOPPEMENT'),

    ('Utilises-tu un DataCenter ?', 28, 'QCM', 'DEVELOPPEMENT'),
    ('Combien d’énergie votre Datacenter consomme-t-il (en kWh) ?', 29, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Sais-tu comment est produite l énergie qui alimente majoritairement ton DataCenter ?', 30, 'QCM', 'DEVELOPPEMENT'),
    ('Quelle énergie alimente majoritairement ton DataCenter ?', 31, 'QCM', 'DEVELOPPEMENT'),

    /*TEST*/
    ('Veux-tu remplir cette phase?', 32,'QCM', 'TEST'),
    ('Combien de jours dure cette phase ?', 33, 'NUMERIC', 'TEST'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 34, 'NUMERIC', 'TEST'),
    ('Combien de jours de télé-travail vos collaborateur ont pour cette phase ?',35, 'NUMERIC', 'TEST'),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 36, 'QCM', 'TEST'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 37, 'NUMERIC', 'TEST'),
    ('Combien viennent en voiture ?', 38, 'NUMERIC', 'TEST'),
    ('Combien viennent en vélo ou à pied ?', 39, 'NUMERIC', 'TEST'),
    ('Combien viennent en transport en commun ?', 40, 'NUMERIC', 'TEST'),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 41, 'NUMERIC', 'TEST'),
    ('Combien de PC portables utilises-tu pour cette phase ?', 42, 'NUMERIC', 'TEST'),

    ('Utilises-tu un DataCenter ?', 43, 'QCM', 'TEST'),
    ('Combien d’énergie votre Datacenter consomme-t-il (en kWh) ?', 44, 'NUMERIC', 'TEST'),
    ('Sais-tu comment est produite l énergie qui alimente majoritairement ton DataCenter ?', 45, 'QCM', 'TEST'),
    ('Quelle énergie alimente majoritairement ton DataCenter ?', 46, 'QCM', 'TEST'),

    /*DEPLOIEMENT*/
    ('Veux-tu remplir cette phase?', 47,'QCM', 'DEPLOIEMENT'),
    ('Combien de jours dure cette phase ?', 48, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 49, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de jours de télé-travail vos collaborateur ont pour cette phase ?',50, 'NUMERIC', 'DEPLOIEMENT'),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 51, 'QCM', 'DEPLOIEMENT'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 52, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien viennent en voiture ?', 53, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien viennent en vélo ou à pied ?', 54, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien viennent en transport en commun ?', 55, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 56, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de PC portables utilises-tu pour cette phase ?', 57, 'NUMERIC', 'DEPLOIEMENT'),
    ('Utilises-tu un DataCenter ?', 58, 'QCM', 'DEPLOIEMENT'),
    ('Combien d’énergie votre Datacenter consomme-t-il (en kWh) ?', 59, 'NUMERIC', 'DEPLOIEMENT'),
    ('Sais-tu comment est produite l énergie qui alimente majoritairement ton DataCenter ?', 60, 'QCM', 'DEPLOIEMENT'),
    ('Quelle énergie alimente majoritairement ton DataCenter ?', 61, 'QCM', 'DEPLOIEMENT'),

    /*MAINTENANCE*/
    ('Veux-tu remplir cette phase?', 62,'QCM', 'MAINTENANCE'),
    ('Combien de jours dure cette phase ?', 63, 'NUMERIC', 'MAINTENANCE'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 64, 'NUMERIC', 'MAINTENANCE'),
    ('Combien de jours de télé-travail vos collaborateur ont pour cette phase ?',65, 'NUMERIC', 'MAINTENANCE'),
    ('Connais-tu à peu près la distance de trajet de tes collaborateurs ?', 66, 'QCM', 'MAINTENANCE'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 67, 'NUMERIC', 'MAINTENANCE'),
    ('Combien viennent en voiture ?', 68, 'NUMERIC', 'MAINTENANCE'),
    ('Combien viennent en vélo ou à pied ?', 69, 'NUMERIC', 'MAINTENANCE'),
    ('Combien viennent en transport en commun ?', 70, 'NUMERIC', 'MAINTENANCE'),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 71, 'NUMERIC', 'MAINTENANCE'),
    ('Combien de PC portables utilises-tu pour cette phase ?', 72, 'NUMERIC', 'MAINTENANCE');

--- CREATION DES CONSTANTES
INSERT INTO constante (constante, tracabilite)
VALUES          (0, 'Constante neutre'),
                (1, 'Constante neutre'),
                (0.178, 'https://datagir.ademe.fr/apps/mon-impact-transport/ '),
 /*C*/          (0.005, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur 0.005'),
 /*C++*/        (0.0067 , 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur 0.005'),
 /*Java*/       (0.0099, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur 0.005'),
 /*Python*/     (0.3794, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur 0.005'),
 /*Moyenne*/    (0.0806, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur 0.005'),
 /*PHP*/        (0.1465, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur 0.005'),
 /*JavaScript*/ (0.02225, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur 0.005'),
 /*TypeScript*/ (0.1075, 'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur 0.005'),
                (0.2, 'Voiture par kilometre, https://datagir.ademe.fr/apps/mon-impact-transport/'),
                (0.0168, 'Transport en commun, https://datagir.ademe.fr/apps/mon-impact-transport/'),
                (7.66, 'Moyenne des transports d un salarié en une journée,https://culture-rh.com/empreinte-carbone-salarie/'),
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
    (7, 12, 'NON', 2),
    (8, 9, 'Veuillez entrer un entier', 2),
    (9, 10, 'Veuillez entrer un entier', 12),
    (10, 11, 'Veuillez entrer un entier', 2),
    (11, 12, 'Veuillez entrer un entier', 13),
    (12, 13, 'Veuillez entrer un entier', 15),
    (13, 14, 'Veuillez entrer un entier', 16),

    /*DEVELOPPEMENT*/
    (14, 15, 'OUI', 2),
    (14, 33, 'NON', 2),
    (15, 16, 'Veuillez entrer un entier', 2),
    (16, 17, 'Veuillez entrer un entier', 14),
    (17, 18, 'Veuillez entrer un entier', 2),
    (18, 19, 'OUI', 2),
    (18, 23, 'NON', 2),
    (19, 20, 'Veuillez entrer un entier', 2),
    (20, 21, 'Veuillez entrer un entier', 12),
    (21, 22, 'Veuillez entrer un entier', 2),
    (22, 23, 'Veuillez entrer un entier', 13),
    (23, 24, 'Veuillez entrer un entier', 15),
    (24, 25, 'Veuillez entrer un entier', 16),
    (25, 26, 'C', 4),
    (25, 26, 'C++', 5),
    (25, 26, 'Java', 6),
    (25, 26, 'Python', 7),
    (25, 26, 'Autre', 8),
    (26, 27, 'Veuillez entrer un entier', 2),
    (27, 28, 'PHP', 9),
    (27, 28, 'JavaScript', 10),
    (27, 28, 'TypeScript', 11),
    (27, 28, 'Autre', 8),
    (28, 29, 'Veuillez entrer un entier', 2),
    (29, 30, 'OUI', 2),
    (29, 33, 'NON', 2),
    (30, 31, 'Veuillez entrer un entier', 2),
    (31, 32, 'OUI', 2),
    (31, 33, 'NON', 2),
    (32, 33, 'NUCLEAIRE', 17),
    (32, 33, 'FOSSILE', 19),
    (32, 33, 'ENERGIE RENOUVELABLE', 18),

    /*TEST*/
    (33, 34, 'OUI', 2),
    (33, 48, 'NON', 2),
    (34, 35, 'Veuillez entrer un entier', 2),
    (35, 36, 'Veuillez entrer un entier', 14),
    (36, 37, 'Veuillez entrer un entier', 2),
    (37, 38, 'OUI', 2),
    (37, 41, 'NON', 2),
    (38, 39, 'Veuillez entrer un entier', 2),
    (39, 40, 'Veuillez entrer un entier', 12),
    (40, 41, 'Veuillez entrer un entier', 2),
    (41, 42, 'Veuillez entrer un entier', 13),
    (42, 43, 'Veuillez entrer un entier', 15),
    (43, 44, 'Veuillez entrer un entier', 16),
    (44, 45, 'OUI', 2),
    (44, 48, 'NON', 2),
    (45, 46, 'Veuillez entrer un entier', 2),
    (46, 47, 'OUI', 2),
    (46, 48, 'NON', 2),
    (47, 48, 'NUCLEAIRE', 17),
    (47, 48, 'FOSSILE', 19),
    (47, 48, 'ENERGIE RENOUVELABLE', 18),

    /*DEPLOIEMENT*/
    (48, 49, 'OUI', 2),
    (48, 63, 'NON', 2),
    (49, 50, 'Veuillez entrer un entier', 2),
    (50, 51, 'Veuillez entrer un entier', 14),
    (51, 52, 'Veuillez entrer un entier', 2),
    (52, 53, 'OUI', 2),
    (52, 57, 'NON', 2),
    (53, 54, 'Veuillez entrer un entier', 2),
    (54, 55, 'Veuillez entrer un entier', 12),
    (55, 56, 'Veuillez entrer un entier', 2),
    (56, 57, 'Veuillez entrer un entier', 13),
    (57, 58, 'Veuillez entrer un entier', 15),
    (58, 59, 'Veuillez entrer un entier', 16),
    (59, 60, 'OUI', 2),
    (59, 63, 'NON', 2),
    (60, 61, 'Veuillez entrer un entier', 2),
    (61, 62, 'OUI', 2),
    (61, 63, 'NON', 2),
    (62, 63, 'NUCLEAIRE', 17),
    (62, 63, 'FOSSILE', 19),
    (62, 63, 'ENERGIE RENOUVELABLE', 18),

    /*MAINTENANCE*/
    (63, 64, 'OUI', 2),
    (63, null, 'NON', 2),
    (64, 65, 'Veuillez entrer un entier', 2),
    (65, 66, 'Veuillez entrer un entier', 14),
    (66, 67, 'Veuillez entrer un entier', 2),
    (67, 68, 'OUI', 2),
    (67, 72, 'NON', 2),
    (68, 69, 'Veuillez entrer un entier', 2),
    (69, 70, 'Veuillez entrer un entier', 12),
    (70, 71, 'Veuillez entrer un entier', 2),
    (71, 72, 'Veuillez entrer un entier', 13),
    (72, 73, 'Veuillez entrer un entier', 15),
    (73, null, 'Veuillez entrer un entier', 16);


--CREATION CALCUL
INSERT INTO calcul(calculopid, reponsepossibleid, nbcalcul,priorite,
                   phase)
VALUES  (5, 3, 1, 1, 'HORS_PHASE'),

        (5, 6, 2, 1, 'PLANIFICATION'),

        (3, 12, 3, 1, 'PLANIFICATION'),--a*
        (3, 11, 3, 1, 'PLANIFICATION'),--y*
        (1, 6, 3, 1, 'PLANIFICATION'),--z+
        (3, 14, 3, 1, 'PLANIFICATION'),--c*
        (3, 11, 3, 1, 'PLANIFICATION'),--y*
        (2, 6, 3, 1, 'PLANIFICATION'),--z-
        (3, 8, 3, 1, 'PLANIFICATION'),--x*
        (3, 12, 3, 1, 'PLANIFICATION'),--a*
        (2, 11, 3, 1, 'PLANIFICATION'),--y-
        (3, 8, 3, 1, 'PLANIFICATION'),--x*
        (3, 14, 3, 1, 'PLANIFICATION'),--c*
        (5, 11, 3, 1, 'PLANIFICATION'),--yf

        (3, 12, 3, 2, 'PLANIFICATION'),--a*
        (3, 11, 3, 2, 'PLANIFICATION'),--y*
        (1, 6, 3, 2, 'PLANIFICATION'),--z+
        (3, 14, 3, 2, 'PLANIFICATION'),--c*
        (3, 11, 3, 2, 'PLANIFICATION'),--y*
        (5, 6, 3, 2, 'PLANIFICATION'),--zf

        (3, 7, 3, 3, 'PLANIFICATION'),
        (5, 6, 3, 3, 'PLANIFICATION'),

        (3, 15, 4, 1, 'PLANIFICATION'),
        (5, 6, 4, 1, 'PLANIFICATION'),

        (3, 16, 5, 1, 'PLANIFICATION'),
        (5, 6, 5, 1, 'PLANIFICATION'),

        (5, 19, 6, 1, 'DEVELOPPEMENT'),

        (3, 26, 7, 1, 'DEVELOPPEMENT'),--a*
        (3, 25, 7, 1, 'DEVELOPPEMENT'),--y*
        (1, 19, 7, 1, 'DEVELOPPEMENT'),--z+
        (3, 28, 7, 1, 'DEVELOPPEMENT'),--c*
        (3, 25, 7, 1, 'DEVELOPPEMENT'),--y*
        (2, 19, 7, 1, 'DEVELOPPEMENT'),--z-
        (3, 21, 7, 1, 'DEVELOPPEMENT'),--x*
        (3, 26, 7, 1, 'DEVELOPPEMENT'),--a*
        (2, 25, 7, 1, 'DEVELOPPEMENT'),--y-
        (3, 21, 7, 1, 'DEVELOPPEMENT'),--x*
        (3, 28, 7, 1, 'DEVELOPPEMENT'),--c*
        (5, 25, 7, 1, 'DEVELOPPEMENT'),--yf

        (3, 26, 7, 2, 'DEVELOPPEMENT'),--a*
        (3, 25, 7, 2, 'DEVELOPPEMENT'),--y*
        (1, 19, 7, 2, 'DEVELOPPEMENT'),--z+
        (3, 28, 7, 2, 'DEVELOPPEMENT'),--c*
        (3, 25, 7, 2, 'DEVELOPPEMENT'),--y*
        (5, 19, 7, 2, 'DEVELOPPEMENT'),--zf

        (3, 20, 7, 3, 'DEVELOPPEMENT'),
        (5, 19, 7, 3, 'DEVELOPPEMENT'),

        (3, 28, 8, 1, 'DEVELOPPEMENT'),
        (5, 19, 8, 1, 'DEVELOPPEMENT'),

        (3, 29, 9, 1, 'DEVELOPPEMENT'),
        (5, 19, 9, 1, 'DEVELOPPEMENT'),

        (3, 30, 10, 1, 'DEVELOPPEMENT'),
        (5, 35, 10, 1, 'DEVELOPPEMENT'),

        (3, 31, 11, 1, 'DEVELOPPEMENT'),
        (5, 35, 11, 1, 'DEVELOPPEMENT'),

        (3, 32, 12, 1, 'DEVELOPPEMENT'),
        (5, 35, 12, 1, 'DEVELOPPEMENT'),

        (3, 33, 13, 1, 'DEVELOPPEMENT'),
        (5, 35, 13, 1, 'DEVELOPPEMENT'),

        (3, 34, 14, 1, 'DEVELOPPEMENT'),
        (5, 35, 14, 1, 'DEVELOPPEMENT'),

        (3, 36, 15, 1, 'DEVELOPPEMENT'),
        (5, 40, 15, 1, 'DEVELOPPEMENT'),

        (3, 37, 16, 1, 'DEVELOPPEMENT'),
        (5, 40, 16, 1, 'DEVELOPPEMENT'),

        (3, 38, 17, 1, 'DEVELOPPEMENT'),
        (5, 40, 17, 1, 'DEVELOPPEMENT'),

        (3, 39, 18, 1, 'DEVELOPPEMENT'),
        (5, 40, 18, 1, 'DEVELOPPEMENT'),

        (3, 43, 19, 1, 'DEVELOPPEMENT'),
        (5, 46, 19, 1, 'DEVELOPPEMENT'),

        (3, 43, 20, 1, 'DEVELOPPEMENT'),
        (5, 47, 20, 1, 'DEVELOPPEMENT'),

        (3, 43, 21, 1, 'DEVELOPPEMENT'),
        (5, 48, 21, 1, 'DEVELOPPEMENT'),

        (5, 51, 22, 1, 'TEST'),

        (3, 57, 23, 1, 'TEST'),
        (3, 56, 23, 1, 'TEST'),
        (1, 51, 23, 1, 'TEST'),
        (3, 59, 23, 1, 'TEST'),
        (3, 56, 23, 1, 'TEST'),
        (2, 51, 23, 1, 'TEST'),
        (3, 53, 23, 1, 'TEST'),
        (3, 57, 23, 1, 'TEST'),
        (2, 56, 23, 1, 'TEST'),
        (3, 53, 23, 1, 'TEST'),
        (3, 59, 23, 1, 'TEST'),
        (5, 56, 23, 1, 'TEST'),

        (3, 57, 23, 2, 'TEST'),
        (3, 56, 23, 2, 'TEST'),
        (1, 51, 23, 2, 'TEST'),
        (3, 59, 23, 2, 'TEST'),
        (3, 56, 23, 2, 'TEST'),
        (5, 51, 23, 2, 'TEST'),

        (3, 52, 23, 3, 'TEST'),
        (5, 51, 23, 3, 'TEST'),

        (3, 60, 24, 1, 'TEST'),
        (5, 51, 24, 1, 'TEST'),

        (3, 61, 25, 1, 'TEST'),
        (5, 51, 25, 1, 'TEST'),

        (3, 64, 26, 1, 'TEST'),
        (5, 67, 26, 1, 'TEST'),

        (3, 64, 27, 1, 'TEST'),
        (5, 68, 27, 1, 'TEST'),

        (3, 64, 28, 1, 'TEST'),
        (5, 69, 28, 1, 'TEST'),

        (5, 72, 29, 1, 'DEPLOIEMENT'),

        (3, 78, 30, 1, 'DEPLOIEMENT'),
        (3, 77, 30, 1, 'DEPLOIEMENT'),
        (1, 72, 30, 1, 'DEPLOIEMENT'),
        (3, 80, 30, 1, 'DEPLOIEMENT'),
        (3, 77, 30, 1, 'DEPLOIEMENT'),
        (2, 72, 30, 1, 'DEPLOIEMENT'),
        (3, 74, 30, 1, 'DEPLOIEMENT'),
        (3, 78, 30, 1, 'DEPLOIEMENT'),
        (2, 77, 30, 1, 'DEPLOIEMENT'),
        (3, 74, 30, 1, 'DEPLOIEMENT'),
        (3, 80, 30, 1, 'DEPLOIEMENT'),
        (5, 77, 30, 1, 'DEPLOIEMENT'),

        (3, 78, 30, 2, 'DEPLOIEMENT'),
        (3, 77, 30, 2, 'DEPLOIEMENT'),
        (1, 72, 30, 2, 'DEPLOIEMENT'),
        (3, 80, 30, 2, 'DEPLOIEMENT'),
        (3, 77, 30, 2, 'DEPLOIEMENT'),
        (5, 72, 30, 2, 'DEPLOIEMENT'),

        (3, 73, 30, 3, 'DEPLOIEMENT'),
        (5, 72, 30, 3, 'DEPLOIEMENT'),

        (3, 81, 31, 1, 'DEPLOIEMENT'),
        (5, 72, 31, 1, 'DEPLOIEMENT'),

        (3, 82, 32, 1, 'DEPLOIEMENT'),
        (5, 72, 32, 1, 'DEPLOIEMENT'),

        (3, 85, 33, 1, 'DEPLOIEMENT'),
        (5, 88, 33, 1, 'DEPLOIEMENT'),

        (3, 85, 34, 1, 'DEPLOIEMENT'),
        (5, 89, 34, 1, 'DEPLOIEMENT'),

        (3, 85, 35, 1, 'DEPLOIEMENT'),
        (5, 90, 35, 1, 'DEPLOIEMENT'),

        (5, 93, 36, 1, 'MAINTENANCE'),

        (3, 99, 37, 1, 'MAINTENANCE'),
        (3, 98, 37, 1, 'MAINTENANCE'),
        (1, 93, 37, 1, 'MAINTENANCE'),
        (3, 101, 37, 1, 'MAINTENANCE'),
        (3, 98, 37, 1, 'MAINTENANCE'),
        (2, 93, 37, 1, 'MAINTENANCE'),
        (3, 95, 37, 1, 'MAINTENANCE'),
        (3, 99, 37, 1, 'MAINTENANCE'),
        (2, 98, 37, 1, 'MAINTENANCE'),
        (3, 93, 37, 1, 'MAINTENANCE'),
        (3, 101, 37, 1, 'MAINTENANCE'),
        (5, 98, 37, 1, 'MAINTENANCE'),

        (3, 99, 37, 2, 'MAINTENANCE'),
        (3, 98, 37, 2, 'MAINTENANCE'),
        (1, 93, 37, 2, 'MAINTENANCE'),
        (3, 101, 37, 2, 'MAINTENANCE'),
        (3, 98, 37, 2, 'MAINTENANCE'),
        (5, 93, 37, 2, 'MAINTENANCE'),

        (3, 94, 37, 3, 'MAINTENANCE'),
        (5, 93, 37, 3, 'MAINTENANCE'),

        (3, 102, 38, 1, 'MAINTENANCE'),
        (5, 93, 38, 1, 'MAINTENANCE'),

        (3, 103, 39, 1, 'MAINTENANCE'),
        (5, 93, 39, 1, 'MAINTENANCE');

/*HORS_PHASE*/
UPDATE question
SET dependance = null
    WHERE idquestion = 1;
UPDATE question
SET dependance = 1
    WHERE idquestion = 2;

/*PLANIFICATION*/
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
SET dependance = 4
    WHERE idquestion = 7;
UPDATE question
SET dependance = 9
    WHERE idquestion = 8;
UPDATE question
SET dependance = 9
    WHERE idquestion = 9;
UPDATE question
SET dependance = 9
    WHERE idquestion = 10;
UPDATE question
SET dependance = 9
    WHERE idquestion = 11;
UPDATE question
SET dependance = 4
    WHERE idquestion = 12;
UPDATE question
SET dependance = 4
    WHERE idquestion = 13;

/*DEVELOPPEMENT*/
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
SET dependance = 22
    WHERE idquestion = 19;
UPDATE question
SET dependance = 22
    WHERE idquestion = 20;
UPDATE question
SET dependance = 22
    WHERE idquestion = 21;
UPDATE question
SET dependance = 22
    WHERE idquestion = 22;
UPDATE question
SET dependance = 17
    WHERE idquestion = 23;
UPDATE question
SET dependance = 17
    WHERE idquestion = 24;
UPDATE question
SET dependance = 17
    WHERE idquestion = 25;
UPDATE question
SET dependance = 17
    WHERE idquestion = 26;
UPDATE question
SET dependance = 17
    WHERE idquestion = 27;
UPDATE question
SET dependance = 17
    WHERE idquestion = 28;
UPDATE question
SET dependance = 17
    WHERE idquestion = 29;
UPDATE question
SET dependance = 41
    WHERE idquestion = 30;
UPDATE question
SET dependance = 41
    WHERE idquestion = 31;
UPDATE question
SET dependance = 44
    WHERE idquestion = 32;

/*TEST*/
UPDATE question
SET dependance = null
    WHERE idquestion = 33;
UPDATE question
SET dependance = 49
    WHERE idquestion = 34;
UPDATE question
SET dependance = 49
    WHERE idquestion = 35;
UPDATE question
SET dependance = 49
    WHERE idquestion = 36;
UPDATE question
SET dependance = 49
    WHERE idquestion = 37;
UPDATE question
SET dependance = 54
    WHERE idquestion = 38;
UPDATE question
SET dependance = 54
    WHERE idquestion = 39;
UPDATE question
SET dependance = 54
    WHERE idquestion = 40;
UPDATE question
SET dependance = 54
    WHERE idquestion = 41;
UPDATE question
SET dependance = 49
    WHERE idquestion = 42;
UPDATE question
SET dependance = 49
    WHERE idquestion = 43;
UPDATE question
SET dependance = 49
    WHERE idquestion = 44;
UPDATE question
SET dependance = 62
    WHERE idquestion = 45;
UPDATE question
SET dependance = 62
    WHERE idquestion = 46;
UPDATE question
SET dependance = 65
    WHERE idquestion = 47;

/*DEPLOIEMENT*/
UPDATE question
SET dependance = null
    WHERE idquestion = 48;
UPDATE question
SET dependance = 70
    WHERE idquestion = 49;
UPDATE question
SET dependance = 70
    WHERE idquestion = 50;
UPDATE question
SET dependance = 70
    WHERE idquestion = 51;
UPDATE question
SET dependance = 70
    WHERE idquestion = 52;
UPDATE question
SET dependance = 75
    WHERE idquestion = 53;
UPDATE question
SET dependance = 75
    WHERE idquestion = 54;
UPDATE question
SET dependance = 75
    WHERE idquestion = 55;
UPDATE question
SET dependance = 75
    WHERE idquestion = 56;
UPDATE question
SET dependance = 70
    WHERE idquestion = 57;
UPDATE question
SET dependance = 70
    WHERE idquestion = 58;
UPDATE question
SET dependance = 70
    WHERE idquestion = 59;
UPDATE question
SET dependance = 83
    WHERE idquestion = 60;
UPDATE question
SET dependance = 83
    WHERE idquestion = 61;
UPDATE question
SET dependance = 86
    WHERE idquestion = 62;

/*MAITENANCE*/
UPDATE question
SET dependance = null
    WHERE idquestion = 63;
UPDATE question
SET dependance = 91
    WHERE idquestion = 64;
UPDATE question
SET dependance = 91
    WHERE idquestion = 65;
UPDATE question
SET dependance = 91
WHERE idquestion = 66;
UPDATE question
SET dependance = 91
WHERE idquestion = 67;
UPDATE question
SET dependance = 96
WHERE idquestion = 68;
UPDATE question
SET dependance = 96
WHERE idquestion = 69;
UPDATE question
SET dependance = 96
WHERE idquestion = 70;
UPDATE question
SET dependance = 96
WHERE idquestion = 71;
UPDATE question
SET dependance = 91
WHERE idquestion = 72;
UPDATE question
SET dependance = 91
WHERE idquestion = 73;