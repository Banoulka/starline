package Base.Scenes;

import Abstracts.ExtendableScene;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class LoadScene extends ExtendableScene {
    public LoadScene(Pane parent) {
        super(parent);

        // Loading label
        Label loadingLabel = new Label();
        loadingLabel.setText("Loading....");
        loadingLabel.getStyleClass().add("title");

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(4000), loadingLabel);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(2);
        scaleTransition.setToY(2);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(2000), loadingLabel);
        rotateTransition.setFromAngle(-40 + 40);
        rotateTransition.setToAngle(5 + 10);
        rotateTransition.setCycleCount(2);
        rotateTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(scaleTransition, rotateTransition);
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.setAutoReverse(true);
        parallelTransition.play();

        HBox hBox = new HBox();
        hBox.getChildren().add(loadingLabel);
        hBox.setAlignment(Pos.CENTER);

        ((BorderPane) parent).setCenter(hBox);
    }
}
