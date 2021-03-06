package MVCs.PlayScene;

import Abstracts.CelestialBody;
import Abstracts.Controller;
import Abstracts.GameObject;
import Base.Controls.PlayerController;
import Base.Scenes.ViewScene;
import Base.Utility.Coord;
import Base.EventManager;
import Base.Interfaces.Actions.IClickable;
import Base.Interfaces.Actions.IHoverable;
import Base.Interfaces.Actions.IPannable;
import Base.Interfaces.Actions.ITooltip;
import Base.Interfaces.IRunAfter;
import Base.GameObjects.PlayerGO;
import Base.SceneManager;
import Base.Scenes.VisitScene;
import MVCs.PlayerData.M_PlayerData;
import javafx.animation.AnimationTimer;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;

import java.util.List;

public class C_PlayScene extends Controller implements IRunAfter {

    protected M_PlayScene model;
    protected V_PlayScene view;

    // Local instance of playerData
    private M_PlayerData playerData = M_PlayerData.getInstance();

    public C_PlayScene(Pane root) {

        // Base constructor for player data sending a playerData model and view
        model = new M_PlayScene();
        view = new V_PlayScene(root, model, this);
    }

    private void setupDraggable() {
        // Local instance of layerDraggable
        Pane layerDraggable = view.getGameObjectLayerDraggable();

        final Coord drag = new Coord();
        final Coord pos = new Coord();

        // Get the pane
        final Pane pane = view.getPane();

        // Calculate the bound goWidth of X
        final double boundWidth = view.getPane().getWidth() - view.getGameObjectLayerDraggable().getPrefWidth();

        layerDraggable.setOnMousePressed(mouseEvent -> {

            // Get the original mouse coords
            drag.x = mouseEvent.getSceneX() - pos.x;

            // Change the cursor to the hand
            layerDraggable.setCursor(Cursor.CLOSED_HAND);

            view.hideTooltip();
        });

        layerDraggable.setOnMouseDragged(mouseEvent -> {
            // Calculate whether the mouse is being dragging left or right
            boolean draggingRight = mouseEvent.getSceneX() - drag.x < pos.x;

            // Can the layerDraggable be dragged?
            boolean canDrag = draggingRight && pos.x > boundWidth || !draggingRight && pos.x < 0;

            if (canDrag) {
                // Change the pos based on the current mouse pos - the old drag pos
                // Translate the layerDraggable within the bounds
                pos.x = mouseEvent.getSceneX() - drag.x;
                view.translateDraggables(pos.x);

            } else {
                // Reset the bounds if they are over so the layerDraggable NEVER
                // goes over the limit
                if (draggingRight && pos.x < boundWidth) {
                    view.translateDraggables(boundWidth);
                }

                else if (!draggingRight && pos.x > 0) {
                    view.translateDraggables(0);
                }
            }
        });

        // Reset cursor on released
        layerDraggable.setOnMouseReleased(mouseEvent -> layerDraggable.setCursor(Cursor.DEFAULT));
    }

    private void setupEvents() {
        // Setup the canvas drag
        setupDraggable();

        model.gameObjectsByActionType(IHoverable.class).forEach(EventManager::setupHover);
        model.gameObjectsByActionType(IClickable.class).forEach(EventManager::setupClickable);

        // Add tooltips to tooltipthings
        List<CelestialBody> bodies =  model.getObjectTypeByAction(ITooltip.class, CelestialBody.class);

        bodies.forEach(celestialBody -> {
            celestialBody.getNode().setOnMouseReleased(mouseEvent -> {
                // show tooltip or something
                view.showTooltip(celestialBody, mouseEvent);
            });
        });
    }

    private void setupObjects() {
        // Add the game objects to their reasonable layer
        model.startingHeight = view.getGameObjectLayerDraggable().getPrefHeight() / 2;

        for (GameObject gameObject : model.getGameObjects()) {
            if (gameObject instanceof IPannable)
                view.getGameObjectLayerDraggable().getChildren().add(gameObject);
            else
                view.getGameObjectLayerStatic().getChildren().add(gameObject);

            gameObject.startAnimation();
        }

    }

    private void setupPlayerData() {
        // Set player to whatever planet they are on
        PlayerGO player = PlayerController.get().getPlayerForPlayScene();

        // Always start with the earth and sun known
        CelestialBody earth = model.findBody("Earth");

        playerData.addKnownBody(earth);
        playerData.addKnownBody(model.findBody("The Sun"));

        // If the player is not currently on ANY planet... should only be on startup
        // then add them to earth
        CelestialBody currPlanet = playerData.getCurrPlanet();
        if (currPlanet == null) {
            playerData.setCurrPlanet(earth);
            // Reassign the current planet
            currPlanet = playerData.getCurrPlanet();
        }

        // Set the position of the player to the current planet
        player.setPosition(new Coord(
                currPlanet.getPosition().x - currPlanet.getTranslateX() + (currPlanet.getGoWidth()/2) - (player.getGoWidth()/2),
                currPlanet.getPosition().y - currPlanet.getTranslateY() - player.getGoHeight()/2 + 10
        ));

        // Bind properties so the player moves with the planets
        // TODO: Make the player move AROUND the planet with the rotation??
        player.translateXProperty().bind(currPlanet.translateXProperty());
        player.translateYProperty().bind(currPlanet.translateYProperty());

        // Add the player to the draggable layer
        view.getGameObjectLayerDraggable().getChildren().add(player);
        player.toBack();
    }

    public void visit(CelestialBody celestialBody) {
        SceneManager.setCurrScene(new VisitScene(celestialBody));
    }

    private void setupAnimationTimer() {
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                view.updateView();
            }
        }.start();
    }

    public void view(CelestialBody celestialBody) {
        SceneManager.setCurrScene(new ViewScene(celestialBody));
    }

    @Override
    public void runAfter() {
        // Run the view after first
        view.runAfter();

        // Our run after
        setupObjects();
        setupEvents();
        setupAnimationTimer();
        setupPlayerData();
    }

    @Override
    public V_PlayScene view() {
        return view;
    }

    @Override
    public M_PlayScene model() {
        return model;
    }
}
