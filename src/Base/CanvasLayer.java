package Base;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class CanvasLayer extends Pane {

    private static CanvasLayer instance;

    public static CanvasLayer getInstance() {
        return instance;
    }

    public static CanvasLayer setCanvas(Canvas canvas) {
        instance = new CanvasLayer();
        instance.getChildren().add(canvas);
        return instance;
    }
}
