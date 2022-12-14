
CREATE DATABASE IF NOT EXISTS ecoAssistant;
\c ecoAssistant

CREATE TABLE IF NOT EXISTS Profil (
    idProfil serial PRIMARY KEY,
    mail VARCHAR (140) NOT NULL,
    password VARCHAR (140) NOT NULL,
    nom VARCHAR (50) NOT NULL,
    prenom VARCHAR (50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Projet (
    idProjet serial PRIMARY KEY,
    idProfil INT NOT NULL,
    nomProjet VARCHAR (50) NOT NULL,
    etat VARCHAR (50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Question (
    idQuestion serial PRIMARY KEY,
    question VARCHAR (255) NOT NULL,
    questionSui INT,
    questionPre INT,
    typeQ VARCHAR (140),
    phase VARCHAR (50),
    FOREIGN KEY (questionSui)
        REFERENCES Question (idQuestion),
    FOREIGN KEY (questionPre)
        REFERENCES Question (idQuestion)
);

CREATE TABLE IF NOT EXISTS ReponsePossible (
    idReponsePos serial PRIMARY KEY,
    questionAsso INT NOT NULL,
    ReponsePos VARCHAR (140) NOT NULL,
    FOREIGN KEY (questionAsso)
        REFERENCES Question (idQuestion)
);

CREATE TABLE IF NOT EXISTS ReponseDonnee (
    idReponse serial PRIMARY KEY,
    idProjet INT NOT NULL,
    idQuestion INT NOT NULL,
    idReponsePos INT NOT NULL,
    FOREIGN KEY (idProjet)
        REFERENCES Projet (idProjet),
    FOREIGN KEY (idQuestion)
        REFERENCES Question (idQuestion),
    FOREIGN KEY (idReponsePos)
        REFERENCES ReponsePossible (idReponsePos)
);

CREATE TABLE IF NOT EXISTS Constante (
    idConstante serial PRIMARY KEY,
    constante INT,
    questionAsso INT,
    tracabilite VARCHAR (255),
    FOREIGN KEY (questionAsso)
        REFERENCES Question (idQuestion)
);

CREATE TABLE IF NOT EXISTS Calcul (
    idCalcul serial PRIMARY KEY,
    calcul VARCHAR (255),
    questionAsso INT,
    tracabilite VARCHAR (255),
    FOREIGN KEY (questionAsso)
        REFERENCES Question (idQuestion)
);

CREATE TABLE IF NOT EXISTS Dependance (
    idQuestionMere INT NOT NULL,
    idQuestionFille INT NOT NULL,
    reponseSaut serial PRIMARY KEY,
    FOREIGN KEY (idQuestionMere)
        REFERENCES Question (idQuestion),
    FOREIGN KEY (idQuestionFille)
        REFERENCES Question (idQuestion),
    FOREIGN KEY (reponseSaut)
        REFERENCES ReponsePossible (idReponsePos)
);
