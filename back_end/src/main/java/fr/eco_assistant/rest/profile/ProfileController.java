package fr.eco_assistant.rest.profile;

import fr.eco_assistant.dataBase.Profil;
import fr.eco_assistant.dataBase.Service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfileController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/add")
    public ResponseEntity<Profile> add(@RequestBody Profile profile){
        ProfilService profilService = new ProfilService(jdbcTemplate);
        Profil profilToCreate = new Profil(profile.getEmail(), profile.getPassword(), profile.getNom(), profile.getPrenom(), false);
        profilService.createProfil(profilToCreate);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
