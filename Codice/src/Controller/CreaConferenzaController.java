package Controller;

import Model.Conferenze.Sede.SedeModel;
import Model.Utente;
import Model.organizzazione.Ente.Ente;
import Model.organizzazione.Ente.EnteModel;
import Model.organizzazione.Sponsor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CreaConferenzaController implements Initializable{

    private Utente user;
    private SedeModel sedi = new SedeModel();
    private EnteModel enti = new EnteModel();
    @FXML
    private Button annullaButton;
    @FXML
    private DatePicker dataFineDP;
    @FXML
    private DatePicker dataInizioDP;
    @FXML
    private Button creaButton;
    @FXML
    private ComboBox<String> entiSelection;
    @FXML
    private TextField nomeConferenzaTF;
    @FXML
    private ChoiceBox<String> sedeSelection;
    @FXML
    private ComboBox<Sponsor> sponsorSelection;

    public Utente getUser() {
        return user;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    @FXML
    public void creaButtonOnAction(ActionEvent event){}
    @FXML
    public void showSedi(MouseEvent event){
        sedeSelection.show();
    }
    @FXML
    public void showEnti(ActionEvent event){
        entiSelection.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sedi.loadNomiFromDB();
        enti.loadNomiFromDB();
        sedeSelection.getItems().addAll(sedi.getNomi());
        entiSelection.getItems().addAll(enti.getNomiEnti());
    }
}

