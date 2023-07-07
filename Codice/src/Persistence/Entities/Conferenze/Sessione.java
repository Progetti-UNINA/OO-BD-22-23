package Persistence.Entities.Conferenze;
import Persistence.Entities.organizzazione.Organizzatore;

import java.sql.Timestamp;
import java.util.Objects;

public class Sessione {
    private int id_sessione;
    private Timestamp inizio;
    private Timestamp fine;
    private String titolo;
    private Sala locazione;
    private Organizzatore coordinatore;
    private Conferenza conferenza;
    private Programma programma;


    public Sessione(){}

    public Sessione(int sessioneID, Timestamp inizio, Timestamp fine, String titolo, Sala locazione, Organizzatore coordinatore, Conferenza conferenza, Programma programma) {
        this.id_sessione = sessioneID;
        this.inizio = inizio;
        this.fine = fine;
        this.titolo = titolo;
        this.locazione = locazione;
        this.coordinatore = coordinatore;
        this.conferenza = conferenza;
        this.programma = programma;
    }

    public int getId_sessione() {
        return id_sessione;
    }

    public void setId_sessione(int id_sessione) {
        this.id_sessione = id_sessione;
    }

    public Timestamp getInizio() {
        return inizio;
    }

    public void setInizio(Timestamp inizio) {
        this.inizio = inizio;
    }

    public Timestamp getFine() {
        return fine;
    }

    public void setFine(Timestamp fine) {
        this.fine = fine;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Sala getLocazione() {
        return locazione;
    }

    public void setLocazione(Sala locazione) {
        this.locazione = locazione;
    }

    public Organizzatore getCoordinatore() {
        return coordinatore;
    }

    public void setCoordinatore(Organizzatore coordinatore) {
        this.coordinatore = coordinatore;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sessione sessione = (Sessione) o;

        return Objects.equals(titolo, sessione.titolo);
    }

    public Programma getProgramma() {
        return programma;
    }

    public void setProgramma(Programma programma) {
        this.programma = programma;
    }

    @Override
    public int hashCode() {
        return titolo != null ? titolo.hashCode() : 0;
    }

    @Override
    public String toString() {
        return titolo;
    }
}
