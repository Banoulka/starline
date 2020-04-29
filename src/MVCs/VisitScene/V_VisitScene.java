package MVCs.VisitScene;

import Abstracts.GameObject;
import Abstracts.View;
import Base.Controls.PlayerController;
import Base.Utility.Coord;
import Base.GameObjects.Bullet;
import Base.GameObjects.rOcks.Asteroid;
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
import javafx.scene.transform.Rotate;

import java.util.Iterator;


public class V_VisitScene extends View implements IRunAfter {

    private Label title;
    private Button backButton;

    private M_VisitScene model;
    private C_VisitScene controller;

    private Pane pane;
    private BorderPane ui;

    private boolean playerEntered = false;
    private boolean playerLeaving = false;

    private Line goLineUI;
    private ImageView playerRocketUI;
    private ImageView gun;

    private Line healthLine;

    private MouseEvent mouseEvent;

    // Turret and rotation
    private ImageView turret;
    private Rotate turretRotation;

    // Get the local player
    private PlayerGO player = PlayerController.get().getPlayerForVisitScene();

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

        // Give the pane to the model's object pools
        model.BulletPool.setParent(pane);
        model.AsteroidPool.setParent(pane);

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

    public void movePlayer() {
        if (!playerEntered) return;

        PlayerController.get().movePlayer(pane.getWidth(), pane.getHeight());
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
                double diffX = mouseEvent.getX() - turret.getLayoutX();
                double diffY = mouseEvent.getY() - turret.getLayoutY();
                double angle = Math.toDegrees(Math.atan2(diffY, diffX));
                turretRotation.setAngle(angle);
            }
        }

        // Gameobject loop
        Iterator<GameObject> gameObjectIterator = model.getGameObjects().iterator();
        while(gameObjectIterator.hasNext()) {
            // Update the current game object
            GameObject currObject = gameObjectIterator.next();
            currObject.update();

            if (currObject instanceof Bullet && isOutOfBounds(currObject)) {
                gameObjectIterator.remove();
                model.BulletPool.despawn((Bullet) currObject);
                return;
            }

            if (currObject instanceof Asteroid ) {

                if (currObject.getBoundsInParent().intersects(player.getBoundsInParent())) {
                    // Collide with player, take off 10 health
                    gameObjectIterator.remove();
                    model.AsteroidPool.despawn((Asteroid) currObject);
                    M_PlayerData.getInstance().setHealth(M_PlayerData.getInstance().getHealth()-10);

                    if (M_PlayerData.getInstance().getHealth() <= 0) {
                        // Player dead
                        System.out.println("player dead");
                    }
                }

                // Iterate through every asteroid for every game bullet
                Iterator<Bullet> bulletIterator = model.getGameObjectsByType(Bullet.class).iterator();
                while(bulletIterator.hasNext()) {
                    Bullet bullet = bulletIterator.next();

                    // If asteroid collided with bullet, remove both
                    if (currObject.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                        System.out.println("Bullet collided with asteroid");
                        gameObjectIterator.remove();
                        bulletIterator.remove();

                        model.AsteroidPool.despawn((Asteroid) currObject);
                        model.BulletPool.despawn(bullet);
                        return;
                    }
                }

                // If the asteroid is out of the screen bounds and does not have grace
                // time left. Remove it
                if (isOutOfBounds(currObject) && !((Asteroid) currObject).hasGraceTime()) {
                    gameObjectIterator.remove();
                    model.AsteroidPool.despawn((Asteroid) currObject);
                    return;
                }
            }
        }
        M_PlayerData playerData = M_PlayerData.getInstance();
        healthLine.setEndX(100 + playerData.getHealth() * 2);
    }

    private boolean isOutOfBounds(GameObject object) {
        boolean remove = false;
        if (object.getLayoutX() > pane.getWidth() + 100 || object.getLayoutX() < -100) {
            remove = true;
        } else if (object.getLayoutY() > pane.getHeight() + 100 || object.getLayoutY() < -100) {
            remove = true;
        }

        return remove;
    }

    public void createBullet(MouseEvent mouseEvent) {
        if (!playerEntered) return;

        Bullet newBullet = model.BulletPool.spawn();

        // Create a bullet at the end of the gun using MATHS WOOO
        newBullet.setOrigin(
                new Coord(
                        turret.getLayoutX() + (turret.getFitWidth() * Math.cos(Math.toRadians(turretRotation.getAngle())) ),
                        turret.getLayoutY() + (turret.getFitWidth() * Math.sin(Math.toRadians(turretRotation.getAngle())) )
                ));
        newBullet.setDirection(turretRotation.getAngle());
    }

    @Override
    public void runAfter() {
        setupStars();
        setupPlayerPosition();
        setupUI();

        // Start all the animations that dont run on a timer
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

        M_PlayerData playerData = M_PlayerData.getInstance();

        healthLine = new Line();
        healthLine.setStrokeWidth(6);
        healthLine.setStroke(Color.GREEN);
        healthLine.setStartX(100);
        healthLine.setEndX(100 + playerData.getHealth() * 2);
        healthLine.setLayoutY(40);
        healthLine.setLayoutX(40);

        Line healthLineStatic = new Line();
        healthLineStatic.setStrokeWidth(6);
        healthLineStatic.setStroke(Color.RED);
        healthLineStatic.setStartX(100);
        healthLineStatic.setEndX(300);
        healthLineStatic.setLayoutY(40);
        healthLineStatic.setLayoutX(40);

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

        uiPane.getChildren().addAll(staticLine, goLineUI, planetImage, playerRocketUI, healthLineStatic, healthLine);

        ui.setCenter(uiPane);
    }

    private void setupPlayerPosition() {
        // Add the player
        player.setPosition(new Coord(pane.getWidth() + 250, pane.getHeight() / 2));

        turret = new ImageView("/Resources/Min/TURRET.png");
        turret.setFitHeight(30);
        turret.setFitWidth(100);
        // Bind this turrets properties to the players,
        // essentially this means the turret copies the player layout with movement
        turret.layoutYProperty().bind(player.layoutYProperty().add(turret.getFitWidth() + 15));
        turret.layoutXProperty().bind(player.layoutXProperty().add(turret.getFitWidth() - 8));

        turretRotation = new Rotate();
        turretRotation.setPivotX(0);
        turretRotation.setPivotY(0 + turret.getFitHeight()/2);
        turretRotation.setAngle(180);
        turret.getTransforms().add(turretRotation);

        pane.getChildren().addAll(player, turret);
        turret.toFront();
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
