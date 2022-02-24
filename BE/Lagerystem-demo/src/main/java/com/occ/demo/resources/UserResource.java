package com.occ.demo.resources;


import com.occ.demo.Entities.UserCredential;
import com.occ.demo.services.PersistenceService;
import com.occ.demo.services.SecurityUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.security.Key;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserResource {

    @Inject
    private SecurityUtil securityUtil;

    @Inject
    PersistenceService persistenceService;



    @Context
    private  UriInfo uriInfo;


    @POST
    @Path("new")
    public Response createUser(@Valid UserCredential user){
        persistenceService.saveUser(user);

        return Response.ok(user).build();
    }

    @POST
    @Path("login") //api/v1/user/login
    public Map<String, String> login(UserCredential user){

        //authenticate the user.
        boolean authenticated = securityUtil.authenticateUser(user.getUsername(), user.getPassword());
        if(!authenticated){
           throw new SecurityException("Email or password incorrect");

        }

        //if authentication succeeds, generate token
        String token = generateToken(user.getUsername());


        Map<String, String> userDetail = new HashMap<>();
        userDetail.put("token", token );
        userDetail.put("username", user.getUsername());
        userDetail.put("expTime", "600");

        return  userDetail;

    }

    private String generateToken(String username) {
        Key securityKey = securityUtil.getSecurityKey();
        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setIssuer(uriInfo.getBaseUri().toString())
                .setAudience(uriInfo.getAbsolutePath().toString())
                .setExpiration(securityUtil.toDate(LocalDateTime.now().plusMinutes(10)))
                .signWith(SignatureAlgorithm.HS512, securityKey).compact();


    }

}

