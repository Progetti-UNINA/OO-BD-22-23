package View.Controller;
import Persistence.DAO.InterventoDao;
import Utilities.Stats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MonthlyStatWindowController implements Initializable {
        @FXML
        private Spinner<Integer> annoSpinner;

        @FXML
        private Spinner<Integer> meseSpinner;

        @FXML
        private BorderPane pieChartPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSpinners();
    }

    private void setSpinners() {
        SpinnerValueFactory<Integer> mesiValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,12,1,1);
        meseSpinner.setValueFactory(mesiValueFactory);
        SpinnerValueFactory<Integer> anniValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2023,3000,1,1);
        annoSpinner.setValueFactory(anniValueFactory);
    }
    private static void showAlert(Alert.AlertType information, String s) {
        Alert alert = new Alert(information);
        alert.setContentText(s);
        alert.showAndWait();
    }
    private void createPieChart(ObservableList<Stats> stats, ObservableList<PieChart.Data> pieChartData, BorderPane pane, String title) {
        for (Stats dataPoint : stats) {
            PieChart.Data data = new PieChart.Data(dataPoint.getIstituzione(), dataPoint.getPercentuale());
            pieChartData.add(data);
        }
        PieChart pieChart = new PieChart(pieChartData);
        pane.setCenter(pieChart);
        pieChart.setMaxHeight(260);
        pieChart.setMaxWidth(260);
        pieChart.setLabelsVisible(true);
        pieChart.setLabelLineLength(10);
        for (PieChart.Data data : pieChart.getData()) {
            data.setName(data.getName() + " (" + Math.round(data.getPieValue()) + "%)");
        }
        pieChart.setTitle(title);
        pieChart.setClockwise(true);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
    }


    private LinkedList<Stats> retrieveIstituzioniByMonth(int m, int y) throws SQLException {
        InterventoDao dao = new InterventoDao();
        return dao.retrieveInterventiStatsByMonth(m, y);
    }
        @FXML
        void calcolaGrafico(ActionEvent event) {
            try {
                int mese = meseSpinner.getValue();
                int anno = annoSpinner.getValue();
                ObservableList<Stats> stats = FXCollections.observableArrayList();
                stats.addAll(retrieveIstituzioniByMonth(mese, anno));
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                if (stats.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "Non risultano interventi nel mese cercato");
                } else {
                    createPieChart(stats, pieChartData, pieChartPane, "Statistica mensile");
                }
            } catch (InputMismatchException e) {
                showAlert(Alert.AlertType.ERROR, "Inserire un input valido");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, e.getMessage());
            }
        }
}