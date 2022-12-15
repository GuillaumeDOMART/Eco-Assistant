package fr.eco_assistant.dataBase.Mapper;

import fr.eco_assistant.dataBase.Enum.Etat;
import fr.eco_assistant.dataBase.Projet;
import fr.eco_assistant.dataBase.Service.ProjetService;
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
