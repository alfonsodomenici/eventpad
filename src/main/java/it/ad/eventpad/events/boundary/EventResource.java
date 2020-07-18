/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad.events.boundary;

import it.ad.eventpad.bookings.boundary.BookingsResource;
import it.ad.eventpad.bookings.control.BookingStore;
import it.ad.eventpad.events.control.EventStore;
import it.ad.eventpad.events.entity.Event;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alfonso
 */

public class EventResource {

    @Context
    ResourceContext resource;

    @Inject
    EventStore store;

    @Inject
    BookingStore bookingStore;

    private Long id;

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response find() {
        Event data = store.find(id).orElseThrow(() -> new NotFoundException());
        return Response.ok(data).build();
    }
    
    @Path("bookings")
    public BookingsResource bookings() {
        BookingsResource sub = resource.getResource(BookingsResource.class);
        sub.setEventId(id);
        return sub;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
