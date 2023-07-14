package View.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Utente;
import Persistence.Entities.Conferenze.Sala;
import Persistence.Entities.organizzazione.Organizzatore;
import javafx.scene.control.cell.PropertyValueFactory;

public class VisualizzaSessioniConferenza implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Button inserisciButton;
    @FXML
    private Button riepilogoButton;
    @FXML
    private Button aggiungiProgrammaButton;
    @FXML
    private Button rimuoviButton;
    @FXML
    private TableView<Sessione> sessioniTableView;
    @FXML
    private TableColumn<Sessione,String> titoloTableColumn;
    @FXML
    private TableColumn<Sessione, Time > inizioTableColumn;
    @FXML
    private TableColumn<Sessione, Time> fineTableColumn;
    @FXML
    private TableColumn<Sessione, Sala> salaTableColumn;
    @FXML
    private TableColumn<Sessione, Organizzatore> chairTableColumn;
    @FXML
    private SubScene subscene;
    private Conferenza conferenza;
    private Utente user;
    //Public Setters
    public void setConferenza(Conferenza c){
        this.conferenza=c;
    }
    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }
    public void setUser(Utente utente){
        this.user=utente;
    }
    //Button Methods
    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        loadInserisciSessione();
    }
    @FXML
    void riepilogoButtonOnAction(ActionEvent event) {
        loadEditConferenza();
    }
    @FXML
    void aggiungiProgrammaButtonOnAction(ActionEvent event){
        try{
            Sessione selected =sessioniTableView.getSelectionModel().getSelectedItem();
            if (selected == null){
                throw new NullPointerException();
            }else{
                loadViewProgramma();
            }
        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Seleziona una sessione prima di procedere");
            alert.showAndWait();
        }
    }
    @FXML
    void backButtonOnAction(ActionEvent event){
        loadAggiungiSponsor();
    }
    @FXML
    void rimuoviButtonOnAction(ActionEvent event) {
        try{
            Sessione selected =sessioniTableView.getSelectionModel().getSelectedItem();
            if (selected == null){
                throw new NullPointerException();
            }
            Optional<ButtonType> result = showDeleteDialog();
            if(result.get() == ButtonType.OK) {
                try {
                    conferenza.removeSessione(selected);
                    setSessioni();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessuna sessione è stata selezionata");
            alert.showAndWait();
        }
    }
    private void loadInserisciSessione(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/InserisciSessione.fxml"));
            InserisciSessioneController controller = new InserisciSessioneController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUtente(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadAggiungiSponsor(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddSponsor.fxml"));
            AddSponsorController controller = new AddSponsorController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUtente(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadViewProgramma(){
        try{
            Sessione s = sessioniTableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ViewProgrammaSessione.fxml"));
            ViewProgrammaController controller = new ViewProgrammaController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUser(user);
            controller.setSessione(s);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadEditConferenza(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaConferenza.fxml"));
            ModificaConferenzaController controller = new ModificaConferenzaController();
            loader.setController(controller);
            controller.setSubscene(subscene);
            controller.setConferenza(conferenza);
            controller.setUser(user);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setSessioni() {
    try {
        conferenza.loadSessioni();
        titoloTableColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        inizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
        fineTableColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
        salaTableColumn.setCellValueFactory(new PropertyValueFactory<>("locazione"));
        chairTableColumn.setCellValueFactory(new PropertyValueFactory<>("coordinatore"));
        sessioniTableView.setItems(conferenza.getSessioni());
    }catch(SQLException e){
        e.printStackTrace();
    }
}
    private Optional<ButtonType> showDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Sicuro di voler eliminare la seguente sessione?");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSessioni();
    }

}
