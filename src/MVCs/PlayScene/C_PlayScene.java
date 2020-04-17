package MVCs.PlayScene;

import Abstracts.CelestialBody;
import Abstracts.Controller;
import Abstracts.GameObject;
import Base.Coord;
import Base.EventManager;
import Base.Interfaces.Actions.*;
import Base.Interfaces.IRunAfter;
import Base.SceneManager;
import Base.Scenes.PlayScene;
import Base.Scenes.VisitScene;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;

import java.util.List;

public class C_PlayScene extends Controller implements IRunAfter {

    protected M_PlayScene model;
    protected V_PlayScene view;

    protected PlayScene scene;

    public C_PlayScene(Pane root, PlayScene parentScene) {
        scene = parentScene;

        // Base constructor for player data sending a playerData model and view
        model = new M_PlayScene();
        view = new V_PlayScene(root, model);
    }

    private void setupDraggable() {
        // Local instance of layerDraggable
        Pane layerDraggable = view.getGameObjectLayerDraggable();

        final Coord drag = new Coord();
        final Coord pos = new Coord();

        // Get the pane
        final Pane pane = view.getPane();

        // Calculate the bound width of X
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
        layerDraggable.setOnMouseReleased(mouseEvent -> {
            layerDraggable.setCursor(Cursor.DEFAULT);
        });
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
        }
    }

    public void visit(CelestialBody celestialBody) {
        SceneManager.setCurrScene(new VisitScene(celestialBody));
    }

    @Override
    public void runAfter() {
        // Run the view after first
        view.runAfter();

        // Our run after
        setupObjects();
        setupEvents();
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
