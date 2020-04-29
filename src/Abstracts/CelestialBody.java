package Abstracts;

import Base.Interfaces.Actions.IClickable;
import Base.Utility.Coord;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;

public abstract class CelestialBody extends GameObject implements IClickable, Cloneable {

    protected String name;
    protected double gravityPull;
    protected boolean hasAtmosphere;
    protected PlanetData planetData;

    public CelestialBody() {}

    @Override
    public void onHover(MouseEvent mouseEvent) {
        // Colour adjust to make the image brighter
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.3);

        this.getImg().setEffect(colorAdjust);
        this.setCursor(Cursor.HAND);
    }

    @Override
    public void onExit(MouseEvent mouseEvent) {
        this.setCursor(Cursor.DEFAULT);
        this.getImg().setEffect(null);
    }

    @Override
    public Node getNode() {
        return this.getImg();
    }

    @Override
    public void doClick(MouseEvent mouseEvent) {

    }

    public String getName() {
        return name;
    }

    public double getGravityPull() {
        return gravityPull;
    }

    public boolean isHasAtmosphere() {
        return hasAtmosphere;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGravityPull(double gravityPull) {
        this.gravityPull = gravityPull;
    }

    public void setHasAtmosphere(boolean hasAtmosphere) {
        this.hasAtmosphere = hasAtmosphere;
    }

    public void setPlanetData(PlanetData planetData) {
        this.planetData = planetData;
    }

    public PlanetData getPlanetData() {
        return planetData;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        CelestialBody newBody = (CelestialBody) super.clone();
        newBody.setPosition((Coord) newBody.getPosition().clone());
        return super.clone();
    }

    @Override
    public String toString() {
        return "CelestialBody{" +
                "name='" + name + '\'' +
                ", gravityPull=" + gravityPull +
                ", hasAtmosphere=" + hasAtmosphere +
                ", position=" + position +
                ", goHeight=" + goHeight +
                ", goWidth=" + goWidth +
                '}';
    }
}
