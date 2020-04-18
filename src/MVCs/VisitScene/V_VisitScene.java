package MVCs.VisitScene;

import Abstracts.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class V_VisitScene extends View {

    private Label title;
    private Button backButton;
    private M_VisitScene model;

    public V_VisitScene(Pane root, M_VisitScene model) {
        super(root);
        this.model = model;

        title = new Label("Visiting... " + model.getCurrentlyVisiting().getName());
        title.getStyleClass().add("title");

        backButton = new Button();
        backButton.setText("Back button");

        VBox vBoxTitle = new VBox();
        vBoxTitle.setAlignment(Pos.TOP_CENTER);
        vBoxTitle.setPadding(new Insets(70, 0, 70, 0));
        vBoxTitle.getChildren().addAll(title, backButton);

        ((BorderPane) root).setTop(vBoxTitle);
    }

    public Button getBackButton() {
        return backButton;
    }

    @Override
    public void updateView() {

    }
}
