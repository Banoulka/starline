package Base.Builders;

import Abstracts.CelestialBody;

public abstract class CelestialBodyBuilder
        <O extends CelestialBody, SELF extends CelestialBodyBuilder<O, SELF>>
    extends GameObjectBuilder<O, SELF> {

    protected abstract O getObj();
    protected abstract SELF self();

    public SELF name(String name) {
        getObj().setName(name);
        return self();
    }

    public SELF gravityPull(double pull) {
        getObj().setGravityPull(pull);
        return self();
    }

    public SELF hasAtmosphere(boolean hasAtmosphere) {
        getObj().setHasAtmosphere(hasAtmosphere);
        return self();
    }
}
