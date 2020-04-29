import Base.SceneManager;
import Base.Scenes.PlayScene;
import Base.Scenes.StartScene;
import Base.Scenes.ViewScene;
import Base.Scenes.VisitScene;
import Base.Scenes.StartScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Starline extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Starline");

        // Setup SceneManager
        // SceneManager handles the staging, scening etc.
        SceneManager.setStage(stage);

        // Set the first scene to the start scene
        // Specific scenes handle the controllers that are needed
        // Controllers handle the view / model situation

        // Hierarchy = SceneManager > Scenes > Controller(s) > View(s) / Model(s)
        SceneManager.setCurrScene(StartScene.get());
    }
}
