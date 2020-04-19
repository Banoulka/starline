package Base.Scenes;

import MVCs.StartScene.C_StartScene;
import javafx.scene.layout.BorderPane;

public class StartScene extends BorderPane {

    private static StartScene instance = new StartScene();

    private StartScene() {
        super();

        // Create the new controller for the page.
        C_StartScene startScene = new C_StartScene(this);
    }

    public static StartScene get() {
        return instance;
    }
}
