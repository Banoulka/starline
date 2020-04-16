package Base.Planets;

import Abstracts.CelestialBody;
import Base.Coord;
import Base.Interfaces.Actions.IDraggable;

import java.util.ArrayList;

public class Planet extends CelestialBody implements IDraggable {

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
