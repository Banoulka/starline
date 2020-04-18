package Base.Scenes;

import Abstracts.ExtendableScene;
import MVCs.StartScene.C_StartScene;
import javafx.scene.layout.BorderPane;

public class StartScene extends ExtendableScene {

    private static StartScene instance = new StartScene();

    private StartScene() {
        super(new BorderPane());

        // Create the new controller for the page.
        C_StartScene startScene = new C_StartScene(parent);
    }

    public static StartScene get() {
        return instance;
    }
}
