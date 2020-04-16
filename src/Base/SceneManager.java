package Base;

import Abstracts.ExtendableScene;
import Base.Interfaces.IRunAfter;
import Base.Scenes.LoadScene;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneManager {

    private static ExtendableScene currScene;
    private static Stage stage;

    public static void setStage(Stage stage) {
        SceneManager.stage = stage;
    }

    public static Scene getCurrScene() {
        return currScene;
    }

    public static <T extends ExtendableScene> void setCurrScene(Class<T> scene){

        // Set the loading screen first in between scenes
        currScene = new LoadScene(new BorderPane());
        stage.setScene(currScene);
        stage.show();
        stage.setResizable(Config.IS_RESIZABLE);

        // Show the loading screen first and then start loading the other scenes
        // once loaded the scene will change again into the requested scene
        Platform.runLater(() -> {

            // Create a new instance of the scene from the class type passed
            // Class types should be ExtendableScenes to be properly instantiated
            // Get the constructor with argument type "Parent" - aka root
            try {
                currScene = scene.getDeclaredConstructor(Pane.class).newInstance(new BorderPane());
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            // Stage the scene and show it
            stage.setScene(currScene);
            stage.show();
            stage.setResizable(Config.IS_RESIZABLE);

            // If the scene implements the run after interface then the method
            if (currScene instanceof IRunAfter) {
                ((IRunAfter) currScene).runAfter();
            }
        });

    }
}
