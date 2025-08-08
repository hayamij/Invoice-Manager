package presentation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import business.ShowInvoiceTypeStatsUseCase;
import business.MonthlyAverageInvoiceUseCase;
import business.MonthlyAverageInvoiceUseCase.MonthlyAverage;
import java.util.List;

public class StatsController {
    @FXML private Label hourlyLabel;
    @FXML private Label dailyLabel;
    @FXML private TableView<MonthlyAverage> averageTable;
    @FXML private TableColumn<MonthlyAverage, Integer> monthColumn;
    @FXML private TableColumn<MonthlyAverage, Integer> yearColumn;
    @FXML private TableColumn<MonthlyAverage, Double> averageColumn;

    private ShowInvoiceTypeStatsUseCase showInvoiceTypeStatsUseCase;
    private MonthlyAverageInvoiceUseCase monthlyAverageInvoiceUseCase;

    public void setUseCases(ShowInvoiceTypeStatsUseCase statsUC, MonthlyAverageInvoiceUseCase avgUC) {
        this.showInvoiceTypeStatsUseCase = statsUC;
        this.monthlyAverageInvoiceUseCase = avgUC;
        loadStats();
    }

    public void initialize() {
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));
    }

    private void loadStats() {
        if (showInvoiceTypeStatsUseCase != null && monthlyAverageInvoiceUseCase != null) {
            ShowInvoiceTypeStatsUseCase.StatsResult stats = showInvoiceTypeStatsUseCase.getStats();
            hourlyLabel.setText("Số lượng thuê theo giờ: " + stats.hourlyCount);
            dailyLabel.setText("Số lượng thuê theo ngày: " + stats.dailyCount);
            List<MonthlyAverage> averages = monthlyAverageInvoiceUseCase.getAllMonthlyAverages();
            averageTable.getItems().setAll(averages);
        }
    }
}
