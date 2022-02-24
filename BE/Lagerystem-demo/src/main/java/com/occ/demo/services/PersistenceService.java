package com.occ.demo.services;

import com.occ.demo.Entities.LagerArtikel;
import com.occ.demo.Entities.UserCredential;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Stateless
public class PersistenceService {

    @Inject
    EntityManager entityManager;

    @Inject
    QueryService queryService;

    @Inject
    SecurityUtil securityUtil;

    //the method be use to create new Article and also update already Existing Article in the database
    public LagerArtikel createArtikel(LagerArtikel lagerArtikel){
        LagerArtikel artikel = queryService.findArtikelById(lagerArtikel.getId());
        if(artikel == null){
            //in case the aricle does not exist
            entityManager.persist(lagerArtikel);
        }
        // if article exists, then update
        entityManager.merge(lagerArtikel);

        return lagerArtikel;
    }



    public UserCredential saveUser(UserCredential userCredential){
        UserCredential user = queryService.findUserByUsername(userCredential.getUsername());

        //create useer only when user does not exist
        if(user == null){
            Map<String, String> credMap = securityUtil.hashPassword(userCredential.getPassword());

            userCredential.setPassword(credMap.get(SecurityUtil.HASHED_PASSWORD_KEY));
            userCredential.setSalt(credMap.get(SecurityUtil.SALT_KEY));

            entityManager.persist(userCredential);
            credMap.clear();

        }return  userCredential;

    }
}
