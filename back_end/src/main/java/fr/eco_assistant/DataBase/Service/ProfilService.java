package fr.eco_assistant.DataBase.Service;

import fr.eco_assistant.DataBase.Mapper.ProfilRowMapper;
import fr.eco_assistant.DataBase.Profil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProfilService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createProfil(Profil profil){
        var sql = "INSERT INTO Profil (idprofil,mail, password, nom,prenom) VALUES\n" +
                "(?, ?, ?, ?, ?, 0);";
        jdbcTemplate.update(sql, profil.getId(), profil.getMail(), profil.getPassword(), profil.getNom(), profil.getPrenom());
    }
    public Profil getProfil(Integer id){
        var sql = "SELECT * FROM Profil WHERE idProfil = ? ";
        return jdbcTemplate.queryForObject(sql,new  Object[]{id}, new ProfilRowMapper());
    }
}
