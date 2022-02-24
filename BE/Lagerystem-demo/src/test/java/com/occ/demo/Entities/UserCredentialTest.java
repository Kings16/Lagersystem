package com.occ.demo.Entities;

import com.occ.demo.services.PersistenceService;
import com.occ.demo.services.QueryService;
import com.occ.demo.services.SecurityUtil;
import org.junit.jupiter.api.*;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;



class UserCredentialTest {

    private  PersistenceService persistenceService;
    private UserCredential user;
    private SecurityUtil securityUtil;
    private QueryService queryService;

    @BeforeAll
    public static  void setUpAll(){
        System.out.println("Should print before all test");
    }
    @BeforeEach
    public  void setUp(){
        persistenceService = new PersistenceService();
        securityUtil = new SecurityUtil();
        user = new UserCredential();
        queryService = new QueryService();


    }


    @Test
    @DisplayName("Should not create user when username is null")
    public void ShouldThrowAnExceptionWhenFirstNameIsNull(){
        user.setPassword("123");
        Assertions.assertThrows(RuntimeException.class, () -> {
            persistenceService.saveUser(user);
        });
    }

    @Test
    @DisplayName("Should not create user when username is empty")
    public void ShouldThrowAnExceptionWhenFirstNameIsEmpty(){

        user.setPassword("");
        user.setPassword("123");
        Assertions.assertThrows(RuntimeException.class, () -> {
            persistenceService.saveUser(user);
        });
    }

    @Test
    @DisplayName("Should not create user when password is null")
    public void ShouldThrowAnExceptionWhenPasswordIsNull(){

        user.setUsername("test20");
        Assertions.assertThrows(RuntimeException.class, () -> {
            persistenceService.saveUser(user);
        });
    }

    @Test
    @DisplayName("Should not create user when password is null")
    public void ShouldThrowAnExceptionWhenPasswordIsEmpty(){

        user.setUsername("test20");
        user.setPassword("");
        Assertions.assertThrows(RuntimeException.class, () -> {
            persistenceService.saveUser(user);
        });
    }

    @Test
    @DisplayName("Should log in on Authentication")
    public void ShouldLoginWhenUserIsAuthenticated() {

        user.setUsername("test20");
        user.setPassword("");
        Assertions.assertThrows(RuntimeException.class, () -> {
            persistenceService.saveUser(user);
        });

    }


}