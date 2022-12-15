package fr.eco_assistant.DataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProfilService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createProfil(Profil profil){
        var sql = "INSERT INTO Profil (idprofil,mail, password, nom,prenom) VALUES\n" +
                "(?, ?, ?, ?, ?, 0);";
        jdbcTemplate.update(sql, profil.id(), profil.mail(), profil.password(), profil.nom(), profil.prenom());
    }
    public Profil getProfil(Integer id){
        var sql = "SELECT * FROM Profil WHERE id = ? ";
        return jdbcTemplate.queryForObject(sql,new  Object[]{id}, new ProfilRowMapper());
    }
}
