package Base.Scenes;

import Abstracts.ExtendableScene;
import Base.Interfaces.IRunAfter;
import MVCs.PlayScene.C_PlayScene;
import MVCs.PlayerData.C_PlayerData;
import javafx.scene.layout.Pane;

public class PlayScene extends ExtendableScene implements IRunAfter {

    private C_PlayerData playerData;
    private C_PlayScene playScene;

    private Pane parent;

    public PlayScene(Pane parent) {
        super(parent);
        this.parent = parent;

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
