package Base.Scenes;

import Abstracts.CelestialBody;
import Abstracts.ExtendableScene;
import MVCs.VisitScene.C_VisitScene;
import javafx.scene.layout.BorderPane;

public class VisitScene extends BorderPane {

    private C_VisitScene visitScene;

    public VisitScene(CelestialBody body) {
        super();

        visitScene = new C_VisitScene(this, body);
    }

}
