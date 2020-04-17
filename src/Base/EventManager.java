package Base;

import Base.Interfaces.Actions.IClickable;
import Base.Interfaces.Actions.IHoverable;

public class EventManager {

    public static void setupHover(IHoverable hoverableWithObject) {
        hoverableWithObject.getNode().setOnMouseEntered(hoverableWithObject::onHover);
        hoverableWithObject.getNode().setOnMouseExited(hoverableWithObject::onExit);
    }

    public static void setupClickable(IClickable clickable) {
        clickable.getNode().setOnMouseReleased(mouseEvent -> {
            if (clickable.getNode().isHover()) clickable.doClick(mouseEvent);
        });
    }
}
