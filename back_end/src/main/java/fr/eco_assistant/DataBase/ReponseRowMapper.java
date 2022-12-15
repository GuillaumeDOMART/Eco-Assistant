package fr.eco_assistant.DataBase;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReponseRowMapper implements RowMapper<Reponse> {
    @Override
    public Reponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        Reponse reponse = new Reponse(
                rs.getInt("idReponsePos"),
                rs.getInt("questionAsso"),
                rs.getInt("questionSuiv"),
                rs.getString("intitule"),
                rs.getInt("constanteId")
        );
        return reponse;
    }
}
