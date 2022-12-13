package fr.eco_assistant;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ProfilMapper implements RowMapper<Profil> {
    public Profil mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profil profil = new Profil();
        profil.setId(rs.getInt("id"));
        profil.setMail(rs.getString("mail"));
        profil.setFirstName(rs.getString("firstName"));
        profil.setLastName(rs.getString("lastName"));
        return profil;
    }
}
