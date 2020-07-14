/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad.events.boundary;

import it.ad.eventpad.events.control.EventStore;
import it.ad.eventpad.events.entity.Event;
import it.ad.eventpad.events.entity.EventDateSummary;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alfonso
 */
@Path("/events")
public class EventsResource {

    @Context
    ResourceContext resource;

    @Inject
    EventStore store;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@QueryParam("data") String date) {
        return date == null ? eventsDateSummary() : byDate(LocalDate.parse(date));
    }

    private Response eventsDateSummary() {
        List<EventDateSummary> data = store.dateSummary();
        return Response.ok(data).build();
    }

    private Response byDate(LocalDate date) {
        List<Event> data = store.byDate(date);
        return Response.ok(data).build();
    }

    @Path("{id}")
    public EventResource find(@PathParam("id") Long id) {
        EventResource sub = resource.getResource(EventResource.class);
        sub.setId(id);
        return sub;
    }
}
