package com.tnicacio.agendarest.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.tnicacio.agendarest.model.dao.CompromissoDAO;
import com.tnicacio.agendarest.model.dao.DaoFactory;
import com.tnicacio.agendarest.model.entities.Compromisso;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author tnica
 */
@Path("compromissos")
public class CompromissoResource {

    @Context
    private UriInfo context;

    private CompromissoDAO dao;
    private Gson gson;

    public CompromissoResource() {
        dao = DaoFactory.createCompromissoDao();
        gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy HH:mm:ss")
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
    
    private boolean hasPathParamWithPositiveValue(String param) throws NumberFormatException{
        return param != null && !"".equals(param.trim()) && Long.valueOf(param) > 0;
    }

    @GET
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response findAll(
            @DefaultValue("0") @QueryParam("local") String localId,
            @DefaultValue("0") @QueryParam("contato") String contatoId) {
        
        try {
            List<Compromisso> compromissos = new ArrayList<>();
            boolean hasLocalIdParam = hasPathParamWithPositiveValue(localId); 
            boolean hasContatoIdParam = hasPathParamWithPositiveValue(contatoId); 

            if (hasLocalIdParam && !hasContatoIdParam) {
                compromissos = dao.findByLocalId(Long.valueOf(localId));
            } else if(!hasLocalIdParam && hasContatoIdParam) {
                compromissos = dao.findByContatoId(Long.valueOf(contatoId));
            } else if(!hasLocalIdParam && !hasContatoIdParam) {
                compromissos = dao.findAll();
            }
            return Response.status(Response.Status.OK).entity(gson.toJson(compromissos)).build();
        
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") String id) {
        Compromisso compromisso = dao.findById(Long.parseLong(id));
        if (compromisso == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(compromisso)).build();
    }

    @POST
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response insert(String content) {
        try {
            Compromisso compromisso = gson.fromJson(content, Compromisso.class
            );
            dao.insert(compromisso);
            return Response.status(Response.Status.CREATED).build();
        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response update(String content, @PathParam("id") String id) {
        try {
            Compromisso compromisso = gson.fromJson(content, Compromisso.class
            );
            dao.update(compromisso, Long.parseLong(id));
            return Response.status(Response.Status.OK).entity(gson.toJson(compromisso)).build();
        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
        try {
            dao.delete(Long.parseLong(id));
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
  
}
