package Presenter;

import DAO.ConferenzaDao;
import Model.Conferenze.Conferenza;
import Model.Utente;
import Observers.ConferenzeObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConferenceModel {
    private ObservableList<Conferenza> conferenze;
    private ObservableList<Conferenza> conferenzeUtente;
    private List<ConferenzeObserver> observers = new ArrayList<>();
    public ConferenceModel(){
        this.conferenze = FXCollections.observableArrayList();
        this.conferenzeUtente = FXCollections.observableArrayList();
    }
    public void loadConferenze() throws SQLException {
        ConferenzaDao dao = new ConferenzaDao();
        conferenze.clear();
        conferenze.addAll(dao.getAllConferenze());
    }
    public void loadConferenzeUtente(Utente user) throws SQLException {
        ConferenzaDao dao = new ConferenzaDao();
        conferenzeUtente.clear();
        conferenzeUtente.addAll(dao.getAllConferenzeByUtente(user));
    }
    public void addConferenza(Conferenza conferenza){
        conferenze.add(conferenza);
        notifyObserversOnConferenzaAdded(conferenza);
    }

    private void notifyObserversOnConferenzaAdded(Conferenza conferenza) {
        for(ConferenzeObserver observer : observers){
            observer.onConferenzaAdded(conferenza);
        }
    }

    public void removeConferenza(Conferenza conferenza){
        conferenze.remove(conferenza);
        notifyObserversOnConferenzaRemoved(conferenza);
    }

    private void notifyObserversOnConferenzaRemoved(Conferenza conferenza) {
        for(ConferenzeObserver observer : observers){
            observer.onConferenzaRemoved(conferenza);
        }
    }

    public ObservableList<Conferenza> getConferenzeUtente() {
        return conferenzeUtente;
    }

    public void setConferenzeUtente(ObservableList<Conferenza> conferenzeUtente) {
        this.conferenzeUtente = conferenzeUtente;
    }

    public void addObserver(ConferenzeObserver observer){
        observers.add(observer);
    }
    public void removeObserver(ConferenzeObserver observer){
        observers.remove(observer);
    }
}
