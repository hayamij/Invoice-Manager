package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import persistence.InvoiceDAO;
import persistence.InvoiceDAOGateway;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/presentation/primary.fxml"));
        Parent root = fxmlLoader.load();
        scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
        // Khởi tạo gateway và truyền vào controller
        PrimaryController controller = fxmlLoader.getController();
        InvoiceDAOGateway gateway = new InvoiceDAO();
        controller.initUseCases(gateway);
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