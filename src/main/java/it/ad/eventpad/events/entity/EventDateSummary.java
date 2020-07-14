/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad.events.entity;

import java.time.LocalDate;

/**
 *
 * @author alfonso
 */
public class EventDateSummary {
    
    public final LocalDate quando;
    public final String luogo;

    public EventDateSummary(LocalDate quando, String luogo) {
        this.quando = quando;
        this.luogo = luogo;
    }
    
    
}
