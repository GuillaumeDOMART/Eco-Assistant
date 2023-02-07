-- CREATION D'UTILISATEURS DE TEST
INSERT INTO Profil VALUES
    (1, 'admin@demo.fr', 'admin@demo.com', 'DEMO', 'Admin', 1),
    (2, 'createur-dev@demo.fr', 'createur-dev@demo.fr', 'DEMO', 'Createur Dev', 0),
    (3, 'createur-support@demo.fr', 'createur-support@demo.fr', 'DEMO', 'Createur Support', 0),
    (4, 'salarie@demo.fr', 'salarie@demo.fr', 'DEMO', 'Salarie', 0);

-- CREATION DES PROJETS DEVS ET SUPPORT
INSERT INTO Projet VALUES
    (1, 2, 'QUESTIONAIRE POUR DEVELOPPEURS', 'INPROGRESS'),
    (2, 3, "QUESTIONAIRE POUR L'EQUIPE SUPPORT", 'INPROGRESS')

-- CREATION DES QUESTIONS
INSERT INTO Question VALUES
    (1, "Combien d'heures codez-vous par semaine ?", null, 'NUMERIC', 'DEVELOPPEMENT', 'FIRST', 1),
    (2, "Utilisez-vous les méthodes agiles au sein de votre equipe ?", 1, 'QCM', 'PLANIFICATION', 'FIRST', 1),
    (3, "Combiens de jours durre un sprint dans votre équipe", 2, 'NUMERIC', 'PLANIFICATION', 'FIRST', 0);

--- CREATION DES CONSTANTES
INSERT INTO Constante VALUES
    (1, 1, 'source1.com'),
    (2, 1, 'source2.com'),
    (3, 1, 'source3.com'),
    (4, 20, 'source4.com'),
--- CREATION DES REPONSES POSSIBLE
INSERT INTO ReponsePossible VALUES
    (1, 1, 2, "Veuillez entrer un entier", 1),
    (2, 2, 3, 'Oui',2),
    (3, 2, null, 'NON',3),
    (4, 3, null, '"Veuillez entrer un entier"',4),
