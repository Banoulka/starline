package MVCs.PlayScene;

import Abstracts.GameObject;
import Abstracts.Model;
import Base.Coord;
import Base.Planets.Planet;

import java.util.ArrayList;

public class M_PlayScene extends Model {

    private ArrayList<GameObject> gameObjects;

    public double startingHeight;

    public M_PlayScene() {
        gameObjects = new ArrayList<GameObject>();
    }


    private void addObjects() {
        addPlanets(startingHeight);
    }


    private void addPlanets(double startingHeight) {
        gameObjects.add(new Planet(
                new Coord(-75, startingHeight),
                "Sun",
                2,
                18,
                false
        ));

        gameObjects.add(new Planet(
                new Coord(1400, startingHeight),
                "Mercury",
                1,
                1,
                false
        ));

        gameObjects.add(new Planet(
                new Coord(1900, startingHeight),
                "Venus",
                1,
                4,
                false
        ));

        gameObjects.add(new Planet(
                new Coord(3000, startingHeight),
                "Earth",
                4,
                5,
                false
        ));

        gameObjects.add(new Planet(
                new Coord(4000, startingHeight),
                "Mars",
                4,
                2,
                false
        ));
//
//        gameObjects.add(new Planet(
//                new Coord(5200, startingHeight),
//                "Jupiter",
//                4,
//                12,
//                false
//        ));

        gameObjects.add(new Planet(
                new Coord(7700, startingHeight),
                "Saturn",
                4,
                8.5,
                false
        ));

        gameObjects.add(new Planet(
                new Coord(10000, startingHeight),
                "Uranus",
                4,
                6,
                false
        ));

        gameObjects.add(new Planet(
                new Coord(13500, startingHeight),
                "Neptune",
                4,
                6,
                false
        ));
    }

    public ArrayList<GameObject> getGameObjects() {

        if (gameObjects.isEmpty()) {
            addObjects();
        }

        return gameObjects;
    }
}
