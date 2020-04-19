package Base;

import Abstracts.ExtendableScene;
import Base.Interfaces.IRunAfter;
import Base.Scenes.LoadScene;
import Base.Scenes.PlayScene;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage stage;

    private static ExtendableScene sceneParent = new ExtendableScene();

    private static Pane currentScene;

    public static void setStage(Stage stage) {
        SceneManager.stage = stage;

        // Stage the sceneParent and show it
        stage.setScene(sceneParent);
        stage.setResizable(Config.IS_RESIZABLE);
        stage.show();
    }

    public static void setCurrScene(Pane newScene){

//        showLoadScene();
        loadScene(newScene);

        Platform.runLater(() -> {
            // Set the loading screen first in between scenes
            try {
                Thread.sleep(650);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Show the loading screen first and then start loading the other scenes
            // once loaded the sceneParent will change again into the requested sceneParent
            SceneManager.finishedLoading();
        });
    }

    private static void loadScene(Pane newScene) {
        newScene.setPrefSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        currentScene = newScene;
        sceneParent.setRoot(newScene);
    }

    public static void finishedLoading() {
        if (currentScene instanceof IRunAfter)
            ((IRunAfter) currentScene).runAfter();
    }
}
