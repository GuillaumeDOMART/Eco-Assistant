package com.ecoassitant.back.DataBase;

import org.springframework.jdbc.core.JdbcTemplate;

public class Init {
    private final JdbcTemplate jdbcTemplate;

    public Init(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void createProfil(){
        String sql = "CREATE TABLE IF NOT EXISTS Profil (\n" +
                "    idProfil serial PRIMARY KEY,\n" +
                "    mail VARCHAR (140) NOT NULL,\n" +
                "    password VARCHAR (140) NOT NULL,\n" +
                "    nom VARCHAR (50) NOT NULL,\n" +
                "    prenom VARCHAR (50) NOT NULL\n," +
                "    isAdmin INT NOT NULL\n" +
                ");";
        jdbcTemplate.execute(sql);
    }

    private void createProjet(){
        var sql = "CREATE TABLE IF NOT EXISTS Projet (\n" +
                "    idProjet serial PRIMARY KEY,\n" +
                "    profilId INT NOT NULL,\n" +
                "    nomProjet VARCHAR (50) NOT NULL,\n" +
                "    etat VARCHAR (50) NOT NULL\n," +
                "    FOREIGN KEY (ProfilId)\n" +
                "        REFERENCES Profil (idProfil)\n" +
                ");";
        jdbcTemplate.execute(sql);
    }

    private void createQuestion(){
        var sql = "CREATE TABLE IF NOT EXISTS Question (\n" +
                "    idQuestion serial PRIMARY KEY,\n" +
                "    intitule VARCHAR (255) NOT NULL,\n" +
                "    questionPre INT,\n" +
                "    typeQ VARCHAR (140) NOT NULL,\n" +
                "    phase VARCHAR (50) NOT NULL,\n" +
                "    categorie VARCHAR (50) NOT NULL,\n" +
                "    calculQid INT ,\n" +
                "    visibilite INT, \n" +
                "    FOREIGN KEY (calculQid)\n" +
                "        REFERENCES Question (idQuestion),\n" +
                "    FOREIGN KEY (questionPre)\n" +
                "        REFERENCES Question (idQuestion)\n" +
                ");";
        jdbcTemplate.execute(sql);
    }

    private void createConstante(){
        var sql = "CREATE TABLE IF NOT EXISTS Constante (\n" +
                "    idConstante serial PRIMARY KEY,\n" +
                "    constante INT,\n" +
                "    tracabilite VARCHAR (255)\n" +
                ");";
        jdbcTemplate.execute(sql);
    }
    private void createReponsePossible(){
        var sql = "CREATE TABLE IF NOT EXISTS ReponsePossible (\n" +
                "    idReponsePos serial PRIMARY KEY,\n" +
                "    questionAsso INT NOT NULL,\n" +
                "    questionSuiv INT NOT NULL,\n" +
                "    intitule VARCHAR (140) NOT NULL,\n" +
                "    constanteId INT NOT NULL,\n" +
                "    FOREIGN KEY (questionAsso)\n" +
                "        REFERENCES Question (idQuestion),\n" +
                "    FOREIGN KEY (questionSuiv)\n" +
                "        REFERENCES Question (idQuestion)\n" +
                ");";
        jdbcTemplate.execute(sql);
    }
    private void createReponseDonnee(){
        var sql = "CREATE TABLE IF NOT EXISTS ReponseDonnee (\n" +
                "    projetId INT NOT NULL,\n" +
                "    reponsePosId INT NOT NULL,\n" +
                "    entry INT NOT NULL,\n" +
                "    PRIMARY KEY (projetId, reponsePosId),\n" +
                "    FOREIGN KEY (projetId)\n" +
                "        REFERENCES Projet (idProjet),\n" +
                "    FOREIGN KEY (reponsePosId)\n" +
                "        REFERENCES ReponsePossible (idReponsePos)\n" +
                ");";
        jdbcTemplate.execute(sql);
    }

    private void createCalcul(){
        var sql = "CREATE TABLE IF NOT EXISTES Calcul (\n" +
                "   calculOpId INT NOT NULL, \n" +
                "   reponsePossibleId INT NOT NULL, \n" +
                "   nbCalcul INT NOT NULL, \n" +
                "   PRIMARY KEY (calculOpId, reponsePossibleId),\n" +
                "   FOREIGN KEY (calculOpId)\n" +
                "       REFERENCES CalculOperateur (idCalculOp),\n" +
                "   FOREIGN KEY (reponsePossibleId)\n" +
                "       REFERENCES ReponsePossible (idReponsePos)\n" +
                ");";
        jdbcTemplate.execute(sql);
    }

    private void createCalculOperateur(){
        var sql = "CREATE TABLE IF NOT EXISTES CalculOperateur(" +
                "idCalcul SERIEAL PRIMARY KEY,\n" +
                "operateur VARCHAR(5)\n" +
                ")";
        jdbcTemplate.execute(sql);
    }

    private void deleteCalcul(){
        var sql = "DELETE FROM Calcul";
        jdbcTemplate.execute(sql);
    }

    private void deleteCalculOperateur(){
        var sql = "DELETE FROM CalculOperateur";
        jdbcTemplate.execute(sql);
    }

    private void deleteReponseDonnee(){
        var sql = "DELETE FROM ReponseDonnee;";
        jdbcTemplate.execute(sql);
    }
    private void deleteProjet(){
        var sql = "DELETE FROM Projet;";
        jdbcTemplate.execute(sql);
    }
    private void deleteProfil(){
        var sql = "DELETE FROM Profil;";
        jdbcTemplate.execute(sql);
    }
    private void deleteReponsePossible(){
        var sql = "DELETE FROM ReponsePossible;";
        jdbcTemplate.execute(sql);
    }

    private void deleteConstante(){
        var sql = "DELETE FROM Constante;";
        jdbcTemplate.execute(sql);
    }
    private void deleteQuestion(){
        var sql = "DELETE FROM Question;";
        jdbcTemplate.execute(sql);
    }

    public void createDatabase(){
        createCalculOperateur();
        createCalcul();
        createConstante();
        createProfil();
        createProjet();
        createQuestion();
        createReponsePossible();
        createReponseDonnee();
    }

    public void deleteDatabase(){
        deleteReponseDonnee();
        deleteProjet();
        deleteProfil();
        deleteConstante();
        deleteReponsePossible();
        deleteQuestion();
        deleteCalcul();
        deleteCalculOperateur();
    }
}
