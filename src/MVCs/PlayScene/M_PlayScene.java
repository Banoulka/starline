package MVCs.PlayScene;

import Abstracts.CelestialBody;
import Abstracts.GameObject;
import Abstracts.Model;
import Base.Builders.MoonBuilder;
import Base.Builders.PlanetBuilder;
import Base.Builders.StarBuilder;
import Base.PlanetData.*;
import Base.Utility.Coord;
import Base.Interfaces.Actions.IEvent;
import Base.GameObjects.Planets.Moon;
import Base.GameObjects.Planets.Planet;
import Base.GameObjects.Planets.Star;

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
        StarBuilder sb = new StarBuilder();
        MoonBuilder mb = new MoonBuilder();

        Star sun = sb
                .name("The Sun")
                .hasAtmosphere(false)
                .sizeSquare(22)
                .gravityPull(20)
                .image("sun")
                .position(new Coord(-450, startingHeight))
                .planetData(new SunData())
                .build();

        Planet mercury = pb
                .name("Mercury")
                .hasAtmosphere(false)
                .sizeSquare(1)
                .gravityPull(1)
                .position(new Coord(1400, startingHeight))
                .planetData(new MercuryData())
                .build();

        Planet venus = pb
                .name("Venus")
                .hasAtmosphere(false)
                .sizeSquare(4)
                .gravityPull(1)
                .position(new Coord(1900, startingHeight))
                .planetData(new VenusData())
                .build();

        Moon moon = mb
                .name("The Moon")
                .hasAtmosphere(false)
                .sizeSquare(0.5)
                .gravityPull(0.2)
                .image("moon")
                .planetData(new MoonData())
                .build();

        Planet earth = pb
                .name("Earth")
                .hasAtmosphere(true)
                .sizeSquare(5)
                .gravityPull(4)
                .position(new Coord(3000, startingHeight))
                .addMoon(moon)
                .planetData(new EarthData())
                .build();

        Planet mars = pb
                .name("Mars")
                .hasAtmosphere(true)
                .sizeSquare(2)
                .gravityPull(2)
                .position(new Coord(4000, startingHeight))
                .planetData(new MarsData())
                .build();

        Planet jupiter = pb
                .name("Jupiter")
                .hasAtmosphere(false)
                .gravityPull(4)
                .sizeSquare(12)
                .position(new Coord(5200, startingHeight))
                .planetData(new JupiterData())
                .build();

        Planet saturn = pb
                .name("Saturn")
                .hasAtmosphere(false)
                .gravityPull(4)
                .sizeRect(11, 8.5)
                .position(new Coord(7700, startingHeight))
                .planetData(new SaturnData())
                .build();

        Planet uranus = pb
                .name("Uranus")
                .hasAtmosphere(false)
                .gravityPull(4)
                .sizeSquare(6)
                .position(new Coord(10000, startingHeight))
                .planetData(new UranusData())
                .build();

        Planet neptune = pb
                .name("Neptune")
                .hasAtmosphere(false)
                .gravityPull(4)
                .sizeSquare(6)
                .position(new Coord(13500, startingHeight))
                .planetData(new NeptuneData())
                .build();

        gameObjects.add(sun);
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
        List<CelestialBody> bodies = gameObjectsByType(CelestialBody.class);

        return (CelestialBody) bodies.stream()
                .filter(body -> body.getName().equalsIgnoreCase(name))
                .map(CelestialBody.class::cast)
                .toArray()[0];
    }

    public List<GameObject> getGameObjects() {
        if (gameObjects.isEmpty()) {
            addPlanets(startingHeight);

            // For each of the planets, check and add the moons
            List<Planet> planets = gameObjectsByType(Planet.class);

            for (Planet planet : planets) {
                if (!planet.getMoons().isEmpty())
                    // Add moons
                    gameObjects.addAll(planet.getMoons());
            }
        }
        return gameObjects;
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

    public <T extends GameObject> List<T> gameObjectsByType(Class<T> type) {
        return gameObjects.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }
}
