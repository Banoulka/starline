package MVCs.PlayerData;

import Abstracts.View;
import Base.Config;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class V_PlayerData extends View {

    private Label nameLabel;

    private M_PlayerData model;

    public V_PlayerData(Pane root) {
        super(root);

        model = M_PlayerData.getInstance();

        // UI Elements
        nameLabel = new Label();
        nameLabel.getStyleClass().add("name");

        // Hbox for layout and styles
        HBox hb = new HBox();
        hb.getChildren().add(nameLabel);
        hb.setPadding(new Insets(15, 12, 15, 12));
        hb.setStyle("-fx-background-color: #171516");
        hb.setMinSize(Config.WINDOW_WIDTH, 140);

        // Adding the hbox at the bottom
        ((BorderPane) root).setBottom(hb);
    }

    @Override
    public void updateView() {
        System.out.println("V_PlayerData :: Update View");
        nameLabel.setText(model.getPlayerName());
    }
}
