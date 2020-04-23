package Base.GameObjects.Planets;

import Abstracts.CelestialBody;
import Base.Config;
import Base.Coord;
import Base.Interfaces.Actions.IPannable;
import Base.Interfaces.Actions.IVisitable;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Planet extends CelestialBody
        implements IPannable, IVisitable {

    private List<Moon> moons;

    private Circle path;

    public Planet() {
        this.moons = new ArrayList<>();
    }

    public void startAnimation() {

        double startAngle = Math.random() * 360;

        // Setup basic orbit animation
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(this.getGoHeight() / 1.5f), this);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setToAngle(startAngle + 360);
        rotateTransition.setFromAngle(startAngle);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
    }

    public void addMoon(Moon moon) {
        moon.setParentPlanet(this);
        moon.setPosition(new Coord(0, 0));
        moons.add(moon);
    }

    public Circle getOrbitPath() {
        return path;
    }

    public void buildOrbitPath() {
        // Create the new path for the moon
        path = new Circle();
        path.setCenterX(getCenter().x);
        path.setCenterY(getCenter().y);
        path.setLayoutY(-position.y);
        path.setLayoutX(-position.x);
        path.setFill(null);

        if (Config.DEBUG)
            path.setStroke(Color.GREEN);
        else
            path.setStroke(null);


        path.setRadius(getGoHeight() / 1.05);

        getChildren().add(path);
    }

    public List<Moon> getMoons() {
        return moons;
    }

    @Override
    public String toString() {
        return "Planet{" + "moons=" + moons +
                ", name='" + name + '\'' +
                ", gravityPull=" + gravityPull +
                ", hasAtmosphere=" + hasAtmosphere +
                ", position=" + position +
                ", goHeight=" + goHeight +
                ", goWidth=" + goWidth +
                '}';
    }
}
