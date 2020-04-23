package Base.Builders;

import Base.GameObjects.Planets.Star;

public class StarBuilder extends
    CelestialBodyBuilder<Star, StarBuilder> {

    Star star = new Star();

    @Override
    protected Star getObj() { return star; }

    @Override
    protected StarBuilder self() { return this; }

    @Override
    public Star build() {
        return star;
    }

    @Override
    public void clear() {
        star = new Star();
    }
}
