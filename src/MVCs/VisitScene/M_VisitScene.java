package MVCs.VisitScene;

import Abstracts.CelestialBody;
import Abstracts.GameObject;
import Base.Builders.AsteroidBuilder;
import Base.GameObjectPool;
import Abstracts.Model;
import Base.Builders.BulletBuilder;
import Base.GameObjects.Bullet;
import Base.GameObjects.rOcks.Asteroid;
import Base.Utility.Config;
import Base.Utility.RandomRange;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class M_VisitScene extends Model {

    private CelestialBody currentlyVisiting;

    private List<GameObject> gameObjects = new ArrayList<>();

    // ObjectPool of bullets
    public final GameObjectPool<Bullet> BulletPool = new GameObjectPool<Bullet>();

    // ObjectPool of asteroids
    public final GameObjectPool<Asteroid> AsteroidPool = new GameObjectPool<Asteroid>();

    public M_VisitScene(CelestialBody currentlyVisiting) {
        this.currentlyVisiting = currentlyVisiting;

        BulletPool.setModelGameObjects(gameObjects);
        AsteroidPool.setModelGameObjects(gameObjects);

        BulletPool
                .setGameObjectBuilder(new BulletBuilder())
                .createObjects(50)
                .createOnEmpty(true);

        double windowWidth = Config.WINDOW_WIDTH;
        double windowHeight = Config.WINDOW_HEIGHT;

        RandomRange randomRangeX = new RandomRange()
                .addRange(-100, 0)
                .addRange(windowWidth, windowWidth+100);

        RandomRange randomRangeY = new RandomRange()
                .addRange(-100, 0)
                .addRange(windowHeight, windowHeight+100);


        // Asteroid builder with values set
        AsteroidBuilder.spawnRangeX = randomRangeX;
        AsteroidBuilder.spawnRangeY = randomRangeY;
        AsteroidBuilder.paneHeight = windowHeight;
        AsteroidBuilder.paneWidth = windowWidth;
        AsteroidBuilder.minSize = 0.2;
        AsteroidBuilder.maxSize = 1;
        AsteroidBuilder.minSpeed = 0.2f;
        AsteroidBuilder.maxSpeed = 1.5f;

        AsteroidPool
                .setGameObjectBuilder(new AsteroidBuilder())
                .createObjects(50)
                .createOnEmpty(true);
    }

    public <T extends GameObject> List<T> getGameObjectsByType(Class<T> type) {
        return gameObjects.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    public CelestialBody getCurrentlyVisiting() {
        return currentlyVisiting;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }


}
