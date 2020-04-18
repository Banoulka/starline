package Base;

import Abstracts.ExtendableScene;
import Base.Interfaces.IRunAfter;
import Base.Scenes.LoadScene;
import javafx.application.Platform;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage stage;

    public static void setStage(Stage stage) {
        SceneManager.stage = stage;
    }

    public static void setCurrScene(ExtendableScene scene){

        // Set the loading screen first in between scenes
        loadScene(LoadScene.get());

        // Show the loading screen first and then start loading the other scenes
        // once loaded the scene will change again into the requested scene
        Platform.runLater(() -> loadScene(scene));
    }

    private static void loadScene(ExtendableScene scene) {
        stage.setScene(null);
        // Stage the scene and show it
        stage.setScene(scene);
        stage.setResizable(Config.IS_RESIZABLE);
        stage.show();

        // If the scene implements the run after interface then the method
        if (scene instanceof IRunAfter) {
            ((IRunAfter) scene).runAfter();
        }
    }
}
