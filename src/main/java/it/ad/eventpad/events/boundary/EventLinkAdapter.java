/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad.events.boundary;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import javax.ws.rs.NotFoundException;
import it.ad.eventpad.events.entity.Event;
import it.ad.eventpad.events.control.EventStore;
/**
 *
 * @author alfonso
 */

public class EventLinkAdapter implements JsonbAdapter<Event, JsonObject> {

    @Inject
    EventStore store;

    @Override
    public JsonObject adaptToJson(Event event) throws Exception {
        return Json.createObjectBuilder()
                .add("id", event.getId())
                .add("titolo", event.getTitolo())
                .build();
    }

    @Override
    public Event adaptFromJson(JsonObject json) throws Exception {
        return store.find(Long.valueOf(json.getInt("id"))).orElseThrow(() -> new NotFoundException());
    }

}
