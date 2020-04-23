package Base.GameObjects.Planets;

import Abstracts.CelestialBody;
import Base.Interfaces.Actions.IPannable;
import Base.Interfaces.Actions.ITooltip;
import javafx.animation.*;
import javafx.util.Duration;

public class Star extends CelestialBody implements IPannable, ITooltip {

    @Override
    public void startAnimation() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(4), this);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(1.02);
        scaleTransition.setToY(1.02);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition.setCycleCount(Timeline.INDEFINITE);
        scaleTransition.play();

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(getGoWidth()), this);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.play();

        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(600),
                new KeyValue(getImg().opacityProperty(), getImg().opacityProperty().getValue(), Interpolator.LINEAR),
                new KeyValue(getImg().opacityProperty(), 0.95, Interpolator.LINEAR)
        );

        Timeline brightness = new Timeline();
        brightness.setCycleCount(Timeline.INDEFINITE);
        brightness.getKeyFrames().add(keyFrame);
        brightness.setAutoReverse(true);
        brightness.play();
    }
}
