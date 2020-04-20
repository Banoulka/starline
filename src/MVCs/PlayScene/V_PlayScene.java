package MVCs.PlayScene;

import Abstracts.CelestialBody;
import Abstracts.GameObject;
import Abstracts.View;
import Base.Config;
import Base.Interfaces.Actions.IVisitable;
import Base.StarFactory;
import MVCs.PlayerData.M_PlayerData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class V_PlayScene extends View {

    private Pane pane;

    // GameObject Pane
    private Pane gameObjectLayerDraggable;
    private Pane gameObjectLayerStatic;

    // Star background
    private StarFactory starFactory;

    // Model
    private M_PlayScene model;

    // Controller
    private C_PlayScene controller;

    // Current tooltip
    private Pane tooltip;

    // Canvas layer
    private Canvas canvasLayer;

    public V_PlayScene(Pane root, M_PlayScene model, C_PlayScene controller) {
        super(root);
        this.model = model;
        this.tooltip = null;
        this.controller = controller;

        // New background image
        Image image = new Image("/Resources/Min/BACKGROUND_NO_STAR-min.png", 1920, 1080, false, true);
        BackgroundSize backgroundSize = new BackgroundSize(105, 105, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
                );
        Background bg = new Background(backgroundImage);

        // New pane for layout and size
        pane = new BorderPane();
        pane.setBackground(bg);

        ((BorderPane) root).setCenter(pane);
    }

    @Override
    public void updateView() {

        // Update all the planets
        for (GameObject object : model.getGameObjects())
            object.update();
    }

    public void runAfter() {
        setupStars();
        setupGameObjectLayer();
        // Providing really laggy game
        setupCanvas();
    }

    public void translateDraggables(double x) {
        gameObjectLayerDraggable.setTranslateX(x);
        starFactory.setTranslateX(x);
        // Update planet positions and canvas layer
        updateView();
    }

    private void setupGameObjectLayer() {
        gameObjectLayerDraggable = new Pane();
        gameObjectLayerDraggable.setPrefSize(Config.PLAY_AREA_SIZE, pane.getHeight());

        gameObjectLayerStatic = new Pane();
        gameObjectLayerStatic.setPrefSize(pane.getWidth(), pane.getHeight());

        pane.getChildren().addAll(gameObjectLayerDraggable, gameObjectLayerStatic);
        gameObjectLayerStatic.toBack();
    }

    private void setupStars() {
        // Creating the star background with different settings
        Pane backgroundLayer = new Pane();
        backgroundLayer.setPrefSize(Config.PLAY_AREA_SIZE / 2, pane.getHeight());

        starFactory = new StarFactory(backgroundLayer.getPrefWidth(), backgroundLayer.getPrefHeight(), StarFactory.StarType.RANDOM);
        starFactory.setNoOfStars(500);
        backgroundLayer.getChildren().addAll(starFactory.buildStars());

        pane.getChildren().add(backgroundLayer);
    }

    private void setupCanvas() {

        canvasLayer = new Canvas(Config.PLAY_AREA_SIZE, pane.getHeight());

        // Setup canvas and graphics context
        GraphicsContext gc = canvasLayer.getGraphicsContext2D();

        // Set the graphics context layer up
        GameObject.setGraphicsContext(gc);

        // Debug stuff
        // Debug graphic line in the center;
        if (Config.DEBUG) {
            gc.setStroke(Color.YELLOW);
            gc.strokeLine(0, canvasLayer.getHeight()/2, canvasLayer.getWidth(), canvasLayer.getHeight()/2);
        }


        gameObjectLayerDraggable.getChildren().add(canvasLayer);
        canvasLayer.toBack();
    }

    public void showTooltip(CelestialBody body, MouseEvent mouseEvent) {
        hideTooltip();
        tooltip = new Pane();
        tooltip.setStyle("-fx-background-color: rgba(45,46,48,0.98)");
        tooltip.setTranslateX(body.getPosition().x + mouseEvent.getX() + 10.0);
        tooltip.setTranslateY(body.getPosition().y + mouseEvent.getY() + 10.0);

        boolean isKnown = M_PlayerData.getInstance().isKnown(body);
        String title = isKnown ? body.getName() : "Unknown Body";

        Label label = new Label();
        label.setText(title);
        label.setStyle("-fx-text-fill: white");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(10, 15, 10, 15));
        vBox.getChildren().add(label);

        if (isKnown) {
            Button actionButton = new Button();
            actionButton.setText("View");
            vBox.getChildren().add(actionButton);
        }


        if (body instanceof IVisitable && body != M_PlayerData.getInstance().getCurrPlanet()) {
            Button visitButton = new Button();
            visitButton.setText("Visit");
            vBox.getChildren().add(visitButton);
            visitButton.setOnMouseReleased(mouseEvent1 -> controller.visit(body));
        }

        tooltip.getChildren().add(vBox);

        gameObjectLayerDraggable.getChildren().add(tooltip);
    }

    public void hideTooltip() {
        gameObjectLayerDraggable.getChildren().remove(tooltip);
    }

    public Pane getPane() {
        return pane;
    }

    public Pane getGameObjectLayerDraggable() {
        return gameObjectLayerDraggable;
    }

    public Pane getGameObjectLayerStatic() {
        return gameObjectLayerStatic;
    }
}
