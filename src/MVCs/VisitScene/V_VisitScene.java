package MVCs.VisitScene;

import Abstracts.GameObject;
import Abstracts.View;
import Base.Config;
import Base.Coord;
import Base.Interfaces.IRunAfter;
import Base.Misc.PlayerGO;
import Base.Planets.Star;
import Base.StarFactory;
import MVCs.PlayerData.M_PlayerData;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class V_VisitScene extends View implements IRunAfter {

    private Label title;
    private Button backButton;

    private M_VisitScene model;
    private C_VisitScene controller;

    private Pane pane;
    private PlayerGO player = M_PlayerData.getInstance().getPlayerGO();

    private boolean playerEntered = false;
    private boolean playerLeaving = false;

    public V_VisitScene(Pane root, M_VisitScene model, C_VisitScene controller) {
        super(root);
        this.model = model;
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

    public Button getBackButton() {
        return backButton;
    }

    public void movePlayer(Coord coord) {
        if (!playerEntered) return;

        // Move player
        player.moveX(coord.x);
        player.moveY(coord.y);
    }

    @Override
    public void updateView() {
        // check if player hasnt entered yet
        // if player hasnt entered.. move him towards to the center of the screen
        if (!playerEntered) {
            player.moveX(-1);
            if (player.getPosition().x <= 1500) {

                // Player has entered, give controls to human
                playerEntered = true;
            }
        }

        player.update();
    }

    @Override
    public void runAfter() {
        setupStars();
        setupPlayerPosition();
    }

    private void setupPlayerPosition() {
        // Add the player
        player = M_PlayerData.getInstance().getPlayerGO();
        player.setRotate(-90);
        player.setGoHeight(4);
        player.setGoWidth(2);
        player.setPosition(new Coord(pane.getWidth() + 250, pane.getHeight() / 2));

        // Play enter animation
        pane.getChildren().add(player);
    }

    private void setupStars() {
        // Creating the star background with different settings
        Pane backgroundLayer = new Pane();
        backgroundLayer.setPrefSize(pane.getWidth(), pane.getHeight());

        StarFactory starFactory = new StarFactory(backgroundLayer.getPrefWidth(), backgroundLayer.getPrefHeight(), StarFactory.StarType.ANIMATE_LEFT);
        starFactory.setNoOfStars(300);
        backgroundLayer.getChildren().addAll(starFactory.buildStars());

        pane.getChildren().add(backgroundLayer);
    }

    public Pane getPane() {
        return pane;
    }
}
