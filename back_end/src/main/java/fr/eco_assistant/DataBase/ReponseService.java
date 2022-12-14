package fr.eco_assistant.DataBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ReponseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public  void createReponse(Reponse reponse){
        var sql = "INSERT INTO ReponsePossible (idReponsePos, questionAsso, questionSuiv, intitule, constanteId) VALUES\n" +
                "(?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, reponse.id(), reponse.questionAsso(), reponse.questionSuiv(), reponse.intitule(), reponse.constanteId());
    }

    public Reponse getReponse(Integer id){
        var sql = "SELECT * FROM ReponsePossible WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new  Object[]{id}, new ReponseRowMapper());
    }

    public List<Reponse> reponses (Integer questionId){
        var sql = "SELECT * FROM ReponsePossible WHERE questionAsso = ?";
        return jdbcTemplate.query(sql,new  Object[]{questionId}, new ReponseRowMapper());
    }
}
