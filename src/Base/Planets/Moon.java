package Base.Planets;

import Abstracts.CelestialBody;
import Base.Coord;

public class Moon extends CelestialBody {

    Planet parent;

    public Moon(Coord startingCords, String name, double gravityPull, double size, boolean hasAtmosphere, Planet parentPlanet) {
        super(startingCords, name, gravityPull, size, hasAtmosphere);
        this.parent = parentPlanet;
    }
}
