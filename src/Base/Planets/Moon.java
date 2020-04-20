package Base.Planets;

import Abstracts.CelestialBody;
import Base.Config;
import Base.Interfaces.Actions.IPannable;
import Base.Interfaces.Actions.IVisitable;
import javafx.animation.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class Moon extends CelestialBody
        implements IPannable, IVisitable {

    private Planet parentPlanet;

    private boolean isOrbiting = false;

    public Planet getParentPlanet() {
        return parentPlanet;
    }

    public void setParentPlanet(Planet parentPlanet) {
        this.parentPlanet = parentPlanet;
    }

    public void orbitPlanet() {

        if (!isOrbiting) {
            position.x = parentPlanet.getPosition().x;
            position.y = parentPlanet.getPosition().y;

            this.setLayoutX(position.x);
            this.setLayoutY(position.y);

            if (Config.DEBUG) {
                // Config debug rectangle for things
                Rectangle rectangle = new Rectangle(goWidth, goHeight);

                rectangle.setFill(null);
                rectangle.setStroke(Color.RED);

                this.getChildren().add(rectangle);
            }

            isOrbiting = true;
        }
    }

    @Override
    public void update() {
        orbitPlanet();
        this.setPrefSize(goWidth, goHeight);

        this.position.x = getTranslateX() + getLayoutX();
        this.position.y = getTranslateY() + getLayoutY();

        if (getImg() != null) {
            getImg().setFitHeight(goHeight);
            getImg().setFitWidth(goWidth);
            getImg().setRotate(this.getRotate());
        }
    }

    @Override
    public void startAnimation() {
        // Setup orbit animation
        PathTransition path = new PathTransition(Duration.seconds(40), parentPlanet.getOrbitPath(), this);
        path.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        path.setDelay(Duration.ZERO);
        path.setInterpolator(Interpolator.LINEAR);

        // Let the animation run forever
        path.setCycleCount(FadeTransition.INDEFINITE);
        path.setAutoReverse(false);

        // Setup rotate animation
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(25), this);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setAutoReverse(false);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(path, rotateTransition);
        parallelTransition.play();
    }

    @Override
    public String toString() {
        return "Moon{" +
                ", name='" + name + '\'' +
                ", gravityPull=" + gravityPull +
                ", hasAtmosphere=" + hasAtmosphere +
                ", position=" + position +
                ", goHeight=" + goHeight +
                ", goWidth=" + goWidth +
                '}';
    }
}
