package com.ecoassitant.back.controller;


import com.ecoassitant.back.dto.ReponseDonneesDto;
import com.ecoassitant.back.service.ReponseDonneesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controller for request about ReponseDonnee
 */
@RequestMapping("api")
@RestController
public class ReponseDonneeController {



    private final ReponseDonneeRepository reponseDonneeRepo;

    @Autowired
    public ReponseDonneeController(ReponseDonneeRepository reponseDonneeRepo) {
        this.reponseDonneeRepo = reponseDonneeRepo;
    }

    /**
     * Save a list of reponseDonnee fo a project
     * @param reponseDonneesDto list of reponseDonnee for a project
     * @return true if save successed
     */
    @PostMapping("/reponsesDonnees")
    public boolean saveReponseDonnees(@RequestBody ReponseDonneesDto reponseDonneesDto){
        return reponseDonneesService.saveResponseDonnees(reponseDonneesDto);
    }
    @GetMapping("/reponsed/{id}")
    public ResponseEntity<List<ReponseDonneeDto>> getReponsesDonneesById(@RequestParam("id") long id){
        var reponses = reponseDonneeRepo.findById(id).stream().map(ReponseDonneeDto::new).toList();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if(reponses.isEmpty()){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(reponses,headers, HttpStatus.OK);
        }
    }
}
