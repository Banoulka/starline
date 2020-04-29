package MVCs.ViewScene;

import Abstracts.CelestialBody;
import Abstracts.Controller;
import Abstracts.Model;
import Abstracts.View;
import Base.GameObjects.Planets.Planet;
import Base.Interfaces.IRunAfter;
import Base.SceneManager;
import Base.Scenes.PlayScene;
import javafx.scene.layout.Pane;

public class C_ViewScene extends Controller implements IRunAfter {

    private V_ViewScene view;
    private M_ViewScene model;

    public C_ViewScene(Pane root, CelestialBody viewingBody) {
        model = new M_ViewScene(viewingBody);
        view = new V_ViewScene(root, this);
    }

    public void backToPlay() {
        SceneManager.setCurrScene(new PlayScene());
    }

    @Override
    public Model model() {
        return model;
    }

    @Override
    public View view() {
        return view;
    }

    @Override
    public void runAfter() {
        view.runAfter();
    }
}
