package View.Controller;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sede;
import Services.Conferenze;
import Services.Sedi;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ViewConferencesController implements Initializable {
    private Conferenze conferenze = new Conferenze();
    private Sedi sedi = new Sedi();
    @FXML
    private Button cercaPerDataButton;

    @FXML
    private Button cercaPerSedeButton;

    @FXML
    private DatePicker dataFineDP;

    @FXML
    private DatePicker dataInizioDP;

    @FXML
    private ChoiceBox<Sede> sedeChoice;

    @FXML
    private Button viewDetailsButton;

    @FXML
    private Button viewEntiButton;

    @FXML
    private Button viewSessionButton;

    @FXML
    private Button viewSponsorsButton;
    @FXML
    private TableColumn<Conferenza,String> sedeColumn;

    @FXML
    private TableView<Conferenza> tableConferenza;

    @FXML
    private TableColumn<Conferenza, String> userColumn;
    @FXML
    private TableColumn<Conferenza, String> descrizioneColumn;

    @FXML
    private TableColumn<Conferenza, Timestamp> fineConferenzaColumn;

    @FXML
    private TableColumn<Conferenza, Timestamp> inizioConferenzaColumn;

    @FXML
    private TableColumn<Conferenza, String> nomeConferenzaColumn;
    @FXML
    private TableColumn<Conferenza, Float> budgetColumn;

    @FXML
    void cercaPerDataOnAction(ActionEvent event) throws SQLException {
        tableConferenza.setItems(null);
        sedeChoice.setValue(null);
        try{
            Date dataInizio = Date.valueOf(dataInizioDP.getValue());
            Date dataFine   = Date.valueOf(dataFineDP.getValue());
            conferenze.loadByDateInterval(dataInizio,dataFine);
            setTable(conferenze.getConferenze());
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nessuna data selezionata");
            alert.setContentText("Seleziona una data per procedere");
            alert.showAndWait();
        }

    }

    @FXML
    void cercaPerSedeOnAction(ActionEvent event) throws SQLException {
        tableConferenza.setItems(null);
        dataInizioDP.setValue(null);
        dataFineDP.setValue(null);
        try {
            Sede sede = sedeChoice.getSelectionModel().getSelectedItem();
            conferenze = new Conferenze();
            conferenze.loadBySede(sede);
            setTable(conferenze.getConferenze());
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nessuna sede selezionata");
            alert.setContentText("Seleziona una sede per procedere");
            alert.showAndWait();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void viewDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void viewEntiOnAction(ActionEvent event) {

    }

    @FXML
    void viewSessioniOnAction(ActionEvent event) {

    }

    @FXML
    void viewSponsorsOnAction(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sedi.loadSedi();
        sedeChoice.setItems(sedi.getSedi());
    }

    private void setTable(ObservableList<Conferenza> c) {
        tableConferenza.setEditable(false);
        nomeConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        inizioConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
        fineConferenzaColumn.setCellValueFactory(new PropertyValueFactory<>("dataFine"));
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        sedeColumn.setCellValueFactory(new PropertyValueFactory<>("sede"));
        budgetColumn.setCellValueFactory(new PropertyValueFactory<>("budget"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("proprietario"));
        tableConferenza.setItems(c);
    }
}
