package fr.eco_assistant.dataBase.Service;

import fr.eco_assistant.dataBase.Mapper.ReponseRowMapper;
import fr.eco_assistant.dataBase.Reponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ReponseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public  void createReponse(Reponse reponse){
        var sql = "INSERT INTO ReponsePossible (idReponsePos, questionAsso, questionSuiv, intitule, constanteId) VALUES\n" +
                "(?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, reponse.getId(), reponse.getQuestionAsso(), reponse.getQuestionSuiv(), reponse.getIntitule(), reponse.getConstanteId());
    }

    public Reponse getReponse(Integer id){
        var sql = "SELECT * FROM ReponsePossible WHERE idReponsePos = ?";
        return jdbcTemplate.queryForObject(sql, new  Object[]{id}, new ReponseRowMapper());
    }

    public List<Reponse> reponses (Integer questionId){
        var sql = "SELECT * FROM ReponsePossible WHERE questionAsso = ?";
        return jdbcTemplate.query(sql,new  Object[]{questionId}, new ReponseRowMapper());
    }
}
