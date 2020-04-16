package Abstracts;

import Base.Config;
import Base.Coord;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public abstract class GameObject extends Region {

    private ImageView img;
    protected Coord position;
    protected double size;

    protected static GraphicsContext gc;

    protected final BoundingBox boundingBox;

    public GameObject(Coord startingCords, double size, ImageView img) {
        this.size = size * Config.SCALE_MODIFIER;

        // Calculate the new coords based on size
        this.position = new Coord(startingCords.x, startingCords.y - (this.size / 2));

        // Draw the bounding box
        this.boundingBox = new BoundingBox(position.x, position.y, this.size, this.size);

        if (img != null) {
            this.img = img;
            this.getChildren().add(img);
        }
        else
            System.err.println(getClass().getName() + " image not found");

        this.update();
    }

    public void update() {
        this.setLayoutX(position.x);
        this.setLayoutY(position.y);
        this.setPrefSize(size, size);

        if (img != null) {
            img.setFitHeight(size);
            img.setFitWidth(size);
        }


        if (Config.DEBUG) {
            gc.setStroke(Color.RED);

            // Draw bounding box over the boundaries
            gc.strokeRect(position.x, position.y, size, size);

            // Draw circle in posX and posY of the game object
            gc.setFill(Color.BLUE);
            gc.fillOval(position.x - 15, position.y - 15, 30, 30);

            // Draw circle in center of gameObject
            gc.setFill(Color.GREEN);
            gc.fillOval(getCenter().x - 15, getCenter().y - 15, 30, 30);
        }
    }

    public Coord getCenter() {
        return new Coord(position.x + (size / 2), position.y + (size / 2));
    }

    public static void setGraphicsContext(GraphicsContext gc) {
        GameObject.gc = gc;
    }

    public Coord getPosition() {
        return position;
    }

    public ImageView getImg() {
        return img;
    }
}
