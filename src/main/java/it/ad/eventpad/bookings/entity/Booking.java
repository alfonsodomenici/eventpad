/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad.bookings.entity;

import it.ad.eventpad.AbstractEntity;
import it.ad.eventpad.events.boundary.EventLinkAdapter;
import it.ad.eventpad.events.entity.Event;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alfonso
 */
@Entity
@Table(name = "booking")
public class Booking extends AbstractEntity {

    @JsonbTypeAdapter(EventLinkAdapter.class)
    @ManyToOne
    @NotNull
    private Event event;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String cognome;
    @NotEmpty
    private String tel;
    @Email
    private String email;
    private boolean confermato;
    private String code;

    public Booking() {
    }

    public Booking(Event event, String nome, String cognome, String tel, String email) {
        this.event = event;
        this.nome = nome;
        this.cognome = cognome;
        this.tel = tel;
        this.email = email;
    }

    /*
    getter/setter
     */
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConfermato() {
        return confermato;
    }

    public void setConfermato(boolean confermato) {
        this.confermato = confermato;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
