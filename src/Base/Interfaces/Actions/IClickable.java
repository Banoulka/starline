package Base.Interfaces.Actions;

import javafx.scene.input.MouseEvent;

public interface IClickable extends IHoverable {

    void doClick(MouseEvent mouseEvent);
}
