package fr.eco_assistant.dataBase;

import fr.eco_assistant.dataBase.Mapper.QuestionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class QuestionService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void CreateQuestion(Question question){
        var sql = "INSERT INTO Question (idQuestion, intitule, questionPre, typeQ, phase, categorie, calculQid, visibilite) VALUES\n" +
                "(?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql,
                question.getId(),
                question.getIntitule(),
                question.getQuestionPre(),
                question.getType().toString(),
                question.getPhase().toString(),
                question.getCategorie().toString(),
                question.getCalculQid(),
                question.isVisibilite()? 1 : 0);
    }

    public Question getQuestion(Integer id){
        var sql ="SELECT * FROM Question WHERE id = ? ";
        return jdbcTemplate.queryForObject(sql,new  Object[]{id}, new QuestionRowMapper());
    }

}
