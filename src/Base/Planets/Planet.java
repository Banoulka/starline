package Base.Planets;

import Abstracts.CelestialBody;
import Base.Coord;
import Base.Interfaces.Actions.IPannable;
import Base.Interfaces.Actions.IVisitable;
import javafx.scene.Node;

import java.util.ArrayList;

public class Planet extends CelestialBody
        implements IPannable, IVisitable {

    private ArrayList<Moon> moons;

    public Planet(Coord startingCords, String name, double gravityPull, double size, boolean hasAtmosphere) {
        super(startingCords, name, gravityPull, size, hasAtmosphere);

        this.moons = new ArrayList<Moon>();
    }

    public void addMoon(Moon moon) {
        moons.add(moon);
    }

    public ArrayList<Moon> getMoons() {
        return moons;
    }
}
