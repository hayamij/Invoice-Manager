package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.Model.InvoiceViewModel;

import java.io.IOException;



/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/presentation/main.fxml"));
        Parent root = fxmlLoader.load();
        scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Invoice Manager");
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        presentation.View.MainView mainView = fxmlLoader.getController();
        mainView.setInvoiceViewModel(invoiceViewModel);    
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