/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad.bookings.boundary;

import it.ad.eventpad.bookings.control.BookingStore;
import it.ad.eventpad.bookings.entity.Booking;
import it.ad.eventpad.events.control.EventStore;
import it.ad.eventpad.events.entity.Event;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alfonso
 */
@PermitAll
public class BookingsResource {

    @Context
    ResourceContext resource;

    @Inject
    BookingStore store;

    @Inject
    EventStore eventStore;

    private Long eventId;

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String json) {
        Booking entity = JsonbBuilder.create().fromJson(json, Booking.class);
        if (!store.isUnique(entity)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("caused-by", "Prenotazione esistente")
                    .build();
        }
        Event event = eventStore.find(eventId).orElseThrow(() -> new NotFoundException());
        entity.setEvent(event);
        Booking saved = store.create(entity);
        return Response
                .status(Response.Status.CREATED)
                .entity(JsonbBuilder.create().toJson(saved))
                .build();
    }

    @RolesAllowed("admin")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        List<Booking> data = store.byEvent(eventId);
        return Response.ok(JsonbBuilder.create().toJson(data)).build();
    }

    @Path("{id}")
    public BookingResource find(@PathParam("id") Long id) {
        BookingResource sub = resource.getResource(BookingResource.class);
        sub.setId(id);
        return sub;
    }

    @RolesAllowed("admin")
    @DELETE
    public Response deleteAll() {
        store.removeByEvent(eventId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
