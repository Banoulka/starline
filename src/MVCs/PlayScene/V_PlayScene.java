package MVCs.PlayScene;

import Abstracts.CelestialBody;
import Abstracts.GameObject;
import Abstracts.View;
import Base.Config;
import Base.StarFactory;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class V_PlayScene extends View {

    private Pane pane;
    private Pane starPane;

    // GameObject Pane
    private Pane gameObjectLayerDraggable;
    private Pane gameObjectLayerStatic;
    private Pane canvasLayer;

    // Star background
    private StarFactory starFactory;

    // Model
    private M_PlayScene model;

    // Current tooltip
    private Pane tooltip;

    public V_PlayScene(Pane root, M_PlayScene model) {
        super(root);
        this.model = model;
        tooltip = null;

        // New background image
        Image image = new Image("/Resources/BACKGROUND_NO_STAR.png", 1920, 1080, false, true);
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
        pane = new Pane();
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
        setupCanvas();
    }

    public void translateDraggables(double x) {
        gameObjectLayerDraggable.setTranslateX(x);
        starFactory.setTranslateX(x);
        canvasLayer.setTranslateX(x);
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

        starFactory = new StarFactory(backgroundLayer.getPrefWidth(), backgroundLayer.getPrefHeight());
        starFactory.setNoOfStars(300);
        backgroundLayer.getChildren().addAll(starFactory.buildStars());

        pane.getChildren().add(backgroundLayer);
    }

    private void setupCanvas() {

        // Setup canvas and graphics context
        Canvas canvas = new Canvas(Config.PLAY_AREA_SIZE, pane.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Set the graphics context layer up
        GameObject.setGraphicsContext(gc);

        // Debug stuff
        // Debug graphic line in the center;
        if (Config.DEBUG) {
            gc.setStroke(Color.YELLOW);
            gc.strokeLine(0, canvas.getHeight()/2, canvas.getWidth(), canvas.getHeight()/2);
        }

        canvasLayer = new Pane();
        canvasLayer.getChildren().add(canvas);

        pane.getChildren().add(canvasLayer);
        canvasLayer.toBack();
    }

    public void showTooltip(CelestialBody body, MouseEvent mouseEvent) {
        hideTooltip();
        tooltip = new Pane();
        tooltip.setStyle("-fx-background-color: red");
        tooltip.setPrefSize(90, 90);

        tooltip.setTranslateX(body.getLayoutX() + mouseEvent.getX() + 10.0);
        tooltip.setTranslateY(body.getLayoutY() + mouseEvent.getY() + 10.0);

        gameObjectLayerDraggable.getChildren().add(tooltip);

        System.out.println("Showing tooltip for " + body.getName());
    }

    public void hideTooltip() {
        System.out.println("Hiding tooltip....");
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

    public Pane getCanvasLayer() {
        return canvasLayer;
    }
}
