package Base.Planets;

import Abstracts.CelestialBody;
import Base.Coord;
import Base.Interfaces.Actions.IPannable;
import Base.Interfaces.Actions.ITooltip;

public class Star extends CelestialBody implements IPannable, ITooltip {
    public Star(Coord startingCords, String name, double gravityPull, double size, boolean hasAtmosphere) {
        super(startingCords, name, gravityPull, size, hasAtmosphere);
    }
}
