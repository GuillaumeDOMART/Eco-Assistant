package fr.eco_assistant.DataBase.Mapper;

import fr.eco_assistant.DataBase.Enum.Etat;
import fr.eco_assistant.DataBase.Projet;
import fr.eco_assistant.DataBase.Service.ProjetService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjetRowMapper implements RowMapper<Projet> {
    @Override
    public Projet mapRow(ResultSet rs, int rowNum) throws SQLException {
        Projet projet = new Projet(
                rs.getInt("idProjet"),
                rs.getInt("profilId"),
                rs.getString("nomProjet"),
                Etat.valueOf(rs.getString("etat")),
                new ProjetService().getResultat(rs.getInt("idProjet"))
        );
        return  projet;
    }
}
