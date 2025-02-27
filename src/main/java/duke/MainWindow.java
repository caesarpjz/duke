package duke;

import duke.exceptions.DukeException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import duke.execution.Ui;

import java.io.IOException;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/ester.jpg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/JoshuaSeet.jpg"));

    /**
     * Initializes the console that pops up when the application runs.
     */
    @FXML
    public void initialize() {
        Ui ui = new Ui();

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String style = "-fx-background-color: rgb(0, 81, 186);";
        this.dialogContainer.setStyle(style);

        //duke welcome message upon opening GUI
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(ui.greeting(), dukeImage)
        );
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws IOException, DukeException, InterruptedException {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
    }
}