package Base.Builders;

import Base.GameObjects.Planets.Moon;

public class MoonBuilder extends
    CelestialBodyBuilder<Moon, MoonBuilder> {

    Moon moon = new Moon();

    @Override
    protected Moon getObj() {
        return moon;
    }

    @Override
    protected MoonBuilder self() {
        return this;
    }

    @Override
    public void clear() {
        moon = new Moon();
    }

    @Override
    public Moon build() {
        Moon moonToBuild = moon;

        if (moonToBuild.getImg() == null)
            moonToBuild.setImg(moonToBuild.getName());

        clear();
        return moonToBuild;
    }
}
