package fr.eco_assistant;

import javax.sql.DataSource;
import java.util.List;

public interface ProfilDAO {
    public void setDataSource(DataSource ds);
    public void create(Integer id, String mail, String password, String firstName, String lastName);
    public List<Profil> listProfils();
}
