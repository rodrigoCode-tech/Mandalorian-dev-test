package org.acme.redis;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import io.smallrye.mutiny.Uni;
import org.apache.http.HttpStatus;

@Path("/api/tarefas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TarefasResource {

    @Inject 
    TarefasService service;

    @GET
    public Uni<List<String>> titulo(){
        return service.titulo();
    }

    @POST
    public Response create(Tarefa tarefa){
        if(tarefa.titulo==null || tarefa.titulo.isEmpty()) {
            return Response.status(HttpStatus.SC_BAD_REQUEST)
                    .entity("O título da tarefa deve ser informado")
                    .build();
        }

        if(service.get(tarefa.titulo)!=null){
            return Response.status(HttpStatus.SC_BAD_REQUEST)
                    .entity("Já existe uma tarefa com o título informado")
                    .build();
        }

        service.set(tarefa.titulo, tarefa.anotacao);

        return Response.status(HttpStatus.SC_CREATED)
                .entity(tarefa)
                .build();

    }

    @PUT
    public Response update(Tarefa tarefa){
        if(tarefa.titulo==null || tarefa.titulo.isEmpty()) {
            return Response.status(HttpStatus.SC_BAD_REQUEST)
                    .entity("O título da tarefa deve ser informado")
                    .build();
        }

        if(service.get(tarefa.titulo)==null){
            return Response.status(HttpStatus.SC_NOT_FOUND)
                    .build();
        }

        service.set(tarefa.titulo, tarefa.anotacao);

        return Response.status(HttpStatus.SC_NO_CONTENT)
                .build();
    }

    @GET
    @Path("/{titulo}")
    public Response get(@PathParam("titulo") String titulo){
        String result = service.get(titulo);
        if(result==null){
            return Response.status(HttpStatus.SC_NOT_FOUND)
                    .build();
        }
        return Response.status(HttpStatus.SC_OK)
                .entity(new Tarefa(titulo, String.valueOf(service.get(titulo))))
                .build();
    }

    @DELETE
    @Path("/{titulo}")
    public Uni<Void> delete(@PathParam("titulo") String titulo){
        return service.del(titulo);
    }
}