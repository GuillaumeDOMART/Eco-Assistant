package fr.eco_assistant;

import fr.eco_assistant.dataBase.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class BackEndApplication {
    @Autowired
    private EmailSenderService senderService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }
    @EventListener(ApplicationReadyEvent.class)
    public void sendMail() {
        //senderService.sendEmail("guillaumedomart@gmail.com",
        //        "Test",
        //        "This is theBody Test");

    }

    @EventListener(ApplicationReadyEvent.class)
    public void generateDatabase() {
        Database database = new Database(jdbcTemplate);
        database.createDatabase();
    }


}
