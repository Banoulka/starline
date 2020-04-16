package Abstracts;

import javafx.scene.layout.Pane;

public abstract class View {
    Pane root;
    Model model;

    public View(Pane root) {
        this.root = root;
        this.model = null;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public abstract void updateView();

}
