/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad.events.control;

import it.ad.eventpad.events.entity.Event;
import it.ad.eventpad.events.entity.EventDateSummary;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alfonso
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EventStore {

    @PersistenceContext(name = "eventpad")
    private EntityManager em;

    public Event save(Event entity) {
        return em.merge(entity);
    }

    public void remove(Long id) {
        Event found = em.find(Event.class, id);
        em.remove(found);
    }

    public Optional<Event> find(Long id) {
        Event found = em.find(Event.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    public List<Event> all() {
        return em.createQuery("select e from Event e order by e.quando", Event.class)
                .getResultList();
    }

    public List<EventDateSummary> dateSummary(){
        return em.createQuery("select new it.ad.eventpad.events.entity.EventDateSummary(e.quando,e.luogo) from Event e GROUP BY e.quando, e.luogo order by e.quando", EventDateSummary.class)
                .getResultList();
    }
    public List<Event> byDate(LocalDate date) {
        return em.createQuery("select e from Event e where e.quando= :quando", Event.class)
                .setParameter("quando", date)
                .getResultList();
    }

    public void addBooking(Long id) {
        em.createQuery("update Event e set e.prenotazioni= e.prenotazioni + 1 where e.id= :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public void removeBooking(Long id) {
        em.createQuery("update Event e set e.prenotazioni= e.prenotazioni - 1 where e.id= :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public void resetBooking(Long id) {
        em.createQuery("update Event e set e.prenotazioni= 0 where e.id= :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public void importDefaultEvents() throws IOException {
        File file = new File(Thread.currentThread().getContextClassLoader()
                .getResource("events.csv").getFile());

        List<String> all = Files.readAllLines(file.toPath());
        all.stream()
                .filter(v -> !v.isEmpty())
                .map(v -> Event.fromCsv(v))
                .forEach(this::save);
    }
}
