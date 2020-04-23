package MVCs.VisitScene;

import Abstracts.CelestialBody;
import Abstracts.Controller;
import Abstracts.ExtendableScene;
import Base.Coord;
import Base.Interfaces.IRunAfter;
import Base.GameObjects.PlayerGO;
import Base.SceneManager;
import Base.Scenes.PlayScene;
import MVCs.PlayerData.M_PlayerData;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class C_VisitScene extends Controller implements IRunAfter {

    protected V_VisitScene view;
    protected M_VisitScene model;

    private M_PlayerData playerData = M_PlayerData.getInstance();
    private PlayerGO player = playerData.getPlayerGO();

    private final Coord moveBy = new Coord(0, 0);

    private double distance;

    // Timelines
    Timeline countdownTimer;
    Timeline uiTimeline;

    public C_VisitScene(Pane root, CelestialBody visiting) {
        model = new M_VisitScene(visiting);
        view = new V_VisitScene(root, model, this);

        // TODO: remove back button or move it

        // Get the distance from the player to here
        // Abs to get positive value
        // Floor to remove decimals
        // Divide by fuel level
        distance = Math.floor( Math.abs((player.getPosition().x - visiting.getPosition().x)) / 40 / playerData.getFuelLevel() );

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
        currScene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.W)
                moveBy.y -= 1;
            else if (keyEvent.getCode() == KeyCode.S)
                moveBy.y += 1;

            if (keyEvent.getCode() == KeyCode.D)
                moveBy.x += 1;
            else if (keyEvent.getCode() == KeyCode.A)
                moveBy.x -= 1;
        });

        // Reset controls if the player releases keys
        currScene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.W)
                moveBy.y += 1;
            if (keyEvent.getCode() == KeyCode.S)
                moveBy.y -= 1;

            if (keyEvent.getCode() == KeyCode.D)
                moveBy.x -= 1;
            if (keyEvent.getCode() == KeyCode.A)
                moveBy.x += 1;
        });

        // All mouse events
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
                // Get the signum values for the move by
                moveBy.x = Math.signum(moveBy.x);
                moveBy.y = Math.signum(moveBy.y);

                view.movePlayer(moveBy);
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
    }

    public void startTimers() {
        uiTimeline.play();
        countdownTimer.play();
    }

    private void journeyComplete() {
        uiTimeline.stop();
        countdownTimer.stop();
        System.out.println("Journey complete");
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
