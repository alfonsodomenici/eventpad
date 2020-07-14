/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad.bookings.control;

import it.ad.eventpad.bookings.entity.Booking;
import it.ad.eventpad.events.control.EventStore;
import it.ad.eventpad.events.entity.Event;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alfonso
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BookingStore {

    @PersistenceContext(name = "eventpad")
    private EntityManager em;

    @Inject
    EventStore eventStore;
    
    public Booking save(Booking entity) {
        Booking saved = em.merge(entity);
        eventStore.addBooking(saved.getEvent().getId());
        return saved;
    }

    public void remove(Long id) {
        Booking found = em.find(Booking.class, id);
        eventStore.removeBooking(found.getEvent().getId());
        em.remove(found);
    }

    public void removeByEvent(Long eventId) {
        em.createQuery("delete from Booking e where e.event.id= :eventId")
                .setParameter("eventId", eventId)
                .executeUpdate();
        eventStore.resetBooking(eventId);
    }
    
    public Optional<Booking> find(Long id) {
        Booking found = em.find(Booking.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    public List<Booking> byEvent(Long eventId) {
        return em.createQuery("select e from Booking e where e.event.id= :eventId", Booking.class)
                .setParameter("eventId", eventId)
                .getResultList();
    }
}
