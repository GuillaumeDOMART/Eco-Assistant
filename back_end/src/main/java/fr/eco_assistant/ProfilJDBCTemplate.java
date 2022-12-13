package fr.eco_assistant;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class ProfilJDBCTemplate implements ProfilDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(Integer id, String mail, String password, String firstName, String lastName) {
        String SQL = "insert into Profil (idprofil, mail, password, nom, prenom) values (?, ?, ?, ?, ?)";
    }
    public List<Profil> listProfils() {
        String SQL = "select * from Student";
        List <Profil> profils = jdbcTemplateObject.query(SQL, new ProfilMapper());
        return profils;
    }
}
