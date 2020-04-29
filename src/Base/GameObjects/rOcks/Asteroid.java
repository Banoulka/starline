package Base.GameObjects.rOcks;

import Abstracts.Projectile;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Asteroid extends Projectile {

    protected float graceTime = 4f;
    protected boolean hasGraceTime = true;

    public Asteroid() { }

    @Override
    public void startAnimation() {
        // TODO: add spinning
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(this.speed * 5f), this.getImg());
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.setAutoReverse(false);
        rotateTransition.setInterpolator(Interpolator.TANGENT(Duration.seconds(this.speed * 5f), this.speed));

        rotateTransition.play();
    }

    @Override
    public void update() {

        // Take a little off the grace time
        if (hasGraceTime)
            graceTime -= 0.01f;

        // If the grace time is below zero then set the boolean
        // This will tell the controller we are ready to be deleted
        // on screen exit
        if (graceTime <= 0)
            hasGraceTime = false;

        super.update();
    }

    public float getGraceTime() {
        return graceTime;
    }

    public boolean hasGraceTime() {
        return hasGraceTime;
    }
}
