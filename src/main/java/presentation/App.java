package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.InvoiceDAO;

import java.io.IOException;

import business.AddInvoice.InvoiceTypeListUseCase;
import business.ShowInvoiceList.ShowInvoiceListUseCase;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/presentation/InvoiceListUI.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Invoice Manager");

        // Lấy controller thực tế từ FXMLLoader
        InvoiceController invoiceController = fxmlLoader.getController();
        InvoiceDAO DAO = new InvoiceDAO();
        InvoiceViewModel model = new InvoiceViewModel();
        ShowInvoiceListUseCase showInvoiceListUseCase = new ShowInvoiceListUseCase(DAO);
        InvoiceTypeListUseCase invoiceTypeListUseCase = new InvoiceTypeListUseCase(DAO);

        // Truyền model và usecase vào controller thực tế
        invoiceController.setViewModel(model);
        invoiceController.setShowInvoiceListUseCase(showInvoiceListUseCase);
        invoiceController.setInvoiceTypeListUseCase(invoiceTypeListUseCase);
        invoiceController.execute();
        invoiceController.displayInvoices();
        invoiceController.loadInvoiceTypes();
        
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/presentation/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}