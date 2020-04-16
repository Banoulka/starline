package Base.Planets;

import Abstracts.CelestialBody;
import Base.Coord;

public class Star extends CelestialBody {
    public Star(Coord startingCords, String name, double gravityPull, double size, boolean hasAtmosphere) {
        super(startingCords, name, gravityPull, size, hasAtmosphere);
    }
}
