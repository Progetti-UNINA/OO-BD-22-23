package Controller.Create;

import Controller.ExceptionWindow_Controller;
import Exceptions.BlankFieldException;
import Interfaces.FormChecker;
import Model.Entities.Organizzazione.Ente;
import Model.Entities.Partecipanti.Speaker;
import Model.Utilities.Enti;
import Model.Utilities.Speakers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddSpeaker_Controller implements Initializable, FormChecker {

    @FXML
    private Button cancelButton;
    @FXML
    private TextField cognomeTextField;
    @FXML
    private TextField emailtextField;
    @FXML
    private ChoiceBox<Ente> enteChoiceBox;
    @FXML
    private TextField nomeTextField;
    private Speakers speakers = new Speakers();
    @FXML
    private ChoiceBox<String> titoloChoiceBox;
    double x, y;

    @Override
    public void checkFieldsAreBlank() throws BlankFieldException {
        if (nomeTextField.getText().isBlank()
                || cognomeTextField.getText().isBlank()
                || emailtextField.getText().isBlank()
                || enteChoiceBox.getValue() == null)
            throw new BlankFieldException();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadChoiceBoxes();
    }

    private Speaker getSpeaker() {
        Speaker speaker = new Speaker();
        speaker.setNome(nomeTextField.getText());
        speaker.setCognome(cognomeTextField.getText());
        speaker.setIstituzione(enteChoiceBox.getSelectionModel().getSelectedItem());
        speaker.setTitolo(titoloChoiceBox.getSelectionModel().getSelectedItem());
        speaker.setEmail(emailtextField.getText());
        return speaker;
    }

    private void loadChoiceBoxes() {
        Enti enti = new Enti();
        String[] titoli = {"Dottore", "Dottoressa", "Professore", "Professoressa", "Assistente", "Ricercatore", "Ricercatrice", "Ingegnere"};
        enti.loadEnti();
        enteChoiceBox.setItems(enti.getEnti());
        titoloChoiceBox.getItems().setAll(titoli);
    }

    private void loadExceptionWindow(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXML/ExceptionWindow.fxml"));
            Parent root = loader.load();
            ExceptionWindow_Controller controller = loader.getController();
            controller.setErrorMessageLabel(message);
            Stage stage = new Stage();
            stage.setTitle("Errore");
            Scene scene = new Scene(root, 400, 200);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmButtonOnAction(ActionEvent event) {

        Speaker speaker = getSpeaker();
        try {
            checkFieldsAreBlank();
            speakers.addSpeaker(speaker);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (BlankFieldException eb) {
            loadExceptionWindow(eb.getMessage());
        } catch (SQLException e) {
            loadExceptionWindow(e.getMessage());
        }
    }

    @FXML
    void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);

    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
}
