package Abstracts;

import Base.Utility.Config;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class ExtendableScene extends Scene {

    public ExtendableScene() {
        // Base details for every scene including the root styles
        // the size of the window etc.
        super(new BorderPane(), Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);

        // Add all the style sheets etc.
        this.getStylesheets().add("Styles/root-styles.css");
        this.getStylesheets().add("Styles/button-styles.css");

        // Prints out whichever scene is loading
        System.out.println(this.getClass() + " :: Loading...");
    }
}

