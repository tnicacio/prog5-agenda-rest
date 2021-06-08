package com.tnicacio.agendarest.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.tnicacio.agendarest.model.dao.DaoFactory;
import com.tnicacio.agendarest.model.dao.ParticipanteDAO;
import com.tnicacio.agendarest.model.entities.Participante;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author tnica
 */
@Path("participantes")
public class ParticipanteResource {

    @Context
    private UriInfo context;

    private ParticipanteDAO dao;
    private Gson gson;
    
    public ParticipanteResource() {
        dao = DaoFactory.createParticipanteDao();
        gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy HH:mm:ss")
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @GET
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Participante> participantes = dao.findAll();
        return Response.status(Response.Status.OK).entity(gson.toJson(participantes)).build();
    }
    
    @GET
    @Path("{id}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response findById(@PathParam ("id") String id) {
        Participante participante = dao.findById(Long.parseLong(id));
        if (participante == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(participante)).build();
    }
    
    @POST
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response insert(String content) {
        try {
            Participante participante = gson.fromJson(content, Participante.class);
            dao.insert(participante);
            return Response.status(Response.Status.CREATED).build();
        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response putJson(String content, @PathParam ("id") String id) {
        try {
            Participante participante = gson.fromJson(content, Participante.class);
            dao.update(participante, Long.parseLong(id));
            return Response.status(Response.Status.OK).entity(gson.toJson(participante)).build();
        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @DELETE
    @Path("{id}")
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response delete(@PathParam ("id") String id) {
        try {
            dao.delete(Long.parseLong(id));
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
