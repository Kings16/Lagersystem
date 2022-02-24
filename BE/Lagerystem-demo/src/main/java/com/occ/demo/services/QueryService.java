package com.occ.demo.services;

import com.occ.demo.Entities.LagerArtikel;
import com.occ.demo.Entities.UserCredential;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Stateless
public class QueryService {

    @Inject
    EntityManager entityManager;

   @Context
   private SecurityContext securityContext;


    public List<LagerArtikel> getAllArticle(){
        return entityManager.createNamedQuery(LagerArtikel.FIND_ALL_ARTICLE, LagerArtikel.class)
                .getResultList();
    }
    public UserCredential findUserByUsername(String username) {
        List<UserCredential> userList = entityManager.createNamedQuery(UserCredential.FIND_USER_BY_USERNAME, UserCredential.class).setParameter("username", username)
                .getResultList();

        if (!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    public LagerArtikel findArtikelById(String id){

        return entityManager.find(LagerArtikel.class, id);
    }



}
