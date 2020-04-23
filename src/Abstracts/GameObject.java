package Abstracts;

import Base.Config;
import Base.Coord;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class GameObject extends Region {

    private ImageView img;
    protected Coord position;
    protected double goHeight;
    protected double goWidth;

    protected static GraphicsContext gc;

    private Canvas canvas;

    protected BoundingBox boundingBox;

    public GameObject(){}

    public void update() {
        this.setLayoutX(position.x);
        this.setLayoutY(position.y);
        this.setPrefSize(goWidth, goHeight);
        this.setWidth(goWidth);
        this.setHeight(goHeight);
        this.boundingBox = new BoundingBox(position.x, position.y, this.goWidth, this.goHeight);

        if (img != null) {
            img.setFitHeight(goHeight);
            img.setFitWidth(goWidth);
            img.setScaleX(this.getScaleX());
            img.setScaleY(this.getScaleY());
        }

        if (Config.DEBUG) {
            Rectangle debugRect = new Rectangle();
            debugRect.setFill(null);
            debugRect.setStroke(Color.RED);
            debugRect.setWidth(goWidth);
            debugRect.setHeight(goHeight);

            // Add debug rect to this
            this.getChildren().add(debugRect);
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

    public double getGoHeight() {
        return goHeight;
    }

    public double getGoWidth() {
        return goWidth;
    }

    public void moveX(double x) {
        this.position.x += x;
    }

    public void moveY(double y) {
        this.position.y += y;
    }

    public void setImg(String imageName) {
        if (imageName != null) {
            try {
                this.img = new ImageView("/Resources/Min/" + imageName.toUpperCase() + ".png");
                this.img.setFitWidth(goWidth);
                this.img.setFitHeight(goHeight);
                this.getChildren().add(this.img);
            } catch (IllegalArgumentException e) {
                System.err.println("Resource not found: " + imageName.toUpperCase() + ".png");
                this.img = null;
            }
        }
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

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public abstract void startAnimation();
}
