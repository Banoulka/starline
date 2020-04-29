package MVCs.VisitScene;

import Abstracts.CelestialBody;
import Abstracts.Controller;
import Abstracts.ExtendableScene;
import Base.Controls.PlayerController;
import Base.Utility.Config;
import Base.Interfaces.IRunAfter;
import Base.SceneManager;
import Base.Scenes.PlayScene;
import MVCs.PlayerData.M_PlayerData;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class C_VisitScene extends Controller implements IRunAfter {

    protected V_VisitScene view;
    protected M_VisitScene model;

    private double distance;

    // Timelines
    private Timeline countdownTimer;
    private Timeline uiTimeline;

    public C_VisitScene(Pane root, CelestialBody visitingBody) {
        model = new M_VisitScene(visitingBody);
        view = new V_VisitScene(root, model, this);

        // TODO: remove back button or move it

        // Get the distance from the player to here
        // Abs to get positive value
        // Floor to remove decimals
        // Divide by fuel level
        M_PlayerData playerData = M_PlayerData.getInstance();

        CelestialBody currentPlanet = M_PlayerData.getInstance().getCurrPlanet();
        // if current planet is null
        // make up a value
        if (currentPlanet != null)
            distance = Math.floor( Math.abs((currentPlanet.getPosition().x - visitingBody.getPosition().x)) / 40 / playerData.getFuelLevel() );
        else if (currentPlanet == null && Config.DEBUG)
            distance = 30;
        else
            System.err.println("Debug is not on and current planet is not set");

        if (distance < 10)
            distance = 10;

        System.out.println("Distance: " + distance + " seconds");
    }


    public void goBackToPlay(CelestialBody celestialBody) {

        // Add the known body to the list
        M_PlayerData.getInstance().addKnownBody(celestialBody);

        // Set the currently visited planet to this one
        M_PlayerData.getInstance().setCurrPlanet(celestialBody);

        SceneManager.setCurrScene(new PlayScene());
    }

    private void setupPlayerControls() {

        // Get the scene instance
        ExtendableScene currScene = SceneManager.getSceneParent();

        // Controls for the player
        // Delegated to player controller
        currScene.setOnKeyPressed(PlayerController.get()::onKeyPressed);
        currScene.setOnKeyReleased(PlayerController.get()::onKeyReleased);

        // All mouse movement events (for turret rotation)
        view.getPane().setOnMouseMoved(view::changeMouseEvent);
        view.getPane().setOnMouseDragged(view::changeMouseEvent);

        // Setup player shooting
        view.getPane().setOnMousePressed(view::createBullet);
    }

    private void setupAnimationTimer() {

        // Animation timer for player movement
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                view.movePlayer();
                view.updateView();
            }
        }.start();

        // Timeline for UI stuff
        uiTimeline = new Timeline();

        KeyFrame keyStart = new KeyFrame(
                Duration.seconds(0),
                e -> view.updateUI((1160 / distance) * 0.005)
        );
        KeyFrame keyEnd = new KeyFrame(Duration.millis(5));

        uiTimeline.getKeyFrames().addAll(keyStart, keyEnd);
        uiTimeline.setCycleCount(Timeline.INDEFINITE);
        uiTimeline.setAutoReverse(false);

        countdownTimer = new Timeline(
                new KeyFrame(
                        Duration.seconds(distance - (1160 / distance) * 0.005),
                        e -> journeyComplete()
                )
        );

        // Timeline for asteroids
        Timeline asteroidTimer = new Timeline(
                new KeyFrame(Duration.millis(2000), e -> model.AsteroidPool.spawn())
        );
        asteroidTimer.setCycleCount(Timeline.INDEFINITE);
        asteroidTimer.play();
    }

    public void startTimers() {
        uiTimeline.play();
        countdownTimer.play();
    }

    private void journeyComplete() {
        uiTimeline.stop();
        countdownTimer.stop();
        System.out.println("Journey complete");
        goBackToPlay(model.getCurrentlyVisiting());
    }

    @Override
    public M_VisitScene model() {
        return model;
    }

    @Override
    public V_VisitScene view() {
        return view;
    }

    @Override
    public void runAfter() {
        view.runAfter();

        setupAnimationTimer();
        setupPlayerControls();
    }
}
