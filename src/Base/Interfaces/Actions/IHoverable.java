package Base.Interfaces.Actions;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public interface IHoverable extends IEvent {
    Node getNode();
    void onHover(MouseEvent mouseEvent);
    void onExit(MouseEvent mouseEvent);
}
