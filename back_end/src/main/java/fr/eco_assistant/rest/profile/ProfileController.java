package fr.eco_assistant.rest.profile;

import fr.eco_assistant.dataBase.Profil;
import fr.eco_assistant.dataBase.Service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        Profil profileToCreate = new Profil(profile.getEmail(), profile.getPassword(), profile.getNom(), profile.getPrenom(), false);
        profilService.createProfil(profileToCreate);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PostMapping("/get")
    public ResponseEntity<Profil> get(@RequestBody Profile profile) {
        ProfilService profilService = new ProfilService(jdbcTemplate);
        Profil profileToGet = new Profil(profile.getEmail(), profile.getPassword(), profile.getNom(), profile.getPrenom(), false);
        var profil = profilService.getProfil(profileToGet);
        if (profil == null)
            return new ResponseEntity<>(null, HttpStatus.OK);
        return new ResponseEntity<>(profil, HttpStatus.OK);
    }
}
