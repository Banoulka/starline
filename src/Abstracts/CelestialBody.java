package Abstracts;

import Base.Coord;
import Base.Interfaces.Actions.IClickable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public abstract class CelestialBody extends GameObject implements IClickable {

    protected String name;
    protected double gravityPull;
    protected boolean hasAtmosphere;

    public CelestialBody(Coord startingCords, String name, double gravityPull, double size, boolean hasAtmosphere) {
        super(startingCords, size, new ImageView("/Resources/" + name.toUpperCase() + ".png"));
        this.name = name;
        this.gravityPull = gravityPull;
        this.hasAtmosphere = hasAtmosphere;
    }

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
}
