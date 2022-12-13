package fr.eco_assistant;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        ProfilJDBCTemplate profilJDBCTemplate = (ProfilJDBCTemplate)
                context.getBean("profilJDBCTemplate");

        System.out.println("------Records Creation--------");
        profilJDBCTemplate.create(0, "guillaume@gmail.com", "normanforever", "guillaume", "domart");
        profilJDBCTemplate.create(1, "leo@gmail.com", "sangoku", "leo", "barroux");
        profilJDBCTemplate.create(2, "guigui@gmail.com", "orangeveutmapeau", "guillaume", "laginha");

        System.out.println("------Listing Multiple Records--------");
        List<Profil> students = profilJDBCTemplate.listProfils();

        for (Profil record : students) {
            System.out.print("ID : " + record.getId());
            System.out.print(", First Name : " + record.getFirstName());
            System.out.println(", Last Name : " + record.getLastName());
            System.out.println(", mail : " + record.getMail());
            System.out.println(", password : " + record.getPassword());
        }
    }
}
