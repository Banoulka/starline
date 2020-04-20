package Base.Builders;

import Base.Planets.Moon;
import Base.Planets.Planet;

public class PlanetBuilder extends
        CelestialBodyBuilder<Planet, PlanetBuilder> {

    private Planet planet = new Planet();

    @Override
    protected Planet getObj() { return planet; }

    @Override
    protected PlanetBuilder self() { return this; }

    public PlanetBuilder addMoon(Moon moon) {
        planet.addMoon(moon);
        return self();
    }

    public Planet build() {
        planet.buildBoundingBox();
        planet.buildOrbitPath();

        if (planet.getImg() == null)
            planet.setImg(planet.getName());

        Planet planetToReturn = planet;
        clear();

        return planetToReturn;
    }

    public void clear() {
        planet = new Planet();
    }
}
