package Controller;

import Model.Entities.Conferenze.Conferenza;
import Model.Entities.Conferenze.Sessione;
import Model.Entities.organizzazione.Comitato;
import Model.Entities.organizzazione.Ente;
import Model.Entities.organizzazione.Organizzatore;
import Model.Entities.organizzazione.Sponsorizzazione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class VisualizzaConferenzaController implements Initializable {
    @FXML
    private Label budgetLabel;
    @FXML
    private TableColumn<Organizzatore, String> cognomeOrganizzatoreColumn1;
    @FXML
    private TableColumn<Organizzatore, String> cognomeOrganizzatoreColumn2;
    @FXML
    private TableColumn<Organizzatore, String> comeOrganizzatoreColumn2;
    @FXML
    private TableView<Organizzatore> comitatoLocaleTable;
    @FXML
    private TableView<Organizzatore> comitatoScientificoTable;
    private Conferenza conferenza;
    @FXML
    private Button confermaButton;
    @FXML
    private TableColumn<Sponsorizzazione, Float> contributoColumn;
    @FXML
    private Label dataFineLabel;
    @FXML
    private Label dataInizioLabel;
    @FXML
    private TextArea descrizioneArea;
    @FXML
    private Button editSessioniButton;
    @FXML
    private TableColumn<Organizzatore, String> emailOrganizzatoreColumn1;
    @FXML
    private TableColumn<Organizzatore, String> emailOrganizzatoreColumn2;
    @FXML
    private TableView<Ente> entiTable;
    @FXML
    private TextArea entiView;
    @FXML
    private TableColumn<Sessione, Timestamp> fineSessioneColumn;
    @FXML
    private Label indirizzoLabel;
    @FXML
    private TableColumn<Sessione, Timestamp> inizioSessioneColumn;
    @FXML
    private TableColumn<Organizzatore, Ente> istituzioneOrganizzatoreColumn1;
    @FXML
    private TableColumn<Organizzatore, Ente> istituzioneOrganizzatoreColumn2;
    @FXML
    private TableColumn<Ente, String> nomeEnte;
    @FXML
    private Label nomeLabel;
    @FXML
    private TableColumn<Organizzatore, String> nomeOrganizzatoreColumn1;
    @FXML
    private TableColumn<Organizzatore, String> nomeOrganizzatoreColumn2;
    @FXML
    private TableColumn<Sessione, String> nomeSessioneColumn;
    @FXML
    private TableColumn<Sessione, String> salaSessioneColumn;
    @FXML
    private Label sedeLabel;
    @FXML
    private TableView<Sessione> sessioniTable;
    @FXML
    private TableColumn<Ente, String> siglaEnte;
    @FXML
    private TableColumn<Sponsorizzazione, String> sponsorColumn;
    @FXML
    private TableView<Sponsorizzazione> sponsorTable;
    @FXML
    private TextArea sponsorizzazioniView;
    private SubScene subScene;
    @FXML
    private Label titleLabel;
    @FXML
    private TableColumn<Organizzatore, String> titoloOrganizzatoreColumn1;
    @FXML
    private TableColumn<Organizzatore, String> titoloOrganizzatoreColumn2;
    @FXML
    private TableColumn<Sponsorizzazione, String> valutaColumn;
    private final VisualizzaConferenzeController visualizzaConferenzeController;

    public VisualizzaConferenzaController(Conferenza conferenza, SubScene subScene, VisualizzaConferenzeController controller) {
        this.conferenza = conferenza;
        this.subScene = subScene;
        this.visualizzaConferenzeController = controller;
    }

    public Conferenza getConferenza() {
        return conferenza;
    }

    public void setConferenza(Conferenza conferenza) {
        this.conferenza = conferenza;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDetails();
        setOrganizzatori();
        setSponsorizzazioni();
        setSessioniTable();
        setComitatoScientiticoTable();
        setComitatoLocaleTable();
    }

    private void setComitatoLocaleTable() {
        Comitato comitato_L = conferenza.getComitato_l();
        try {
            comitato_L.loadMembri();
            nomeOrganizzatoreColumn1.setCellValueFactory(new PropertyValueFactory<>("nome"));
            cognomeOrganizzatoreColumn1.setCellValueFactory(new PropertyValueFactory<>("cognome"));
            titoloOrganizzatoreColumn1.setCellValueFactory(new PropertyValueFactory<>("titolo"));
            emailOrganizzatoreColumn1.setCellValueFactory(new PropertyValueFactory<>("email"));
            istituzioneOrganizzatoreColumn1.setCellValueFactory(new PropertyValueFactory<>("istituzione"));
            comitatoLocaleTable.setItems(comitato_L.getMembri());
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void setComitatoScientiticoTable() {
        Comitato comitato_S = conferenza.getComitato_s();
        try {
            comitato_S.loadMembri();
            nomeOrganizzatoreColumn2.setCellValueFactory(new PropertyValueFactory<>("nome"));
            cognomeOrganizzatoreColumn2.setCellValueFactory(new PropertyValueFactory<>("cognome"));
            titoloOrganizzatoreColumn2.setCellValueFactory(new PropertyValueFactory<>("titolo"));
            emailOrganizzatoreColumn2.setCellValueFactory(new PropertyValueFactory<>("email"));
            istituzioneOrganizzatoreColumn2.setCellValueFactory(new PropertyValueFactory<>("istituzione"));
            comitatoScientificoTable.setItems(comitato_S.getMembri());
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void setDetails() {
        this.setTitleLabel();
        nomeLabel.setText(conferenza.getTitolo());
        descrizioneArea.setText(conferenza.getDescrizione());
        dataInizioLabel.setText(conferenza.getInizio().toString());
        dataFineLabel.setText(conferenza.getFine().toString());
        sedeLabel.setText(conferenza.getSede().toString());
        indirizzoLabel.setText(conferenza.getSede().getIndirizzo().toString());
    }

    public void setOrganizzatori() {
        try {
            conferenza.loadOrganizzatori();
            entiTable.setEditable(false);
            nomeEnte.setCellValueFactory(new PropertyValueFactory<>("nome"));
            siglaEnte.setCellValueFactory(new PropertyValueFactory<>("sigla"));
            entiTable.getItems().addAll(conferenza.getEnti());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTitleLabel() {
        if (conferenza != null)
            titleLabel.setText("Conferenza: " + conferenza.getTitolo());
    }

    private void setSessioniTable() {
        sessioniTable.setEditable(false);
        try {
            conferenza.loadSessioni();
            nomeSessioneColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
            inizioSessioneColumn.setCellValueFactory(new PropertyValueFactory<>("inizio"));
            fineSessioneColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
            salaSessioneColumn.setCellValueFactory(new PropertyValueFactory<Sessione, String>("locazione"));
            sessioniTable.getItems().addAll(conferenza.getSessioni());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setSponsorizzazioni() {
        try {
            conferenza.loadSponsorizzazioni();
            sponsorColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, String>("sponsor"));
            contributoColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, Float>("contributo"));
            valutaColumn.setCellValueFactory(new PropertyValueFactory<Sponsorizzazione, String>("valuta"));
            sponsorTable.setItems(conferenza.getSponsorizzazioni());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void viewSessioniButtonOnAction(ActionEvent event) throws IOException {
        Sessione s = sessioniTable.getSelectionModel().getSelectedItem();
        if (s.equals(null)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selezionare una sessione");
            alert.setContentText("Nessuna sessione selezionata");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/ViewSessione.fxml"));
            ViewSessioneController controller = new ViewSessioneController(s,this,subScene);
            controller.setSessione(s);
            controller.setVisualizzaConferenzaController(this);
            controller.setSubScene(subScene);
            loader.setController(controller);
            Parent root = loader.load();
            subScene.setRoot(root);
        }
    }

    @FXML
    void confermaButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/VisualizzaConferenze.fxml"));
        loader.setController(visualizzaConferenzeController);
        Parent root = loader.load();
        subScene.setRoot(root);
    }

    @FXML
    void viewSessione(MouseEvent event) {
        try {
            Sessione s = sessioniTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/VisualizzaSessione.fxml"));
            VisualizzaSessioneController controller = new VisualizzaSessioneController(s, subScene, this);
            loader.setController(controller);
            Parent root = loader.load();
            subScene.setRoot(root);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nessuna sessione selezionata");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}