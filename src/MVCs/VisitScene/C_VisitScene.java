package MVCs.VisitScene;

import Abstracts.CelestialBody;
import Abstracts.Controller;
import Abstracts.ExtendableScene;
import Base.Coord;
import Base.Interfaces.IRunAfter;
import Base.Misc.PlayerGO;
import Base.SceneManager;
import Base.Scenes.PlayScene;
import MVCs.PlayerData.M_PlayerData;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class C_VisitScene extends Controller implements IRunAfter {

    protected V_VisitScene view;
    protected M_VisitScene model;

    private M_PlayerData playerData = M_PlayerData.getInstance();
    private PlayerGO player = playerData.getPlayerGO();

    private final Coord moveBy = new Coord(0, 0);

    public C_VisitScene(Pane root, CelestialBody visiting) {
        model = new M_VisitScene(visiting);
        view = new V_VisitScene(root, model, this);

        // TODO: remove back button or move it

        // Get the distance from the player to here
        // Abs to get positive value
        // Floor to remove decimals
        // Divide by fuel level
        double distance = Math.floor(
                Math.abs((player.getPosition().x - visiting.getPosition().x)) / 40 / playerData.getFuelLevel()) +
                Math.floor(Math.abs((player.getPosition().x - visiting.getPosition().x) / 150));

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
    }

    private double lerp(double a, double b, double f)
    {
        return a + f * (b - a);
    }

    private void setupAnimationTimer() {
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
