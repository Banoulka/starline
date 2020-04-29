package MVCs.PlayerData;

import Abstracts.Controller;
import Base.Utility.Config;
import javafx.scene.layout.Pane;

public class C_PlayerData extends Controller {

    protected M_PlayerData model;
    protected V_PlayerData view;

    public C_PlayerData(Pane root) {
        // Base constructor for player data sending a playerData model and view
        model = M_PlayerData.getInstance();
        view = new V_PlayerData(root);
        view.updateView();

        // Set action listeners
        view.getBtn_showUpgrades().setOnMouseClicked(mouseEvent -> {
            view.getBtn_showUpgrades().arm();

            // Show the upgrades screen?
            view.showUpgradeScreen();
        });
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
