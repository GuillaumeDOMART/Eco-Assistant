package fr.eco_assistant.dataBase.Service;

import fr.eco_assistant.dataBase.Mapper.ProfilRowMapper;
import fr.eco_assistant.dataBase.Profil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProfilService {

    private final JdbcTemplate jdbcTemplate;

    public ProfilService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createProfil(Profil profil){
        var sql = "INSERT INTO Profil (mail, password, nom, prenom, isAdmin) VALUES\n" +
                "(?, ?, ?, ?, 0);";
        jdbcTemplate.update(sql, profil.getMail(), profil.getPassword(), profil.getNom(), profil.getPrenom());
    }
    public Profil getProfil(Integer id){
        var sql = "SELECT * FROM Profil WHERE idProfil = ? ";
        return jdbcTemplate.queryForObject(sql,new  Object[]{id}, new ProfilRowMapper());
    }
}
