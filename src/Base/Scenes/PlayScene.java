package Base.Scenes;

import Abstracts.ExtendableScene;
import Base.Interfaces.IRunAfter;
import MVCs.PlayScene.C_PlayScene;
import MVCs.PlayerData.C_PlayerData;
import javafx.scene.layout.BorderPane;

public class PlayScene extends ExtendableScene implements IRunAfter {

    private C_PlayerData playerData;
    private static C_PlayScene playScene;

    public PlayScene() {
        super(new BorderPane());

        // TODO: Create the sidebar

        // Create the main play scene
        playScene = new C_PlayScene(parent, this);

        // Create the player data controller
        playerData = new C_PlayerData(parent);
    }

    @Override
    public void runAfter() {
        // Setup things after the window is created
        playScene.runAfter();
    }
}
