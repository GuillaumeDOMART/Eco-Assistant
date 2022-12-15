package fr.eco_assistant.dataBase.Mapper;

import fr.eco_assistant.dataBase.Resultat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultatRowMapper implements RowMapper<Resultat> {
    @Override
    public Resultat mapRow(ResultSet rs, int rowNum) throws SQLException {
        Resultat resultat = new Resultat(
                rs.getInt("projetId"),
                rs.getInt("reponsePosId"),
                rs.getInt("entry")
        );
        return resultat;
    }
}
