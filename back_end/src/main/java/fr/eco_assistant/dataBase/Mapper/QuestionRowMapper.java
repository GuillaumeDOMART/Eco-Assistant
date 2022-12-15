package fr.eco_assistant.dataBase.Mapper;

import fr.eco_assistant.dataBase.Enum.Categorie;
import fr.eco_assistant.dataBase.Enum.PhaseE;
import fr.eco_assistant.dataBase.Question;
import fr.eco_assistant.dataBase.Enum.TypeQ;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionRowMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question question = new Question(
                rs.getInt("idQuestion"),
                rs.getString("intitule"),
                rs.getInt("questionPre"),
                TypeQ.valueOf(rs.getString("typeQ")),
                PhaseE.valueOf(rs.getString("phase")),
                Categorie.valueOf(rs.getString("categorie")),
                rs.getInt("calculQid"),
                rs.getInt("visibilite") == 1 ? true : false);
        return question;
    }
}
