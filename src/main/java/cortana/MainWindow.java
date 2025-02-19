package cortana;

import io.Ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Cortana cortana;

    private final Image cortanaImage = new Image(this.getClass()
            .getResourceAsStream("/images/Cortana.png"));
    private final Image userImage = new Image(this.getClass()
            .getResourceAsStream("/images/MasterChief.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Show intro message when UI initializes
        dialogContainer.getChildren().add(
                DialogBox.getCortanaDialog(Ui.showIntro(), cortanaImage)
        );
    }

    @FXML
    private void handleDelayedExit() {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();
    }

    public void setCortana(Cortana c) {
        cortana = c;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's
     * reply then appending it to the dialog container. Clears the user input after processing
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String output = cortana.getOutput(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getCortanaDialog(output, cortanaImage)
        );
        userInput.clear();

        if (output.equals(Ui.showEnd())) {
            handleDelayedExit();
        }
    }
}

