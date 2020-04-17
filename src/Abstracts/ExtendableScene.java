package Abstracts;

import Base.Config;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public abstract class ExtendableScene extends Scene {

    protected Pane parent;

    protected ExtendableScene(Pane parent) {
        // Base details for every scene including the root styles
        // the size of the window etc.
        super(parent , Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);

        // Assign Local Variable
        this.parent = parent;

        // Add all the style sheets etc.
        this.getStylesheets().add("Styles/root-styles.css");
        this.getStylesheets().add("Styles/button-styles.css");

        // Prints out whichever scene is loading
        System.out.println(this.getClass() + " :: Loading...");
    }
}
