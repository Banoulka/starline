package MVCs.PlayerData;

import Abstracts.Controller;
import javafx.scene.layout.Pane;

public class C_PlayerData extends Controller {

    protected M_PlayerData model;
    protected V_PlayerData view;

    public C_PlayerData(Pane root) {
        // Base constructor for player data sending a playerData model and view
        model = M_PlayerData.getInstance();
        view = new V_PlayerData(root);
        view.updateView();
    }

    @Override
    public M_PlayerData model() {
        return model;
    }

    @Override
    public V_PlayerData view() {
        return view;
    }
}
