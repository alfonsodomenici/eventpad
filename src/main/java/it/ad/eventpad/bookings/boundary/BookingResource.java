/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad.bookings.boundary;

import it.ad.eventpad.bookings.control.BookingStore;
import it.ad.eventpad.bookings.entity.Booking;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alfonso
 */
public class BookingResource {

    @Inject
    BookingStore store;

    private Long id;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response find() {
        Booking data = store.find(id).orElseThrow(() -> new NotFoundException());
        return Response.ok(JsonbBuilder.create().toJson(data)).build();
    }

    @DELETE
    public Response delete() {
        store.remove(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    public void setId(Long id) {
        this.id = id;
    }

}
