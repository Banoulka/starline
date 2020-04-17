package Base.Scenes;

import Abstracts.CelestialBody;
import Abstracts.ExtendableScene;
import MVCs.VisitScene.C_VisitScene;
import javafx.scene.layout.BorderPane;

public class VisitScene extends ExtendableScene {

    private C_VisitScene visitScene;

    public VisitScene(CelestialBody visiting) {
        super(new BorderPane());

        visitScene = new C_VisitScene(parent, visiting);
    }
}
