package fr.eco_assistant.DataBase.Mapper;


import fr.eco_assistant.DataBase.Profil;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
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
