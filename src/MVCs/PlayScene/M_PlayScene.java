package MVCs.PlayScene;

import Abstracts.CelestialBody;
import Abstracts.GameObject;
import Abstracts.Model;
import Base.Builders.PlanetBuilder;
import Base.Coord;
import Base.Interfaces.Actions.IEvent;
import Base.Planets.Planet;
import Base.Planets.Star;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class M_PlayScene extends Model {

    private List<GameObject> gameObjects;

    public double startingHeight;

    public M_PlayScene() {
        gameObjects = new ArrayList<GameObject>();
    }


    private void addObjects() {
        addPlanets(startingHeight);
    }


    private void addPlanets(double startingHeight) {

        PlanetBuilder pb = new PlanetBuilder();

        gameObjects.add(new Star(
                new Coord(-75, startingHeight),
                "Sun",
                2,
                18,
                false
        ));

        Planet mercury = pb
                .name("Mercury")
                .hasAtmosphere(false)
                .sizeSquare(1)
                .gravityPull(1)
                .position(new Coord(1400, startingHeight))
                .build();

        Planet venus = pb
                .name("Venus")
                .hasAtmosphere(false)
                .sizeSquare(4)
                .gravityPull(1)
                .position(new Coord(1900, startingHeight))
                .build();

        Planet earth = pb
                .name("Earth")
                .hasAtmosphere(true)
                .sizeSquare(5)
                .gravityPull(4)
                .position(new Coord(3000, startingHeight))
                .build();

        Planet mars = pb
                .name("Mars")
                .hasAtmosphere(true)
                .sizeSquare(2)
                .gravityPull(2)
                .position(new Coord(4000, startingHeight))
                .build();

        Planet jupiter = pb
                .name("Jupiter")
                .hasAtmosphere(false)
                .gravityPull(4)
                .sizeSquare(12)
                .position(new Coord(5200, startingHeight))
                .build();

        Planet saturn = pb
                .name("Saturn")
                .hasAtmosphere(false)
                .gravityPull(4)
                .sizeRect(12, 8.5)
                .position(new Coord(7700, startingHeight))
                .build();

        Planet uranus = pb
                .name("Uranus")
                .hasAtmosphere(false)
                .gravityPull(4)
                .sizeSquare(6)
                .position(new Coord(10000, startingHeight))
                .build();

        Planet neptune = pb
                .name("Neptune")
                .hasAtmosphere(false)
                .gravityPull(4)
                .sizeSquare(6)
                .position(new Coord(13500, startingHeight))
                .build();

        gameObjects.add(mercury);
        gameObjects.add(venus);
        gameObjects.add(earth);
        gameObjects.add(mars);
        gameObjects.add(jupiter);
        gameObjects.add(saturn);
        gameObjects.add(uranus);
        gameObjects.add(neptune);
    }

    public CelestialBody findBody(String name) {
        List<CelestialBody> bodies = getBodies();

        return (CelestialBody) bodies.stream()
                .filter(body -> body.getName().equals(name))
                .map(CelestialBody.class::cast)
                .toArray()[0];
    }

    public List<GameObject> getGameObjects() {
        if (gameObjects.isEmpty()) {
            addObjects();
        }
        return gameObjects;
    }

    public List<CelestialBody> getBodies() {
        return gameObjects.stream()
                .filter(CelestialBody.class::isInstance)
                .map(CelestialBody.class::cast)
                .collect(Collectors.toList());
    }

    public <T extends IEvent, J extends GameObject> List<J>
    getObjectTypeByAction(Class<T> action, Class<J> objectType) {

        return gameObjects.stream()
                .filter(action::isInstance)
                .filter(objectType::isInstance)
                .map(objectType::cast)
                .collect(Collectors.toList());
    }

    public <T extends IEvent> List<T> gameObjectsByActionType(Class<T> action) {
        return gameObjects.stream()
                .filter(action::isInstance)
                .map(action::cast)
                .collect(Collectors.toList());
    }
}
