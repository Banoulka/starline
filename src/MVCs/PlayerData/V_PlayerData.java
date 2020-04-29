package MVCs.PlayerData;

import Abstracts.View;
import Base.Utility.Config;
import Base.Utility.Utils;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class V_PlayerData extends View {

    // Player Info UI
    private Label lb_nameLabel, lb_moneyLabel;

    // Player Actions UI
    private Button btn_showUpgrades;

    // Show Upgrades Screen
    private Pane upgradeScreen;

    private M_PlayerData model;

    public V_PlayerData(Pane root) {
        super(root);

        model = M_PlayerData.getInstance();

        // Pane for the lower elements
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #171516");
        pane.setMinSize(Config.WINDOW_WIDTH, 140);

        // UI Elements
        lb_nameLabel = new Label();
        lb_nameLabel.getStyleClass().add("name");

        lb_moneyLabel = new Label();
        lb_moneyLabel.getStyleClass().add("name");

        // Vbox for vertical alignment
        VBox playerInfo = new VBox();
        playerInfo.setMinSize(400, 140);
        playerInfo.getChildren().addAll(lb_moneyLabel, lb_nameLabel);
        playerInfo.setPadding(new Insets(15, 12, 15, 12));
        playerInfo.setSpacing(15);

        VBox playerActions = new VBox();
        playerActions.setMinSize(400, 140);
        playerActions.setPadding(new Insets(15, 12, 15, 12));
        playerActions.setSpacing(15);
        playerActions.setLayoutX(Config.WINDOW_WIDTH - 400);
        playerActions.setAlignment(Pos.TOP_RIGHT);

        // Buttons for the player actions
        btn_showUpgrades = new Button();
        btn_showUpgrades.setText("Purchase Upgrades");
        btn_showUpgrades.getStyleClass().add("action-btn");

        playerActions.getChildren().add(btn_showUpgrades);

        // Upgrade screen
        upgradeScreen = new Pane();
        upgradeScreen.setPrefSize(0, 0);
        upgradeScreen.setStyle("-fx-background-color: red");

        // Add all the alignment boxes
        pane.getChildren().addAll(playerInfo, playerActions);

        // Adding the pane at the bottom
        ((BorderPane) root).setBottom(pane);
        ((BorderPane) root).setTop(upgradeScreen);
    }

    @Override
    public void updateView() {
        System.out.println("V_PlayerData :: Update View");
        lb_nameLabel.setText(model.getPlayerName());
        lb_moneyLabel.setText("Money: " + Utils.numberFormatter.format(model.getPlayerMoney()));
    }

    public Button getBtn_showUpgrades() {
        return btn_showUpgrades;
    }

    public Pane getUpgradeScreen() {
        return upgradeScreen;
    }

    public void showUpgradeScreen() {

    }
}
