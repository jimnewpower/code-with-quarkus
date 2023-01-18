package org.acme.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/help")
public class HelpResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String help() {
        return "This is a Quarkus demo application that connects with a Postgres database.";
    }
}