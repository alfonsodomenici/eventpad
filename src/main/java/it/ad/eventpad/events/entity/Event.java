/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ad.eventpad.events.entity;

import it.ad.eventpad.AbstractEntity;
import java.time.LocalDate;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author alfonso
 */
@Entity
@Table(name = "event")
public class Event extends AbstractEntity {

    private LocalDate quando;
    private String ora;
    private String luogo;
    private Integer posti;
    private Integer prenotazioni;
    private String categoria;
    private String titolo;
    @Column(length = 2048)
    private String descrizione;

    public Event() {
    }

    public Event(LocalDate quando,String ora, String luogo, Integer posti, Integer prenotazioni, String categoria, String titolo, String descrizione) {
        this.quando = quando;
        this.ora = ora;
        this.luogo = luogo;
        this.posti = posti;
        this.prenotazioni = prenotazioni;
        this.categoria = categoria;
        this.titolo = titolo;
        this.descrizione = descrizione;
    }

    public boolean isEsaurito() {
        return prenotazioni >= posti;
    }

    public static Event fromCsv(String line) {
        System.out.println(line);
        String[] fields = line.split("\\|");
        System.out.println(Arrays.toString(fields));
        return new Event(LocalDate.parse(fields[0]), fields[1], fields[2], Integer.parseInt(fields[3]), 0, fields[4], fields[5], fields[6]);
    }

    /*
    getter/setter
     */

    public LocalDate getQuando() {
        return quando;
    }

    public void setQuando(LocalDate quando) {
        this.quando = quando;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public Integer getPosti() {
        return posti;
    }

    public void setPosti(Integer posti) {
        this.posti = posti;
    }

    public Integer getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(Integer prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    
}
