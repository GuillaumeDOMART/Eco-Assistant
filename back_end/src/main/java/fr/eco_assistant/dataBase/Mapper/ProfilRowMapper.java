package fr.eco_assistant.dataBase.Mapper;


import fr.eco_assistant.dataBase.Profil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfilRowMapper implements RowMapper<Profil> {

    @Override
    public Profil mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profil profil = new Profil(
                rs.getInt("idProfil"),
                rs.getString("mail"),
                rs.getString("password"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getInt("isAdmin") == 1 ? true : false);
        return profil;
    }
}
