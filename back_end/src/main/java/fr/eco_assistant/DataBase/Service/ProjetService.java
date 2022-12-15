package fr.eco_assistant.DataBase.Service;

import fr.eco_assistant.DataBase.Mapper.ProjetRowMapper;
import fr.eco_assistant.DataBase.Projet;
import fr.eco_assistant.DataBase.Mapper.ResultatRowMapper;
import fr.eco_assistant.DataBase.Resultat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ProjetService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void createProject(Projet projet){
        var sql = "INSERT INTO Projet (idProjet, profilId, nomProjet, etat) VALUES\n" +
                "(?, ?, ?, ?);";
        jdbcTemplate.update(sql, projet.getId(), projet.getProfilId(), projet.getNomProjet(), projet.getEtat());
    }
    private void createResultat(Resultat resultat){
        var sql = "INSERT INTO ReponseDonnee (projetId, reponsePosId, entry) VALUES\n" +
                "(?, ?, ?);";
        jdbcTemplate.update(sql, resultat.getProjetId(), resultat.getReponsePosId(), resultat.getEntry());
    }

    public List<Resultat> getResultat(Integer projetId){
        var sql = "SELECT * FROM ReponseDonnee WHERE projetId = ? ";
        return jdbcTemplate.query(sql, new ResultatRowMapper(), projetId);
    }

    public Projet getProjet(Integer idProjet){
        var sql = "SELECT * Projet Profil WHERE idProjet = ? ";
        return jdbcTemplate.queryForObject(sql,new  Object[]{idProjet}, new ProjetRowMapper());
    }
}
