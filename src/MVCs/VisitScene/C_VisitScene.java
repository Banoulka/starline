package MVCs.VisitScene;

import Abstracts.CelestialBody;
import Abstracts.Controller;
import Base.Interfaces.IRunAfter;
import Base.SceneManager;
import Base.Scenes.PlayScene;
import MVCs.PlayerData.M_PlayerData;
import javafx.scene.layout.Pane;

public class C_VisitScene extends Controller implements IRunAfter {

    protected V_VisitScene view;
    protected M_VisitScene model;

    protected static CelestialBody visiting;

    public C_VisitScene(Pane root) {

        model = new M_VisitScene(visiting);
        view = new V_VisitScene(root, model);

        view.getBackButton().setOnMouseReleased(mouseEvent -> goBackToPlay(visiting));
    }

    public static void setVisiting(CelestialBody visiting) {
        C_VisitScene.visiting = visiting;
    }

    public void goBackToPlay(CelestialBody celestialBody) {
        M_PlayerData.getInstance().addPlanet(celestialBody);
        SceneManager.setCurrScene(PlayScene.get(true));
    }

    @Override
    public M_VisitScene model() {
        return model;
    }

    @Override
    public V_VisitScene view() {
        return view;
    }

    @Override
    public void runAfter() {

    }
}
