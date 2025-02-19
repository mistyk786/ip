package cortana;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

    private static final String FILE_PATH = "data/tasks.txt";
    private final Cortana cortana = new Cortana(FILE_PATH);

    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setCortana(cortana);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}