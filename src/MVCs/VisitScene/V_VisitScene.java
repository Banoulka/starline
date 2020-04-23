package MVCs.VisitScene;

import Abstracts.GameObject;
import Abstracts.View;
import Base.Builders.BulletBuilder;
import Base.Coord;
import Base.GameObjects.Bullet;
import Base.Interfaces.IRunAfter;
import Base.GameObjects.PlayerGO;
import Base.StarFactory;
import MVCs.PlayerData.M_PlayerData;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Iterator;


public class V_VisitScene extends View implements IRunAfter {

    private Label title;
    private Button backButton;

    private M_VisitScene model;
    private C_VisitScene controller;

    private Pane pane;
    private BorderPane ui;
    private PlayerGO player = M_PlayerData.getInstance().getPlayerGO();

    private boolean playerEntered = false;
    private boolean playerLeaving = false;


    private Line goLineUI;
    private ImageView playerRocketUI;
    private ImageView gun;

    private MouseEvent mouseEvent;

    // Test
    private Rectangle rect;

    public V_VisitScene(Pane root, M_VisitScene model, C_VisitScene controller) {
        super(root);
        this.model = model;
        this.controller = controller;

        // Add the player to the list of current gameobjects
        // in the scene
        model.addGameObject(player);

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

        ui = new BorderPane();
        ui.setPrefSize(pane.getWidth(), 300);
        ((BorderPane) pane).setBottom(ui);

        ((BorderPane) root).setCenter(pane);
    }

    public void changeMouseEvent(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
    }

    public Button getBackButton() {
        return backButton;
    }

    public void movePlayer(Coord coord) {
        if (!playerEntered) return;


        // Check position against screen bounds
        double playerX = player.getPosition().x;
        double playerY = player.getPosition().y;

        // Move player within pane bounds
        if (playerX < (pane.getWidth()) && playerX > 0)
            player.moveX(coord.x);

        if (playerY < pane.getHeight() && playerY > 0)
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
                controller.startTimers();
            }
        } else {
            if (mouseEvent != null) {
                double diffX = mouseEvent.getX() - rect.getLayoutX();
                double diffY = mouseEvent.getY() - rect.getLayoutY();
                double angle = Math.toDegrees(Math.atan2(diffY, diffX));
                rect.setRotate(angle);
            }
        }

        Iterator<GameObject> iterator = model.getGameObjects().iterator();

        // Gameobjects in the list ( includes the player )
//        for (GameObject go : model.getGameObjects()) {
//            go.update();
//
//            // Check if BULLETs are out of bounds

//        }

        while(iterator.hasNext()) {
            GameObject currObj = iterator.next();
            currObj.update();

            // Janky ?
            if (currObj instanceof Bullet) {
                boolean remove = false;
                if (currObj.getLayoutX() > pane.getWidth() + 100 || currObj.getLayoutX() < -100) {
                    remove = true;
                } else if (currObj.getLayoutY() > pane.getHeight() + 100 || currObj.getLayoutY() < -100) {
                    remove = true;
                }

                if (remove) {
                    pane.getChildren().remove(currObj);
                    model.BulletPool.despawn((Bullet) currObj);
                    iterator.remove();
                }
            }
        }
    }

    public void createBullet(MouseEvent mouseEvent) {
        if (!playerEntered) return;

        Bullet newBullet = model.BulletPool.spawn();
        newBullet.setOrigin(new Coord(rect.getLayoutX(), rect.getLayoutY()));
        newBullet.setDirection(rect.getRotate());

        pane.getChildren().add(newBullet);
        model.addGameObject(newBullet);
    }

    @Override
    public void runAfter() {
        setupStars();
        setupPlayerPosition();
        setupUI();

        model.getGameObjects().forEach(GameObject::startAnimation);
    }

    public void updateUI(double offset) {
        // get called every WHATEVER - by controller
        goLineUI.setEndX(goLineUI.getEndX() - offset);
        playerRocketUI.setLayoutX(playerRocketUI.getLayoutX() - offset);
    }

    private void setupUI() {
        Pane uiPane = new Pane();
        uiPane.setPrefSize(ui.getWidth(), ui.getHeight());

        goLineUI = new Line();
        goLineUI.setStroke(Color.WHITE);
        goLineUI.setStrokeWidth(3);
        goLineUI.setLayoutY(ui.getHeight() / 2);
        goLineUI.setStartX(380);
        goLineUI.setEndX(pane.getWidth() - 380);

        Line staticLine = new Line();
        staticLine.setStroke(Color.GREEN);
        staticLine.setStrokeWidth(3);
        staticLine.setLayoutY(ui.getHeight() / 2);
        staticLine.setStartX(380);
        staticLine.setEndX(pane.getWidth() - 380);

        ImageView planetImage = new ImageView();
        planetImage.setFitHeight(80);
        planetImage.setFitWidth(80);
        planetImage.setLayoutY(ui.getHeight() / 2 - (planetImage.getFitHeight()/2));
        planetImage.setLayoutX(350);
        planetImage.setImage(model.getCurrentlyVisiting().getImg().getImage());

        playerRocketUI = new ImageView();
        playerRocketUI.setFitWidth(40);
        playerRocketUI.setFitHeight(80);
        playerRocketUI.setLayoutY(ui.getHeight() / 2 - (playerRocketUI.getFitHeight()/2));
        playerRocketUI.setLayoutX(pane.getWidth() - 360);
        playerRocketUI.setImage(player.getImg().getImage());
        playerRocketUI.setRotate(-90);

        uiPane.getChildren().addAll(staticLine, goLineUI, planetImage, playerRocketUI);

        ui.setCenter(uiPane);
    }

    private void setupPlayerPosition() {
        // Add the player
        player = M_PlayerData.getInstance().getPlayerGO();
        player.setRotate(-90);
        player.setGoHeight(4);
        player.setGoWidth(2);
        player.setPosition(new Coord(pane.getWidth() + 250, pane.getHeight() / 2));


        rect = new Rectangle();
        rect.setFill(Color.GREEN);
        rect.setLayoutX(500);
        rect.setLayoutY(500);
        rect.setWidth(40);
        rect.setHeight(40);

        pane.getChildren().addAll(player, rect);
        rect.toFront();
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
