package Abstracts;

import javafx.scene.layout.Pane;

public abstract class Controller {

    // Base model and views to override
    protected Model model;
    protected View view;

    // Needs public controller to make the pane with a root argument
    public Controller(Pane root){}

    // Base constructor with no arguments hidden to public
    protected Controller() {}

    // All controllers must have a get for model and view
    // these can return null if no model or view is attached
    public abstract Model model();
    public abstract View view();
}
