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
    UNIQUE (mail)
);
CREATE SEQUENCE project_sequence START WITH 1;
CREATE TABLE IF NOT EXISTS projet
(
    idprojet  INTEGER PRIMARY KEY DEFAULT nextval('project_sequence'),
    profilid  INT         NOT NULL,
    nomprojet VARCHAR(50) NOT NULL,
    etat      VARCHAR(50) NOT NULL,
    type      VARCHAR(50) NOT NULL,
    CONSTRAINT profilid FOREIGN KEY (profilid) REFERENCES profil (idprofil),
    UNIQUE(profilid, nomprojet)
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
    ADD CONSTRAINT dependance FOREIGN KEY (dependance) REFERENCES reponsepossible (idreponsepos);

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
INSERT INTO projet(profilid, nomprojet, etat, type)
VALUES (1, 'QUESTIONAIRE POUR Administrateur',
        'INPROGRESS', 'PROJET'),
       (2, 'QUESTIONAIRE POUR Salarie 1',
        'INPROGRESS', 'PROJET'),
       (2, 'QUESTIONAIRE POUR Salarie 2',
        'INPROGRESS', 'SIMULATION');
-- CREATION DES QUESTIONS
INSERT INTO question (intitule, questionpre, typeq, phase)
VALUES

    /*HORS_PHASE*/
    ('Votre projet nécessite-t-il des trajets en avion ?', null, 'QCM', 'HORS_PHASE'),
    ('Combien de km en avion sont effectués pour le projet ?', 1, 'NUMERIC', 'HORS_PHASE'),

    /*PLANIFICATION*/
    ('Veux-tu apporté des données concernant la phase de planification ?', 2, 'QCM', 'PLANIFICATION'),
    ('Combien de jours dure cette phase ?', 3, 'NUMERIC', 'PLANIFICATION'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 4, 'NUMERIC', 'PLANIFICATION'),
    ('Combien de jours de télé-travail a en moyenne un collaborateur pour la durée de cette phase ?', 5, 'NUMERIC',
     'PLANIFICATION'),
    ('Veux-tu donné la distance de trajet de tes collaborateurs ?', 6, 'QCM', 'PLANIFICATION'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 7, 'NUMERIC',
     'PLANIFICATION'),
    ('Combien viennent en voiture ?', 8, 'NUMERIC', 'PLANIFICATION'),
    ('Combien viennent en vélo ou à pied ?', 9, 'NUMERIC', 'PLANIFICATION'),
    ('Combien viennent en transport en commun ?', 10, 'NUMERIC', 'PLANIFICATION'),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 11, 'NUMERIC', 'PLANIFICATION'),
    ('Combien de PC portables utilises-tu pour cette phase ?', 12, 'NUMERIC', 'PLANIFICATION'),

    /*DEVELOPPEMENT*/
    ('Veux-tu apporté des données concernant la phase de developpement  ?', 13, 'QCM', 'DEVELOPPEMENT'),
    ('Combien de jours dure cette phase ?', 14, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 15, 'NUMERIC', 'DEVELOPPEMENT'),
    ('Combien de jours de télé-travail a en moyenne un collaborateur pour la durée de cette phase ?', 16, 'NUMERIC',
     'DEVELOPPEMENT'),
    ('Veux-tu donné la distance de trajet de tes collaborateurs ?', 17, 'QCM', 'DEVELOPPEMENT'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 18, 'NUMERIC',
     'DEVELOPPEMENT'),
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
    ('Sais-tu comment est produite l énergie qui alimente majoritairement ton DataCenter ?', 30, 'QCM',
     'DEVELOPPEMENT'),
    ('Quelle énergie alimente majoritairement ton DataCenter ?', 31, 'QCM', 'DEVELOPPEMENT'),

    /*TEST*/
    ('Veux-tu apporté des données concernant la phase de test  ?', 32, 'QCM', 'TEST'),
    ('Combien de jours dure cette phase ?', 33, 'NUMERIC', 'TEST'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 34, 'NUMERIC', 'TEST'),
    ('Combien de jours de télé-travail a en moyenne un collaborateur pour la durée de cette phase ?', 35, 'NUMERIC',
     'TEST'),
    ('Veux-tu donné la distance de trajet de tes collaborateurs ?', 36, 'QCM', 'TEST'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 37, 'NUMERIC',
     'TEST'),
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
    ('Veux-tu apporté des données concernant la phase de deploiement  ?', 47, 'QCM', 'DEPLOIEMENT'),
    ('Combien de jours dure cette phase ?', 48, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 49, 'NUMERIC', 'DEPLOIEMENT'),
    ('Combien de jours de télé-travail a en moyenne un collaborateur pour la durée de cette phase ?', 50, 'NUMERIC',
     'DEPLOIEMENT'),
    ('Veux-tu donné la distance de trajet de tes collaborateurs ?', 51, 'QCM', 'DEPLOIEMENT'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 52, 'NUMERIC',
     'DEPLOIEMENT'),
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
    ('Veux-tu apporté des données concernant la phase de maintenance  ?', 62, 'QCM', 'MAINTENANCE'),
    ('Combien de jours dure cette phase ?', 63, 'NUMERIC', 'MAINTENANCE'),
    ('Combien de collaborateurs travaillent sur cette phase ?', 64, 'NUMERIC', 'MAINTENANCE'),
    ('Combien de jours de télé-travail a en moyenne un collaborateur pour la durée de cette phase ?', 65, 'NUMERIC',
     'MAINTENANCE'),
    ('Veux-tu donné la distance de trajet de tes collaborateurs ?', 66, 'QCM', 'MAINTENANCE'),
    ('Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?', 67, 'NUMERIC',
     'MAINTENANCE'),
    ('Combien viennent en voiture ?', 68, 'NUMERIC', 'MAINTENANCE'),
    ('Combien viennent en vélo ou à pied ?', 69, 'NUMERIC', 'MAINTENANCE'),
    ('Combien viennent en transport en commun ?', 70, 'NUMERIC', 'MAINTENANCE'),
    ('Combien de PC fixes utilises-tu pour cette phase ?', 71, 'NUMERIC', 'MAINTENANCE'),
    ('Combien de PC portables utilises-tu pour cette phase ?', 72, 'NUMERIC', 'MAINTENANCE');

--- CREATION DES CONSTANTES
INSERT INTO constante (constante, tracabilite)
VALUES (0, 'Constante neutre'),
       (1, 'Constante neutre'),
       (0.178, 'https://datagir.ademe.fr/apps/mon-impact-transport/ '),
    /*C*/
       (0.005,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*C++*/
       (0.0067,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Java*/
       (0.0099,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Python*/
       (0.3794,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Moyenne*/
       (0.0806,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*PHP*/
       (0.1465,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*JavaScript*/
       (0.02225,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*TypeScript*/
       (0.1075,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur 0.005'),
       (0.2, 'Voiture par kilometre, https://datagir.ademe.fr/apps/mon-impact-transport/'),
       (0.0168, 'Transport en commun, https://datagir.ademe.fr/apps/mon-impact-transport/'),
       (7.66, 'Moyenne des transports d un salarié en une journée,https://culture-rh.com/empreinte-carbone-salarie/'),
       (0.67, 'Empreinte carbonne d un ordinateur fixe pour 1 journée, https://impactco2.fr/usagenumerique '),
       (0.25, 'Empreinte carbonne d un ordinateur portable pour 1 journée, https://impactco2.fr/usagenumerique '),
       (0.006, 'Émissions de CO2 (kgCO2e/kWh) du nucleaire, https://climate.selectra.com/fr/empreinte-carbone/energie'),
       (0.025,
        'Émissions de CO2 (kgCO2e/kWh) de energie renouvlable, https://climate.selectra.com/fr/empreinte-carbone/energie'),
       (0.6515,
        'Émissions de CO2 (kgCO2e/kWh) de energie fossile, https://climate.selectra.com/fr/empreinte-carbone/energie'),
    /*Perl*/
       (79.58 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Ruby*/
       (69.91 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Jruby*/
       (46.54 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Lua*/
       (48.98 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Erlang*/
       (42.23 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Hack*/
       (24.02 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Racket*/
       (7.91 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*F#*/
       (4.13 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Haskell*/
       (3.10 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Swift*/
       (2.79 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Fortran*/
       (2.52 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*OCaml*/
       (2.40 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Lisp*/
       (2.27 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Chapel*/
       (2.18 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Pascal*/
       (2.14 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Ada*/
       (1.70 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base'),
    /*Rust*/
       (1.03 / 200,
        'conjecture fait pour 1 ligne de code afin de pouvoir avoir une idée de la difference entre 2 langages. C étant la valeur de base');

--- CREATION DES REPONSES POSSIBLE
INSERT INTO reponsepossible (questionasso, questionsuiv, intitule,
                             constanteid)
VALUES

    /*HORS_PHASE*/
    (1, 2, 'OUI', 2),
    (1, 3, 'NON', 2),
    (2, 3, 'Veuillez entrer le nombre de km', 3),

    /*PLANIFICATION*/
    (3, 4, 'OUI', 2),
    (3, 14, 'NON', 2),
    (4, 5, 'Veuillez entrer le nombre de jours', 2),
    (5, 6, 'Veuillez entrer le nombre de collaborateurs', 14),
    (6, 7, 'Veuillez entrer le nombre de jours', 2),
    (7, 8, 'OUI', 2),
    (7, 12, 'NON', 2),
    (8, 9, 'Veuillez entrer le nombre de km', 2),
    (9, 10, 'Veuillez entrer le nombre de collaborateurs', 12),
    (10, 11, 'Veuillez entrer le nombre de collaborateurs', 2),
    (11, 12, 'Veuillez entrer le nombre de collaborateurs', 13),
    (12, 13, 'Veuillez entrer le nombre de PC fixes', 15),
    (13, 14, 'Veuillez entrer le nombre de PC portables', 16),

    /*DEVELOPPEMENT*/
    (14, 15, 'OUI', 2),
    (14, 33, 'NON', 2),
    (15, 16, 'Veuillez entrer le nombre de jours', 2),
    (16, 17, 'Veuillez entrer le nombre de collaborateurs', 14),
    (17, 18, 'Veuillez entrer le nombre de jours', 2),
    (18, 19, 'OUI', 2),
    (18, 23, 'NON', 2),
    (19, 20, 'Veuillez entrer le nombre de km', 2),
    (20, 21, 'Veuillez entrer le nombre de collaborateurs', 12),
    (21, 22, 'Veuillez entrer le nombre de collaborateurs', 2),
    (22, 23, 'Veuillez entrer le nombre de collaborateurs', 13),
    (23, 24, 'Veuillez entrer le nombre de PC fixes', 15),
    (24, 25, 'Veuillez entrer le nombre de PC portables', 16),
    (25, 26, 'C', 4),
    (25, 26, 'C++', 5),
    (25, 26, 'Java', 6),
    (25, 26, 'Python', 7),
    (25, 26, 'Perl', 20),
    (25, 26, 'Ruby', 21),
    (25, 26, 'JRuby', 22),
    (25, 26, 'Erlang', 24),
    (25, 26, 'Racket', 26),
    (25, 26, 'F#', 27),
    (25, 26, 'Haskell', 28),
    (25, 26, 'Swift', 29),
    (25, 26, 'Fortran', 30),
    (25, 26, 'OCaml', 31),
    (25, 26, 'Lisp', 32),
    (25, 26, 'Chapel', 33),
    (25, 26, 'Pascal', 34),
    (25, 26, 'Rust', 36),
    (25, 26, 'Autre', 8),
    (26, 27, 'Veuillez entrer le nombre de lignes', 2),
    (27, 28, 'PHP', 9),
    (27, 28, 'JavaScript', 10),
    (27, 28, 'TypeScript', 11),
    (27, 28, 'Perl', 20),
    (27, 28, 'Hack', 25),
    (27, 28, 'Swift', 29),
    (27, 28, 'Autre', 8),
    (28, 29, 'Veuillez entrer le nombre de lignes', 2),
    (29, 30, 'OUI', 2),
    (29, 33, 'NON', 2),
    (29, 33, 'Je ne sais pas', 2),
    (30, 31, 'Veuillez entrer le nombre de KWh', 2),
    (31, 32, 'OUI', 2),
    (31, 33, 'NON', 2),
    (32, 33, 'NUCLEAIRE', 17),
    (32, 33, 'FOSSILE', 19),
    (32, 33, 'ENERGIE RENOUVELABLE', 18),

    /*TEST*/
    (33, 34, 'OUI', 2),
    (33, 48, 'NON', 2),
    (34, 35, 'Veuillez entrer le nombre de jours', 2),
    (35, 36, 'Veuillez entrer le nombre de collaborateurs', 14),
    (36, 37, 'Veuillez entrer le nombre de jours', 2),
    (37, 38, 'OUI', 2),
    (37, 41, 'NON', 2),
    (38, 39, 'Veuillez entrer le nombre de km', 2),
    (39, 40, 'Veuillez entrer le nombre de collaborateurs', 12),
    (40, 41, 'Veuillez entrer le nombre de collaborateurs', 2),
    (41, 42, 'Veuillez entrer le nombre de collaborateurs', 13),
    (42, 43, 'Veuillez entrer le nombre de PC fixes', 15),
    (43, 44, 'Veuillez entrer le nombre de PC portables', 16),
    (44, 45, 'OUI', 2),
    (44, 48, 'NON', 2),
    (44, 48, 'Je ne sais pas', 2),
    (45, 46, 'Veuillez entrer le nombre de KWh', 2),
    (46, 47, 'OUI', 2),
    (46, 48, 'NON', 2),
    (47, 48, 'NUCLEAIRE', 17),
    (47, 48, 'FOSSILE', 19),
    (47, 48, 'ENERGIE RENOUVELABLE', 18),

    /*DEPLOIEMENT*/
    (48, 49, 'OUI', 2),
    (48, 63, 'NON', 2),
    (49, 50, 'Veuillez entrer le nombre de jours', 2),
    (50, 51, 'Veuillez entrer le nombre de collaborateurs', 14),
    (51, 52, 'Veuillez entrer le nombre de jours', 2),
    (52, 53, 'OUI', 2),
    (52, 57, 'NON', 2),
    (53, 54, 'Veuillez entrer le nombre de km', 2),
    (54, 55, 'Veuillez entrer le nombre de collaborateurs', 12),
    (55, 56, 'Veuillez entrer le nombre de collaborateurs', 2),
    (56, 57, 'Veuillez entrer le nombre de collaborateurs', 13),
    (57, 58, 'Veuillez entrer le nombre de PC fixes', 15),
    (58, 59, 'Veuillez entrer le nombre de PC portables', 16),
    (59, 60, 'OUI', 2),
    (59, 63, 'NON', 2),
    (59, 63, 'Je ne sais pas', 2),
    (60, 61, 'Veuillez entrer le nombre de KWh', 2),
    (61, 62, 'OUI', 2),
    (61, 63, 'NON', 2),
    (62, 63, 'NUCLEAIRE', 17),
    (62, 63, 'FOSSILE', 19),
    (62, 63, 'ENERGIE RENOUVELABLE', 18),

    /*MAINTENANCE*/
    (63, 64, 'OUI', 2),
    (63, null, 'NON', 2),
    (64, 65, 'Veuillez entrer le nombre de jours', 2),
    (65, 66, 'Veuillez entrer le nombre collaborateurs', 14),
    (66, 67, 'Veuillez entrer le nombre de jours', 2),
    (67, 68, 'OUI', 2),
    (67, 72, 'NON', 2),
    (68, 69, 'Veuillez entrer le nombre de km', 2),
    (69, 70, 'Veuillez entrer le nombre de collaborateurs', 12),
    (70, 71, 'Veuillez entrer le nombre de collaborateurs', 2),
    (71, 72, 'Veuillez entrer le nombre de collaborateurs', 13),
    (72, 73, 'Veuillez entrer un entier', 15),
    (73, null, 'Veuillez entrer un entier', 16);


--CREATION CALCUL
INSERT INTO calcul(calculopid, reponsepossibleid, nbcalcul, priorite,
                   phase)
VALUES (5, 3, 1, 1, 'HORS_PHASE'),

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
       (5, 49, 10, 1, 'DEVELOPPEMENT'),

       (3, 31, 11, 1, 'DEVELOPPEMENT'),
       (5, 49, 11, 1, 'DEVELOPPEMENT'),

       (3, 32, 12, 1, 'DEVELOPPEMENT'),
       (5, 49, 12, 1, 'DEVELOPPEMENT'),

       (3, 33, 13, 1, 'DEVELOPPEMENT'),
       (5, 49, 13, 1, 'DEVELOPPEMENT'),

       (3, 34, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 35, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 36, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 37, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 38, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 39, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 40, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 41, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 42, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 43, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 44, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 45, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 46, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 47, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 48, 14, 1, 'DEVELOPPEMENT'),
       (5, 49, 14, 1, 'DEVELOPPEMENT'),

       (3, 50, 15, 1, 'DEVELOPPEMENT'),
       (5, 57, 15, 1, 'DEVELOPPEMENT'),

       (3, 51, 16, 1, 'DEVELOPPEMENT'),
       (5, 57, 16, 1, 'DEVELOPPEMENT'),

       (3, 52, 17, 1, 'DEVELOPPEMENT'),
       (5, 57, 17, 1, 'DEVELOPPEMENT'),

       (3, 53, 18, 1, 'DEVELOPPEMENT'),
       (5, 57, 18, 1, 'DEVELOPPEMENT'),

       (3, 54, 18, 1, 'DEVELOPPEMENT'),
       (5, 57, 18, 1, 'DEVELOPPEMENT'),

       (3, 55, 18, 1, 'DEVELOPPEMENT'),
       (5, 57, 18, 1, 'DEVELOPPEMENT'),

       (3, 56, 18, 1, 'DEVELOPPEMENT'),
       (5, 57, 18, 1, 'DEVELOPPEMENT'),

       (3, 61, 19, 1, 'DEVELOPPEMENT'),
       (5, 64, 19, 1, 'DEVELOPPEMENT'),

       (3, 61, 20, 1, 'DEVELOPPEMENT'),
       (5, 65, 20, 1, 'DEVELOPPEMENT'),

       (3, 61, 21, 1, 'DEVELOPPEMENT'),
       (5, 66, 21, 1, 'DEVELOPPEMENT'),

       (5, 69, 22, 1, 'TEST'),

       (3, 75, 23, 1, 'TEST'),
       (3, 74, 23, 1, 'TEST'),
       (1, 69, 23, 1, 'TEST'),
       (3, 77, 23, 1, 'TEST'),
       (3, 74, 23, 1, 'TEST'),
       (2, 69, 23, 1, 'TEST'),
       (3, 71, 23, 1, 'TEST'),
       (3, 75, 23, 1, 'TEST'),
       (2, 74, 23, 1, 'TEST'),
       (3, 71, 23, 1, 'TEST'),
       (3, 77, 23, 1, 'TEST'),
       (5, 74, 23, 1, 'TEST'),

       (3, 75, 23, 2, 'TEST'),
       (3, 74, 23, 2, 'TEST'),
       (1, 69, 23, 2, 'TEST'),
       (3, 77, 23, 2, 'TEST'),
       (3, 74, 23, 2, 'TEST'),
       (5, 69, 23, 2, 'TEST'),

       (3, 70, 23, 3, 'TEST'),
       (5, 69, 23, 3, 'TEST'),

       (3, 78, 24, 1, 'TEST'),
       (5, 69, 24, 1, 'TEST'),

       (3, 79, 25, 1, 'TEST'),
       (5, 69, 25, 1, 'TEST'),

       (3, 83, 26, 1, 'TEST'),
       (5, 86, 26, 1, 'TEST'),

       (3, 83, 27, 1, 'TEST'),
       (5, 87, 27, 1, 'TEST'),

       (3, 83, 28, 1, 'TEST'),
       (5, 88, 28, 1, 'TEST'),

       (5, 91, 29, 1, 'DEPLOIEMENT'),

       (3, 97, 30, 1, 'DEPLOIEMENT'),
       (3, 96, 30, 1, 'DEPLOIEMENT'),
       (1, 91, 30, 1, 'DEPLOIEMENT'),
       (3, 99, 30, 1, 'DEPLOIEMENT'),
       (3, 96, 30, 1, 'DEPLOIEMENT'),
       (2, 91, 30, 1, 'DEPLOIEMENT'),
       (3, 93, 30, 1, 'DEPLOIEMENT'),
       (3, 97, 30, 1, 'DEPLOIEMENT'),
       (2, 96, 30, 1, 'DEPLOIEMENT'),
       (3, 93, 30, 1, 'DEPLOIEMENT'),
       (3, 99, 30, 1, 'DEPLOIEMENT'),
       (5, 96, 30, 1, 'DEPLOIEMENT'),

       (3, 97, 30, 2, 'DEPLOIEMENT'),
       (3, 96, 30, 2, 'DEPLOIEMENT'),
       (1, 91, 30, 2, 'DEPLOIEMENT'),
       (3, 99, 30, 2, 'DEPLOIEMENT'),
       (3, 96, 30, 2, 'DEPLOIEMENT'),
       (5, 91, 30, 2, 'DEPLOIEMENT'),

       (3, 92, 30, 3, 'DEPLOIEMENT'),
       (5, 91, 30, 3, 'DEPLOIEMENT'),

       (3, 100, 31, 1, 'DEPLOIEMENT'),
       (5, 91, 31, 1, 'DEPLOIEMENT'),

       (3, 101, 32, 1, 'DEPLOIEMENT'),
       (5, 91, 32, 1, 'DEPLOIEMENT'),

       (3, 105, 33, 1, 'DEPLOIEMENT'),
       (5, 108, 33, 1, 'DEPLOIEMENT'),

       (3, 105, 34, 1, 'DEPLOIEMENT'),
       (5, 109, 34, 1, 'DEPLOIEMENT'),

       (3, 105, 35, 1, 'DEPLOIEMENT'),
       (5, 110, 35, 1, 'DEPLOIEMENT'),

       (5, 113, 36, 1, 'MAINTENANCE'),

       (3, 119, 37, 1, 'MAINTENANCE'),
       (3, 118, 37, 1, 'MAINTENANCE'),
       (1, 113, 37, 1, 'MAINTENANCE'),
       (3, 121, 37, 1, 'MAINTENANCE'),
       (3, 118, 37, 1, 'MAINTENANCE'),
       (2, 113, 37, 1, 'MAINTENANCE'),
       (3, 115, 37, 1, 'MAINTENANCE'),
       (3, 119, 37, 1, 'MAINTENANCE'),
       (2, 118, 37, 1, 'MAINTENANCE'),
       (3, 113, 37, 1, 'MAINTENANCE'),
       (3, 121, 37, 1, 'MAINTENANCE'),
       (5, 118, 37, 1, 'MAINTENANCE'),

       (3, 119, 37, 2, 'MAINTENANCE'),
       (3, 118, 37, 2, 'MAINTENANCE'),
       (1, 113, 37, 2, 'MAINTENANCE'),
       (3, 121, 37, 2, 'MAINTENANCE'),
       (3, 118, 37, 2, 'MAINTENANCE'),
       (5, 113, 37, 2, 'MAINTENANCE'),

       (3, 114, 37, 3, 'MAINTENANCE'),
       (5, 113, 37, 3, 'MAINTENANCE'),

       (3, 122, 38, 1, 'MAINTENANCE'),
       (5, 113, 38, 1, 'MAINTENANCE'),

       (3, 123, 39, 1, 'MAINTENANCE'),
       (5, 113, 39, 1, 'MAINTENANCE');


--Création Questions proposées
/*HORS_PHASE*/
INSERT INTO questionpropose (intitule, phase, vote, isapprove)
VALUES ('Est-ce que vous avez des consoles de jeux en salle de pause ?', 'HORS_PHASE', 0, 0);

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
SET dependance = 58
WHERE idquestion = 30;
UPDATE question
SET dependance = 58
WHERE idquestion = 31;
UPDATE question
SET dependance = 62
WHERE idquestion = 32;

/*TEST*/
UPDATE question
SET dependance = null
WHERE idquestion = 33;
UPDATE question
SET dependance = 67
WHERE idquestion = 34;
UPDATE question
SET dependance = 67
WHERE idquestion = 35;
UPDATE question
SET dependance = 67
WHERE idquestion = 36;
UPDATE question
SET dependance = 67
WHERE idquestion = 37;
UPDATE question
SET dependance = 72
WHERE idquestion = 38;
UPDATE question
SET dependance = 72
WHERE idquestion = 39;
UPDATE question
SET dependance = 72
WHERE idquestion = 40;
UPDATE question
SET dependance = 72
WHERE idquestion = 41;
UPDATE question
SET dependance = 67
WHERE idquestion = 42;
UPDATE question
SET dependance = 67
WHERE idquestion = 43;
UPDATE question
SET dependance = 67
WHERE idquestion = 44;
UPDATE question
SET dependance = 80
WHERE idquestion = 45;
UPDATE question
SET dependance = 80
WHERE idquestion = 46;
UPDATE question
SET dependance = 84
WHERE idquestion = 47;

/*DEPLOIEMENT*/
UPDATE question
SET dependance = null
WHERE idquestion = 48;
UPDATE question
SET dependance = 89
WHERE idquestion = 49;
UPDATE question
SET dependance = 89
WHERE idquestion = 50;
UPDATE question
SET dependance = 89
WHERE idquestion = 51;
UPDATE question
SET dependance = 89
WHERE idquestion = 52;
UPDATE question
SET dependance = 94
WHERE idquestion = 53;
UPDATE question
SET dependance = 94
WHERE idquestion = 54;
UPDATE question
SET dependance = 94
WHERE idquestion = 55;
UPDATE question
SET dependance = 94
WHERE idquestion = 56;
UPDATE question
SET dependance = 89
WHERE idquestion = 57;
UPDATE question
SET dependance = 89
WHERE idquestion = 58;
UPDATE question
SET dependance = 89
WHERE idquestion = 59;
UPDATE question
SET dependance = 102
WHERE idquestion = 60;
UPDATE question
SET dependance = 102
WHERE idquestion = 61;
UPDATE question
SET dependance = 106
WHERE idquestion = 62;

/*MAITENANCE*/
UPDATE question
SET dependance = null
WHERE idquestion = 63;
UPDATE question
SET dependance = 111
WHERE idquestion = 64;
UPDATE question
SET dependance = 111
WHERE idquestion = 65;
UPDATE question
SET dependance = 111
WHERE idquestion = 66;
UPDATE question
SET dependance = 111
WHERE idquestion = 67;
UPDATE question
SET dependance = 116
WHERE idquestion = 68;
UPDATE question
SET dependance = 116
WHERE idquestion = 69;
UPDATE question
SET dependance = 116
WHERE idquestion = 70;
UPDATE question
SET dependance = 116
WHERE idquestion = 71;
UPDATE question
SET dependance = 111
WHERE idquestion = 72;
UPDATE question
SET dependance = 111
WHERE idquestion = 73;