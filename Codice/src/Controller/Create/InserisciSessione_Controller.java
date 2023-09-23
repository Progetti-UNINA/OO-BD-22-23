package Controller.Create;

import Exceptions.BlankFieldException;
import Exceptions.DateMismatchException;
import Exceptions.SediNonDisponibiliException;
import Exceptions.SessionePresenteException;
import Interfaces.FormChecker;
import Model.DAO.SessioneDao;
import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Conferenze.Sala;
import Model.Entities.Conferenze.Sessione;
import Model.Entities.Utente;
import Model.Entities.organizzazione.Organizzatore;
import Model.Utilities.MembriComitato;
import Model.Utilities.Sale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class InserisciSessione_Controller implements Initializable, FormChecker {
    @FXML
    private Button backButton;
    private Conferenza conferenza;
    @FXML
    private ChoiceBox<Organizzatore> coordinatoreChoiceBox;
    @FXML
    private DateTimePicker fineDateTimePicker;
    @FXML
    private ImageView fineSessioneAboutImage;
    @FXML
    private DateTimePicker inizioDateTimePicker;
    @FXML
    private ImageView inizioSessioneAboutImage;
    @FXML
    private Button inserisciButton;
    private Sale sale;
    @FXML
    private ChoiceBox<Sala> saleChoiceBox;
    private SubScene subscene;
    @FXML
    private TextField titoloSessioneTextField;
    private Utente user;

    //Overrides
    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (titoloSessioneTextField.getText().isBlank() || inizioDateTimePicker.getValue() == null ||
                fineDateTimePicker.getValue() == null || saleChoiceBox.getValue() == null)
            throw new BlankFieldException();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadCoordinatoreChoiceBox();
            Tooltip.install(inizioSessioneAboutImage, new Tooltip("L'inizio della conferenza è: " + conferenza.getInizio()));
            Tooltip.install(fineSessioneAboutImage, new Tooltip("La fine della conferenza è: " + conferenza.getFine()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void openAddSessioneDialogWindow() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Successo!");
        alert.setHeaderText("Sessione aggiunta correttamente");
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.showAndWait();
    }

    //Public Setters
    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public void setSubscene(SubScene subscene) {
        this.subscene = subscene;
    }

    public void setUtente(Utente utente) {
        this.user = utente;
    }

    private void loadCoordinatoreChoiceBox() throws SQLException {
        MembriComitato membriComitato = new MembriComitato(conferenza);

        try {
            membriComitato.loadMembriComitatoScientifico();
            coordinatoreChoiceBox.setItems(membriComitato.getMembriComitatoScientifico());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadViewSessioni(Conferenza c) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/Create/VisualizzaSessioniConferenza.fxml"));
            VisualizzaSessioniConferenza_Controller controller = new VisualizzaSessioniConferenza_Controller(subscene, c, user);
            loader.setController(controller);
            Parent root = loader.load();
            subscene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Sessione setSessione() {
        Sessione s = new Sessione();
        s.setConferenza(conferenza);
        s.setTitolo(titoloSessioneTextField.getText());
        s.setLocazione(saleChoiceBox.getValue());
        s.setCoordinatore(coordinatoreChoiceBox.getValue());
        s.setInizio(Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue()));
        s.setFine(Timestamp.valueOf(fineDateTimePicker.getDateTimeValue()));
        return s;
    }

    //Button methods
    @FXML
    void backButtonOnAction(ActionEvent event) {
        loadViewSessioni(conferenza);
    }

    @FXML
    void inserisciButtonOnAction(ActionEvent event) {
        try {
            checkFieldsAreBlank();
            Sessione s = setSessione();
            conferenza.addSessione(s);
            openAddSessioneDialogWindow();
            loadViewSessioni(conferenza);
        } catch (DateMismatchException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (BlankFieldException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        } catch (SessionePresenteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void showSale(MouseEvent event) {
        try {
            sale = new Sale(conferenza.getSede());
            Timestamp inizio = Timestamp.valueOf(inizioDateTimePicker.getDateTimeValue());
            Timestamp fine = Timestamp.valueOf(fineDateTimePicker.getDateTimeValue());
            sale.loadSaleDisponibili(inizio, fine);
            if (sale.getSale().isEmpty())
                throw new SediNonDisponibiliException();
            else
                saleChoiceBox.setItems(sale.getSale());
        } catch (SediNonDisponibiliException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inserire delle date per visualizzare le sale libere");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}