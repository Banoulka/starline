package Base;

import Base.Interfaces.Actions.IClickable;
import Base.Interfaces.Actions.IHoverable;

public class EventManager {

    public static void setupHover(IHoverable hoverableWithObject) {
        hoverableWithObject.getNode().setOnMouseEntered(hoverableWithObject::onHover);
        hoverableWithObject.getNode().setOnMouseExited(hoverableWithObject::onExit);
    }

    public static void setupClickable(IClickable clickable) {
        clickable.getNode().setOnMousePressed(mouseEvent -> {
            if (clickable.getNode().isHover() &&
                            mouseEvent.isPrimaryButtonDown())
                clickable.doClick(mouseEvent);
        });
    }
}
