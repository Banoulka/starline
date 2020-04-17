package Base.Scenes;

import Abstracts.ExtendableScene;
import MVCs.StartScene.C_StartScene;
import javafx.scene.layout.BorderPane;

public class StartScene extends ExtendableScene {

    public StartScene() {
        super(new BorderPane());

        // Create the new controller for the page.
        C_StartScene startScene = new C_StartScene(parent);
    }
}
