package com.occ.demo.services;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class CDIProducer {

    @Produces //to make entity manager injectable
    @PersistenceContext
    EntityManager entityManager;
}
