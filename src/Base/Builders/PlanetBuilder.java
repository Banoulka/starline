package Base.Builders;

import Base.Coord;
import Base.Planets.Planet;
import javafx.scene.image.ImageView;

public class PlanetBuilder {

    private Planet planet = new Planet();

    public PlanetBuilder name(String name) {
        planet.setName(name);
        return this;
    }

    public PlanetBuilder position(Coord coords) {
        planet.setPosition(coords);
        return this;
    }

    public PlanetBuilder gravityPull(double pull) {
        planet.setGravityPull(pull);
        return this;
    }

    public PlanetBuilder hasAtmosphere(boolean atmos) {
        planet.setHasAtmosphere(atmos);
        return this;
    }

    public PlanetBuilder sizeSquare(double size) {
        return this.sizeRect(size, size);
    }

    public PlanetBuilder sizeRect(double goWidth, double goHeight) {
        planet.setGoHeight(goHeight);
        planet.setGoWidth(goWidth);
        return this;
    }

    public PlanetBuilder image(String imageName) {
        planet.setImg(imageName);
        return this;
    }

    public Planet build() {
        // Build the bounding box for the planet
        planet.buildBoundingBox();

        // If the planet does not have an image
        // set the image to the planet name.
        if (planet.getImg() == null)
            planet.setImg(planet.getName());

        Planet planetToReturn = planet;

        // Reset the builder before returning
        clear();
        return planetToReturn;
    }

    private void clear() {
        planet = new Planet();
    }
}
