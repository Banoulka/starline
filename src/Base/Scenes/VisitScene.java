package Base.Scenes;

import Abstracts.ExtendableScene;
import MVCs.VisitScene.C_VisitScene;
import javafx.scene.layout.BorderPane;

public class VisitScene extends ExtendableScene {

    private C_VisitScene visitScene;
    private static VisitScene instance = new VisitScene();

    public VisitScene() {
        super(new BorderPane());

        visitScene = new C_VisitScene(parent);
    }

    public static VisitScene get() {
        return instance;
    }

}
