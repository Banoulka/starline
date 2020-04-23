package Base;

import Abstracts.GameObject;
import Base.Interfaces.IGameObjectBuilderEmpty;

import java.util.ArrayList;
import java.util.List;

public class GameObjectPool <T extends GameObject> {

    // Builder of object
    protected IGameObjectBuilderEmpty<T> gameObjectBuilder;

    // List of objects
    protected List<T> gameObjectList;

    // Should the pool auto-create objects to meet higher demands?
    protected boolean createOnEmpty = false;

    public GameObjectPool() {
        gameObjectList = new ArrayList<T>();
    }

    public GameObjectPool<T> setGameObjectBuilder(IGameObjectBuilderEmpty<T> gameObjectBuilder) {
        // Pass in a nice lil builder for this lovely pool
        this.gameObjectBuilder = gameObjectBuilder;
        return this;
    }

    public GameObjectPool<T> createObjects(int objects) {
        // Start the object pool off with X number of objects
        for (int i = 0; i < objects; i++)
            gameObjectList.add(gameObjectBuilder.buildEmpty());
        return this;
    }

    public GameObjectPool<T> createOnEmpty(boolean createOnEmpty) {
        this.createOnEmpty = createOnEmpty;
        return this;
    }

    public T spawn() {
        // Get an object from the pool and remove it from the pool
        // If the pool is empty and createOnEmpty is false... return null
        // otherwise return just a new object
        if (!gameObjectList.isEmpty()) {
            T gameObject = gameObjectList.get(0);
            gameObjectList.remove(gameObject);
            return gameObject;
        }
        else if (createOnEmpty)
            return gameObjectBuilder.buildEmpty();
        else
            return null;
    }

    public void despawn(T gameObject) {
        // Add the despawned object back to the pool with
        // its values reset
        gameObjectList.add(gameObjectBuilder.resetValues(gameObject));
    }


    @Override
    public String toString() {
        return "GameObjectPool{" +
                "gameObjectList=" + gameObjectList +
                '}';
    }
}
