package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ExceptionWindow_Controller implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button closeButton;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Pane hBox;
    private double x, y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorMessageLabel.setText("");
    }

    public void setErrorMessageLabel(String message) {
        errorMessageLabel.setText(message);
    }

    @FXML
    void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
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
