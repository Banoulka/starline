package Base.Scenes;

import Abstracts.ExtendableScene;
import Base.Interfaces.IRunAfter;
import Base.SceneManager;
import MVCs.PlayScene.C_PlayScene;
import MVCs.PlayerData.C_PlayerData;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class PlayScene extends BorderPane implements IRunAfter {

    private C_PlayerData playerData;
    private static C_PlayScene playScene;

    public PlayScene() {
        super();

        // Create the main play scene
        playScene = new C_PlayScene(this);

        // Create the player data controller
        playerData = new C_PlayerData(this);
    }

    @Override
    public void runAfter() {
        // Setup things after the window is created
        playScene.runAfter();
    }
}
