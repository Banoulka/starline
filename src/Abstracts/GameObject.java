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
    protected double goHeight;
    protected double goWidth;

    protected static GraphicsContext gc;

    protected BoundingBox boundingBox;

    public GameObject(Coord startingCords, double size, ImageView img) {
        this.goWidth = size * Config.SCALE_MODIFIER;
        this.goHeight = this.goWidth;

        // Calculate the new coords based on size
        this.position = new Coord(startingCords.x, startingCords.y - (this.goWidth / 2));

        // Draw the bounding box
        this.boundingBox = new BoundingBox(position.x, position.y, this.goWidth, this.goHeight);

        if (img != null) {
            this.img = img;
            this.getChildren().add(img);
        }
        else
            System.err.println(getClass().getName() + " image not found");

        this.update();
    }

    public GameObject(){}

    public void update() {
        this.setLayoutX(position.x);
        this.setLayoutY(position.y);
        this.setPrefSize(goWidth, goHeight);

        if (img != null) {
            img.setFitHeight(goHeight);
            img.setFitWidth(goWidth);
        }

        if (Config.DEBUG && gc != null) {
            gc.setStroke(Color.RED);

            // Draw bounding box over the boundaries
            gc.strokeRect(position.x, position.y, goWidth, goHeight);

            // Draw circle in posX and posY of the game object
            gc.setFill(Color.BLUE);
            gc.fillOval(position.x - 15, position.y - 15, 30, 30);

            // Draw circle in center of gameObject
            gc.setFill(Color.GREEN);
            gc.fillOval(getCenter().x - 15, getCenter().y - 15, 30, 30);
        }
    }

    public Coord getCenter() {
        return new Coord(position.x + (goWidth / 2), position.y + (goHeight / 2));
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

    public void setImg(String imageName) {
        this.img = new ImageView("/Resources/" + imageName.toUpperCase() + ".png");
        this.img.setFitWidth(goWidth);
        this.img.setFitHeight(goHeight);
        this.getChildren().add(this.img);
    }

    public void setPosition(Coord position) {
        this.position = new Coord(position.x, position.y - (this.goHeight / 2));
        this.setLayoutX(this.position.x);
        this.setLayoutY(this.position.y);
    }

    public void setGoHeight(double goHeight) {
        this.goHeight = goHeight * Config.SCALE_MODIFIER;
    }

    public void setGoWidth(double goWidth) {
        this.goWidth = goWidth * Config.SCALE_MODIFIER;
    }

    public void buildBoundingBox() {
        this.boundingBox = new BoundingBox(position.x, position.y, this.goWidth, this.goHeight);
    }
}
