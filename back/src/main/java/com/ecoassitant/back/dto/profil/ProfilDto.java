package com.ecoassitant.back.dto.profil;

import com.ecoassitant.back.entity.ProfilEntity;
import lombok.Data;

import java.util.Objects;

/**
 * Dto for profil
 */
@Data
public class ProfilDto {

    private Integer id;

    private String mail;

    private String lastName;

    private String firstName;

    private Integer isAdmin;

    /**
     * constructor to create ProfilDto with a ProfilEntity
     * @param entity the entity
     */
    public ProfilDto(ProfilEntity entity){
        Objects.requireNonNull(entity);

        this.id = entity.getIdProfil();
        this.mail = entity.getMail();
        this.lastName = entity.getLastname();
        this.firstName = entity.getFirstname();
        this.isAdmin = entity.getIsAdmin();
    }
}
