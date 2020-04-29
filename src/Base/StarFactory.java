package Base;

import Base.Utility.Utils;
import javafx.animation.*;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarFactory {

    // Path to resources directory
    private final static File resourcesDir = new File("src/Resources/Min");

    // Filter to get all stars
    private final static FilenameFilter starFilter = (dir, name) -> name.startsWith("STAR");

    // Get the list of stars from the resource directory
    private final static File[] starList = resourcesDir.listFiles(starFilter);

    // Array list of images to use as star sprites
    private final static ArrayList<ImageView> starSprites = new ArrayList<ImageView>();

    private Random r = Utils.random;

    private int noOfStars = 30;
    private double parentWidth;
    private double parentHeight;

    private ArrayList<Star> stars;

    private StarType starType;

    public enum StarType {
        RANDOM,
        ANIMATE_LEFT
    }

    public StarFactory(double parentWidth, double parentHeight, StarType type) {

        this.starType = type;
        this.parentWidth = parentWidth - 50;
        this.parentHeight = parentHeight - 50;

        stars = new ArrayList<Star>();

        if (starSprites.isEmpty()) {

            // If the star list is empty or the images not found then
            // throw an error because something went wrong :(
            if (starList != null && resourcesDir.isDirectory()) {
                for (final File currFile : starList) {

                    // Get the new image as a javafx image from the URI
                    ImageView img = new ImageView(currFile.toURI().toString());
                    System.out.println("IStarBuilder Star Found :: " + currFile.getName());

                    // Add the image to the star list array
                    starSprites.add(img);
                }
            } else System.err.println("IStarBuilder :: No star images found");

        } else {
            System.out.println("IStarBuilder :: Sprites already loaded");
        }
    }

    public void setTranslateX(double x) {
        x /= 3;
        for (Star star : stars) {
            // Random noise
            star.getCurrImage().setTranslateX(x / star.getSize());
        }
    }


    public ArrayList<ImageView> buildStars() {

        ArrayList<ImageView> imageViews = new ArrayList<>();

        // For the number of stars set
        for (int i = 0; i < noOfStars; i++) {

            // Get a random image from the list of sprites and 'copy'
            // the new image
            ImageView currImage = new ImageView(starSprites.get(r.nextInt(starSprites.size())).getImage());

            // Add the new star to the list

            List<Class<? extends Star>> starTypes = new ArrayList<>();
            starTypes.add(StarOne.class);
            starTypes.add(StarTwo.class);
            // Add as many star classes

            // Get a random class to add
            Class<? extends Star> randomClass = starTypes.get(Utils.random.nextInt(starTypes.size()-1));

            Star starToAdd = null;

            // Try to instantiate the star class
            try {
                 starToAdd = randomClass
                        .getDeclaredConstructor(ImageView.class, double.class, double.class)
                        .newInstance(currImage, parentWidth, parentHeight);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // If it is successful, add it to the list
            if (starToAdd != null) {

                if (starType == StarType.ANIMATE_LEFT) {

                    ImageView starImage = starToAdd.getCurrImage();

                    // Add an animation to the stars
                    TranslateTransition tt = new TranslateTransition(Duration.millis((starToAdd.getSize() * starToAdd.getSize()) * 150), starImage);
                    tt.setFromX(starImage.getX() - parentWidth);
                    tt.setToX(parentWidth);
                    tt.setAutoReverse(false);
                    tt.setInterpolator(Interpolator.LINEAR);
                    tt.setCycleCount(Timeline.INDEFINITE);
                    tt.play();
                }

                stars.add(starToAdd);
                imageViews.add(starToAdd.getCurrImage());
            }

        }

        return imageViews;
    }

    public void setNoOfStars(int noOfStars) {
        this.noOfStars = noOfStars;
    }
}

abstract class Star {

    private ImageView currImage;
    private double size;

    public Star(ImageView currImage, int startSizeMax, float intensityWeight, double parentWidth, double parentHeight) {
        this.currImage = currImage;

        // Initial Random Values
        // Get a random size and opacity for the star
        Random r = Utils.random;
        this.size = r.nextInt(startSizeMax) + 1;
        double opacity = r.nextDouble() * intensityWeight;

        // Get a random position for the star
        double posX = r.nextDouble() * parentWidth;
        double posY = r.nextDouble() * parentHeight;

        // Get a random rotation
        double rotation = 360 * r.nextDouble();

        // and set the opacity
        currImage.setFitWidth(this.size);
        currImage.setFitHeight(this.size);
        currImage.setOpacity(opacity);

        // Set random rotation
        currImage.setRotate(rotation);

        // Pick a random position for the star based on the spread
        currImage.setX(posX);
        currImage.setY(posY);


        // Setup the timeline animation
        final Timeline rotateTimeline = new Timeline();
        rotateTimeline.setCycleCount(Timeline.INDEFINITE);
        rotateTimeline.setAutoReverse(false); // Rotate never switches

        final Timeline otherTimeline = new Timeline();
        otherTimeline.setCycleCount(Timeline.INDEFINITE);
        otherTimeline.setAutoReverse(true); // Pulse like animation


        // Get random anim values
        int rotateChange = r.nextDouble() > 0.5 ? +360 : -360; // Rotate Left or Right
        double speed = 15000 + (80000 - 15000) * r.nextDouble(); // Seconds between 15 - 80
        double opacityChange = r.nextFloat(); // Opacity between 0 - 1
        double scaleChange = currImage.getScaleX() // Scale between original and original+2
                + ((currImage.getScaleX()+2) - currImage.getScaleX()) * r.nextDouble();

        // Rotate timeline (slower)
        final KeyFrame rotateKF = new KeyFrame(
                Duration.millis(speed),
                new KeyValue(currImage.rotateProperty(), rotateChange)
        );

        // Other keyframes, opacity and scale (faster)
        final KeyFrame otherKF = new KeyFrame(
                Duration.millis(speed / 4), // less speed /4
                new KeyValue(currImage.opacityProperty(), opacityChange),
                new KeyValue(currImage.scaleXProperty(), scaleChange),
                new KeyValue(currImage.scaleYProperty(), scaleChange)
        );

        // Add and start the animations
        rotateTimeline.getKeyFrames().add(rotateKF);
        rotateTimeline.play();

        otherTimeline.getKeyFrames().add(otherKF);
        otherTimeline.play();
    }

    public ImageView getCurrImage() {
        return currImage;
    }

    public double getSize() {
        return size;
    }
}

class StarOne extends Star {
    public StarOne(ImageView imageView, double parentWidth, double parentHeight) {
        super(imageView, 15, 0.75f, parentWidth, parentHeight);
    }
}

class StarTwo extends Star {
    public StarTwo(ImageView imageView, double parentWidth, double parentHeight) {
        super(imageView, 30, 1.5f, parentWidth, parentHeight);
    }
}