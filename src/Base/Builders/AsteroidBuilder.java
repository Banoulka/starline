package Base.Builders;

import Base.GameObjects.rOcks.Asteroid;
import Base.Interfaces.IGameObjectBuilderEmpty;
import Base.Utility.Coord;
import Base.Utility.RandomRange;
import Base.Utility.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsteroidBuilder extends GameObjectBuilder<Asteroid, AsteroidBuilder> implements IGameObjectBuilderEmpty<Asteroid> {

    private Asteroid asteroid = new Asteroid();

    public static RandomRange spawnRangeX;
    public static RandomRange spawnRangeY;

    public static double paneWidth;
    public static double paneHeight;

    public static double minSize;
    public static double maxSize;

    public static float minSpeed;
    public static float maxSpeed;

    private static List<String> imageNames = new ArrayList<String>(Arrays.asList(
            "asteroid1",
            "asteroid2",
            "asteroid3",
            "asteroid4"
    ));

    private void setSize(double size) {
        asteroid.setGoWidth(size);
        asteroid.setGoHeight(size);
    }

    @Override
    public Asteroid buildEmpty() {

        // Random image
        // TODO: randomise
        asteroid.setImg(imageNames.get(Utils.random.nextInt(imageNames.size()-1)));

        // Random position
        asteroid.setPosition(new Coord(spawnRangeX.getRandomNumber(), spawnRangeY.getRandomNumber()));

        // Random direction
        double randX = Utils.random.nextInt((int) paneWidth);
        double randY = Utils.random.nextInt((int) paneHeight);

        double distX = randX - asteroid.getPosition().x;
        double distY = randY - asteroid.getPosition().y;

        double angle = Math.toDegrees(Math.atan2(distY, distX));
        asteroid.setDirection(angle);

        // Random size
        setSize(minSize + Math.random() * (maxSize + minSpeed));

        // Random speed
        asteroid.setSpeed(minSpeed + (float) Math.random() * (maxSpeed + minSpeed));

        Asteroid asteroidToReturn = asteroid;
        clear();
        return asteroidToReturn;
    }

    @Override
    public Asteroid resetValues(Asteroid gameObject) {
        clear();
        return buildEmpty();
    }

    @Override
    protected Asteroid getObj() {
        return asteroid;
    }

    @Override
    protected AsteroidBuilder self() {
        return this;
    }

    @Override
    public void clear() {
        asteroid = new Asteroid();
    }

    @Override
    public Asteroid build() {
        return null;
    }
}
