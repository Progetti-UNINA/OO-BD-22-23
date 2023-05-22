package View.Controller;

import Exceptions.SessioneNotSelectedException;
import Persistence.Entities.Conferenze.Conferenza;
import Persistence.Entities.Conferenze.Sessione;
import Persistence.Entities.Utente;
import Services.Sessioni;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageSessioniController implements Initializable {
    private Conferenza conferenza;
    private Sessioni sessioni ;
    private SubScene subscene;
    private Utente user;
    private ModificaConferenzaController modificaConferenzaController;
    @FXML
    private ListView<Sessione> sessioniView;
    @FXML
    private Button addSessioneButton;

    @FXML
    private Button confermaButton;

    @FXML
    private Button editSessionsButton;

    @FXML
    private Button rimuoviSessioneButton;

    @FXML
    public void addSessioneOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddSessione.fxml"));
        AddSessioneController controller = new AddSessioneController();
        loader.setController(controller);
        controller.setConferenza(conferenza);
        controller.setManageSessioniController(this);
        controller.setSubscene(subscene);
        controller.setSessioni(sessioni);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    public void editSessionsOnAction(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/EditSessione.fxml"));
            EditSessioneController controller = new EditSessioneController();
            loader.setController(controller);
            Sessione s = sessioniView.getSelectionModel().getSelectedItem();
            if(s == null)
                throw new SessioneNotSelectedException();
            controller.setSessione(s);
            controller.setConferenza(conferenza);
            controller.setManageSessioniController(this);
            controller.setSubScene(subscene);
            Parent root = loader.load();
            subscene.setRoot(root);
        }catch(SessioneNotSelectedException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sessioniView.setItems(sessioni.getSessioni());
    }

    public void setEditConferenceController(ModificaConferenzaController modificaConferenzaController) {
        this.modificaConferenzaController = modificaConferenzaController;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }
    @FXML
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ModificaConferenza.fxml"));
        ModificaConferenzaController controller = new ModificaConferenzaController();
        controller.setSubscene(subscene);
        controller.setConferenza(conferenza);
        loader.setController(controller);
        Parent root = loader.load();
        subscene.setRoot(root);
    }
    @FXML
    void rimuoviSessioneOnAction(ActionEvent event) {
        try {
            Sessione s = sessioniView.getSelectionModel().getSelectedItem();
            if (s == null) {
                throw new SessioneNotSelectedException();
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Sicuro di voler rimuovere la sessione '" + s.getTitolo() + "'?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    sessioni.removeSessione(s);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }catch (SessioneNotSelectedException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void setSessioni(Sessioni sessioni) {
        this.sessioni = sessioni;
    }
}