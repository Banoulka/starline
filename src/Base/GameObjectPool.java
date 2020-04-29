package Base;

import Abstracts.GameObject;
import Base.Interfaces.IGameObjectBuilderEmpty;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameObjectPool <T extends GameObject> {

    // Builder of object
    protected IGameObjectBuilderEmpty<T> gameObjectBuilder;

    // List of objects
    protected List<T> gameObjectList;

    // Should the pool auto-create objects to meet higher demands?
    protected boolean createOnEmpty = false;

    // The pane to remove the object from
    protected Pane parent;

    // The game object list to remove from
    protected List<GameObject> modelGameObjects;

    public GameObjectPool() {
        gameObjectList = new ArrayList<T>();
    }

    public void setParent(Pane parent) {
        this.parent = parent;
    }

    public void setModelGameObjects(List<GameObject> modelGameObjects) {
        this.modelGameObjects = modelGameObjects;
    }

    public GameObjectPool<T> setGameObjectBuilder(IGameObjectBuilderEmpty<T> gameObjectBuilder) {
        // Pass in a nice lil builder for this lovely pool
        this.gameObjectBuilder = gameObjectBuilder;
        return this;
    }

    public GameObjectPool<T> createObjects(int objects) {
        // Start the object pool off with X number of objects
        for (int i = 0; i < objects; i++) {
            gameObjectList.add(gameObjectBuilder.buildEmpty());
        }
        return this;
    }

    public GameObjectPool<T> createOnEmpty(boolean createOnEmpty) {
        this.createOnEmpty = createOnEmpty;
        return this;
    }

    public T spawn() {
        T objectToReturn;
        // Get an object from the pool and remove it from the pool
        // If the pool is empty and createOnEmpty is false... return null
        // otherwise return just a new object
        if (!gameObjectList.isEmpty()) {
            T gameObject = gameObjectList.get(0);
            gameObjectList.remove(gameObject);
            objectToReturn = gameObject;
        }
        else if (createOnEmpty)
            objectToReturn = gameObjectBuilder.buildEmpty();
        else return null;

        parent.getChildren().add(objectToReturn);
        modelGameObjects.add(objectToReturn);
        objectToReturn.startAnimation();
        return objectToReturn;
    }

    public void despawn(T gameObject) {
        // Add the despawned object back to the pool with
        // its values reset
        gameObjectList.add(gameObjectBuilder.resetValues(gameObject));

        // Remove the object from the game object list
        // and the parent
        parent.getChildren().remove(gameObject);
    }


    @Override
    public String toString() {
        return "GameObjectPool{" +
                "gameObjectList=" + gameObjectList +
                '}';
    }
}
