/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad;

import it.ad.eventpad.events.control.EventStore;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import it.ad.eventpad.users.control.UserStore;
import it.ad.eventpad.users.entity.User;
import java.io.IOException;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 *
 * @author alfonso
 */
@ApplicationScoped
public class Inizializer {

    @Inject
    private UserStore userStore;

    @Inject
    private EventStore eventStore;
        
    @Inject
    Logger LOG;

    public void checkDefaultData(@Observes @Initialized(ApplicationScoped.class) Object doesnMatter) throws IOException {
        LOG.log(Level.INFO,"----------------- Init default initializer -----------------------------");
        Optional<User> defaultAdmin = userStore.findByUsr("admin");
        if (defaultAdmin.isEmpty()) {
            userStore.createDefaultAdmin();
        }
        
        if(eventStore.all().isEmpty()){
            eventStore.importDefaultEvents();
        }
        LOG.log(Level.INFO,"----------------- End default initializer -----------------------------");
    }
}
