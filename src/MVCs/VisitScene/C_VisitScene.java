package MVCs.VisitScene;

import Abstracts.CelestialBody;
import Abstracts.Controller;
import Base.SceneManager;
import Base.Scenes.PlayScene;
import javafx.scene.layout.Pane;

public class C_VisitScene extends Controller {

    protected V_VisitScene view;
    protected M_VisitScene model;

    private PlayScene prevScene;

    public C_VisitScene(Pane root, CelestialBody visiting) {
        super(root);

        model = new M_VisitScene(visiting);
        view = new V_VisitScene(root, model);

        view.getBackButton().setOnMouseReleased(mouseEvent -> goBackToPlay());
    }

    public void goBackToPlay() {
        SceneManager.setCurrScene(new PlayScene());
    }

    @Override
    public M_VisitScene model() {
        return model;
    }

    @Override
    public V_VisitScene view() {
        return view;
    }
}
