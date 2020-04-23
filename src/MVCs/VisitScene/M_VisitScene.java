package MVCs.VisitScene;

import Abstracts.CelestialBody;
import Abstracts.GameObject;
import Base.GameObjectPool;
import Abstracts.Model;
import Base.Builders.BulletBuilder;
import Base.GameObjects.Bullet;

import java.util.ArrayList;
import java.util.List;

public class M_VisitScene extends Model {

    private CelestialBody currentlyVisiting;

    private List<GameObject> gameObjects = new ArrayList<>();

    // ObjectPool of bullets
    public final GameObjectPool<Bullet> BulletPool = new GameObjectPool<Bullet>();

    public M_VisitScene(CelestialBody currentlyVisiting) {
        this.currentlyVisiting = currentlyVisiting;

        BulletPool
                .setGameObjectBuilder(new BulletBuilder())
                .createObjects(50)
                .createOnEmpty(true);
    }

    public CelestialBody getCurrentlyVisiting() {
        return currentlyVisiting;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }


}
