package Abstracts;

import Base.Coord;
import Base.Interfaces.Actions.IClickable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public abstract class CelestialBody extends GameObject implements IClickable {

    protected final String name;
    protected final double gravityPull;
    protected final boolean hasAtmosphere;

    public CelestialBody(Coord startingCords, String name, double gravityPull, double size, boolean hasAtmosphere) {
        super(startingCords, size, new ImageView("/Resources/" + name.toUpperCase() + ".png"));
        this.name = name;
        this.gravityPull = gravityPull;
        this.hasAtmosphere = hasAtmosphere;
    }

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
    public void doClick() {
        System.out.println(getClass().getName() + " clicked!!!");
    }

    @Override
    public Node getNode() {
        return this.getImg();
    }
}
