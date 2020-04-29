package MVCs.StartScene;

import Abstracts.View;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Random;

public class V_StartScene extends View {

    private Button startButton;
    private TextField nameInput;

    private Label errorLabel, title;

    public V_StartScene(Pane root) {
        super(root);

        // Start button and other UI elements
        startButton = new Button("Start");
        startButton.getStyleClass().add("primary");

        nameInput = new TextField();
        nameInput.setPromptText("Enter name...");
        nameInput.setMaxWidth(200);
        nameInput.setFocusTraversable(false);
        nameInput.getStyleClass().add("name-input");

        errorLabel = new Label();
        errorLabel.setText("Name cannot be empty!");
        errorLabel.getStyleClass().add("error");
        errorLabel.setVisible(false);

        title = new Label("Starline!");
        title.getStyleClass().add("title");


        VBox vBoxTitle = new VBox();
        vBoxTitle.setAlignment(Pos.TOP_CENTER);
        vBoxTitle.setPadding(new Insets(70, 0, 70, 0));
        vBoxTitle.getChildren().add(title);

        // Box for layout, set alignment and add elements
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.setSpacing(5);
        vBox.getChildren().addAll(nameInput, errorLabel, startButton);

        // Add the vertical box to the center of the root
        ((BorderPane) root).setCenter(vBox);
        ((BorderPane) root).setTop(vBoxTitle);

        // Start the animations
        setupAnimations();
    }

    private void setupAnimations() {

        Random r = new Random(System.currentTimeMillis());

        // New parallel transition to run for the title
        // rotating and scale transitions

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(2000), title);
        rotateTransition.setFromAngle(-40 + (0 - -40) * r.nextDouble());
        rotateTransition.setToAngle(5 + (30 - 20) * r.nextDouble());
        rotateTransition.setCycleCount(2);
        rotateTransition.setAutoReverse(true);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(4000), title);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(2);
        scaleTransition.setToY(2);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(rotateTransition, scaleTransition);
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.setAutoReverse(true);

        parallelTransition.play();
    }

    public void toggleError(Boolean toggle) {
        errorLabel.setVisible(toggle);
    }

    @Override
    public void updateView() {}

    public Button getStartButton() {
        return startButton;
    }

    public TextField getNameInput() {
        return nameInput;
    }

}
