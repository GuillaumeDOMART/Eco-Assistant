
\c ecoAssistant

INSERT INTO Question VALUES
(0, 'Depuis combien de temps le projet a t il démarré (en jours)?',null,null,'numerique', 'Questions de depart');
INSERT INTO Question VALUES
(1, 'A quelle phase en êtes vous du projet?',0,null,'qcm', 'Questions de depart');
INSERT INTO Question VALUES
(2, 'Quelle type d entreprise',1,null,'qcm', 'Questions de depart');
INSERT INTO Question VALUES
(3, 'Quelle taille d entreprise (nombre d employés)?',2,null,'numerique', 'Questions de depart');
INSERT INTO Question VALUES
(4, 'Dans quelle domaine se situe le projet',3,null,'qcm', 'Questions de depart');

INSERT INTO Question VALUES
(5, 'Combien de personnes sont impliquées dans cette phrase?',4,null,'numerique', 'Planification');
INSERT INTO Question VALUES
(6, 'Sur une semaine, combien de jours sont-elles présentes sur site en moyenne?',5,null,'numerique', 'Planification');
INSERT INTO Question VALUES
(7, 'Quels transports utilisent-ils majoritairement?',6,null,'qcm', 'Planification');
INSERT INTO Question VALUES
(8, 'Quel est la duré estimée de la phase de planification (en jours)?',7,null,'numerique', 'Planification');
INSERT INTO Question VALUES
(9, 'Quelle langages de programmation utilisés',8,null,'qcm', 'Planification');
INSERT INTO Question VALUES
(10, 'Combien de lignes de codes produites par langages',9,null,'numerique', 'Planification');
INSERT INTO Question VALUES
(11, 'Estimation du temps de réalisation totale du projet?',10,null,'numerique', 'Planification');
INSERT INTO Question VALUES
(12, 'Considerez-vous la phase comme une réussite',11,null,'qcm', 'Planification');

INSERT INTO Question VALUES
(13, 'Combien de personnes impliquées dans cette phase',12,null,'numerique', 'Developpement');
INSERT INTO Question VALUES
(14, 'Sur une semaine, combien de jours sont elles présentés sur un site en moyenne',13,null,'numerique', 'Developpement');
INSERT INTO Question VALUES
(15, 'Quels transports utilisent-ils majoritairement?',14,null,'qcm', 'Developpement');
INSERT INTO Question VALUES
(16, 'Quel est la durée estimée de la phase de développement',15,null,'numerique', 'Developpement');
INSERT INTO Question VALUES
(17, 'Considérez-vous la phase comme une réussite',16,null,'qcm', 'Developpement');

INSERT INTO Question VALUES
(18, 'Combien de personnes impliquées dans cette phase?',17,null,'numerique', 'Test');
INSERT INTO Question VALUES
(19,'Sur une semaine, combien de jours sont-elles présentés sur site en moyenne?',18,null,'numerique', 'Test');
INSERT INTO Question VALUES
(20,'Quel est la durée estimée de la phase de test (en jours)?',19,null,'numerique', 'Test');

INSERT INTO Question VALUES
(21, 'Combien de personnes impliquées dans cette phase?',20,null,'numerique', 'Deploiement');
INSERT INTO Question VALUES
(22,'Sur une semaine, combien de jours sont-elles présentés sur site en moyenne?',21,null,'numerique', 'Deploiement');
INSERT INTO Question VALUES
(23, 'Quel est la durée estimée de la phase de deploiement (en jours)?',22,null,'numerique', 'Deploiement');

INSERT INTO Question VALUES
(24, 'Combien de personnes impliquées dans cette phase?',23,null,'numerique', 'Maintenance');
INSERT INTO Question VALUES
(25,'Sur une semaine, combien de jours sont-elles présentés sur site en moyenne?',24,null,'numerique', 'Maintenance');
INSERT INTO Question VALUES
(26, 'Quel est la durée estimée de la phase de maintenance (en jours)?',25,null,'numerique', 'Maintenance');

INSERT INTO Question VALUES
(27, 'Les choix de technologie étaient-ils correct au final?',26,null,'qcm', 'Questions finales');
INSERT INTO Question VALUES
(28, 'Quel est le temps total de production final du projet?',27,null,'numérique', 'Questions finales');



UPDATE Question SET questionSui = 1, questionPre = null
WHERE idQuestion = 0;
UPDATE Question SET questionSui = 2, questionPre = 0
WHERE idQuestion = 1;
UPDATE Question SET questionSui = 3, questionPre = 1
WHERE idQuestion = 2;
UPDATE Question SET questionSui = 4, questionPre = 2
WHERE idQuestion = 3;
UPDATE Question SET questionSui = 5, questionPre = 3
WHERE idQuestion = 4;
UPDATE Question SET questionSui = 6, questionPre = 4
WHERE idQuestion = 5;
UPDATE Question SET questionSui = 7, questionPre = 5
WHERE idQuestion = 6;
UPDATE Question SET questionSui = 8, questionPre = 6
WHERE idQuestion = 7;
UPDATE Question SET questionSui = 9, questionPre = 7
WHERE idQuestion = 8;
UPDATE Question SET questionSui = 10, questionPre = 8
WHERE idQuestion = 9;
UPDATE Question SET questionSui = 11, questionPre = 9
WHERE idQuestion = 10;
UPDATE Question SET questionSui = 12, questionPre = 10
WHERE idQuestion = 11;
UPDATE Question SET questionSui = 13, questionPre = 11
WHERE idQuestion = 12;
UPDATE Question SET questionSui = 14, questionPre = 12
WHERE idQuestion = 13;
UPDATE Question SET questionSui = 15, questionPre = 13
WHERE idQuestion = 14;
UPDATE Question SET questionSui = 16, questionPre = 14
WHERE idQuestion = 15;
UPDATE Question SET questionSui = 17, questionPre = 15
WHERE idQuestion = 16;
UPDATE Question SET questionSui = 18, questionPre = 16
WHERE idQuestion = 17;
UPDATE Question SET questionSui = 19, questionPre = 17
WHERE idQuestion = 18;
UPDATE Question SET questionSui = 20, questionPre = 18
WHERE idQuestion = 19;
UPDATE Question SET questionSui = 21, questionPre = 19
WHERE idQuestion = 20;
UPDATE Question SET questionSui = 22, questionPre = 20
WHERE idQuestion = 21;
UPDATE Question SET questionSui = 23, questionPre = 21
WHERE idQuestion = 22;
UPDATE Question SET questionSui = 24, questionPre = 22
WHERE idQuestion = 23;
UPDATE Question SET questionSui = 25, questionPre = 23
WHERE idQuestion = 24;
UPDATE Question SET questionSui = 26, questionPre = 24
WHERE idQuestion = 25;
UPDATE Question SET questionSui = 27, questionPre = 25
WHERE idQuestion = 26;
UPDATE Question SET questionSui = 28, questionPre = 26
WHERE idQuestion = 27;
UPDATE Question SET questionSui = null, questionPre = 27
WHERE idQuestion = 28;





INSERT INTO ReponsePossible VALUES
(0,0,'81');
INSERT INTO ReponsePossible VALUES
(1,1,'Planification');
INSERT INTO ReponsePossible VALUES
(2,2,'Microentreprise');
INSERT INTO ReponsePossible VALUES
(3,3,'5');
INSERT INTO ReponsePossible VALUES
(4,4,'IT');
INSERT INTO ReponsePossible VALUES
(5,5,'5');
INSERT INTO ReponsePossible VALUES
(6,6,'1');
INSERT INTO ReponsePossible VALUES
(7,7,'Train/Metro');
INSERT INTO ReponsePossible VALUES
(8,8,'103');
INSERT INTO ReponsePossible VALUES
(9,9,'Java');
INSERT INTO ReponsePossible VALUES
(10,10,'1000');
INSERT INTO ReponsePossible VALUES
(11,11,'130');
INSERT INTO ReponsePossible VALUES
(12,12,'eeeeuh...Oui?');
INSERT INTO ReponsePossible VALUES
(13,13,'3');
INSERT INTO ReponsePossible VALUES
(14,14,'5');
INSERT INTO ReponsePossible VALUES
(15,15,'Train/Metro');
INSERT INTO ReponsePossible VALUES
(16,16,'15');
INSERT INTO ReponsePossible VALUES
(17,17,'Pas encore commencée :)');
INSERT INTO ReponsePossible VALUES
(18,18,'3');
INSERT INTO ReponsePossible VALUES
(19,19,'5');
INSERT INTO ReponsePossible VALUES
(20,20,'5');
INSERT INTO ReponsePossible VALUES
(21,21,'3');
INSERT INTO ReponsePossible VALUES
(22,22,'5');
INSERT INTO ReponsePossible VALUES
(23,23,'5');
INSERT INTO ReponsePossible VALUES
(24,24,'3');
INSERT INTO ReponsePossible VALUES
(25,25,'5');
INSERT INTO ReponsePossible VALUES
(26,26,'1');
INSERT INTO ReponsePossible VALUES
(27,27,'Oui');
INSERT INTO ReponsePossible VALUES
(28,28,'130');





INSERT INTO Profil ("idprofil","mail", "password", "nom","prenom") VALUES
(0,'javaforever@gmail.com','jaimepaspython','Forax','Remi');





INSERT INTO Projet VALUES
(0,0,'test','en cours');








INSERT INTO ReponseDonnee VALUES
(0,0,0,0);
INSERT INTO ReponseDonnee VALUES
(1,0,1,1);
INSERT INTO ReponseDonnee VALUES
(2,0,2,2);
INSERT INTO ReponseDonnee VALUES
(3,0,3,3);
INSERT INTO ReponseDonnee VALUES
(4,0,4,4);
INSERT INTO ReponseDonnee VALUES
(5,0,5,5);
INSERT INTO ReponseDonnee VALUES
(6,0,6,6);
INSERT INTO ReponseDonnee VALUES
(7,0,7,7);
INSERT INTO ReponseDonnee VALUES
(8,0,8,8);
INSERT INTO ReponseDonnee VALUES
(9,0,9,9);
INSERT INTO ReponseDonnee VALUES
(10,0,11,11);
INSERT INTO ReponseDonnee VALUES
(12,0,12,12);
INSERT INTO ReponseDonnee VALUES
(13,0,13,13);
INSERT INTO ReponseDonnee VALUES
(14,0,14,14);
INSERT INTO ReponseDonnee VALUES
(15,0,15,15);
INSERT INTO ReponseDonnee VALUES
(16,0,16,16);
INSERT INTO ReponseDonnee VALUES
(17,0,17,17);
INSERT INTO ReponseDonnee VALUES
(18,0,18,18);
INSERT INTO ReponseDonnee VALUES
(19,0,19,19);
INSERT INTO ReponseDonnee VALUES
(20,0,20,20);
INSERT INTO ReponseDonnee VALUES
(21,0,21,21);
INSERT INTO ReponseDonnee VALUES
(22,0,22,22);
INSERT INTO ReponseDonnee VALUES
(23,0,23,23);
INSERT INTO ReponseDonnee VALUES
(24,0,24,24);
INSERT INTO ReponseDonnee VALUES
(25,0,25,25);
INSERT INTO ReponseDonnee VALUES
(26,0,26,26);
INSERT INTO ReponseDonnee VALUES
(27,0,27,27);
INSERT INTO ReponseDonnee VALUES
(28,0,28,28);
