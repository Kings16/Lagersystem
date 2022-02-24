package com.occ.demo.resources;


import com.occ.demo.Entities.LagerArtikel;
import com.occ.demo.services.PersistenceService;
import com.occ.demo.services.QueryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/lagerservice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secure // only logged in users can call the methods of this class
public class LagerungsRestResource {

    @Inject
    QueryService queryService;

    @Inject
    PersistenceService persistenceService;


    @GET
    @Path("all") //api/v1/lagerservice/all
    public List<LagerArtikel> getAllArtikel(){
        return queryService.getAllArticle();
    }

    @POST
    @Path("new")
    public Response createArtikel(LagerArtikel artikel){
        persistenceService.createArtikel(artikel);
        return Response.ok(artikel).build();
    }

    @PUT
    @Path("update")
    public Response updateArtikel(LagerArtikel artikel){
        persistenceService.createArtikel(artikel);
        return Response.ok(artikel).build();
    }
}
