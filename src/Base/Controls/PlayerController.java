package Base.Controls;

import Base.Builders.PlayerBuilder;
import Base.GameObjects.PlayerGO;
import Base.Utility.Coord;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PlayerController {

    private static PlayerController instance;

    // Player Builder
    private PlayerBuilder playerBuilder = new PlayerBuilder();

    // Local coord for player movement
    private Coord moveBy = new Coord(0,0);

    private PlayerGO currPlayerInstance;

    public static PlayerController get() {
        if (instance == null)
            instance = new PlayerController();

        return instance;
    }

    public void movePlayer(double boundWidth, double boundHeight) {
        // Check position against screen bounds
        double playerX = currPlayerInstance.getPosition().x;
        double playerY = currPlayerInstance.getPosition().y;

        // Move player within pane bounds
        if (playerX < boundWidth && playerX > 0)
            currPlayerInstance.moveX(moveBy.x);

        if (playerY < boundHeight && playerY > 0)
            currPlayerInstance.moveY(moveBy.y);
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.W)
            moveBy.y = Math.signum(moveBy.y - 1);
        if (keyEvent.getCode() == KeyCode.S)
            moveBy.y = Math.signum(moveBy.y + 1);

        if (keyEvent.getCode() == KeyCode.D)
            moveBy.x = Math.signum(moveBy.x + 1);
        if (keyEvent.getCode() == KeyCode.A)
            moveBy.x = Math.signum(moveBy.x - 1);
    }

    public void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.W)
            moveBy.y = Math.signum(moveBy.y + 1);
        if (keyEvent.getCode() == KeyCode.S)
            moveBy.y = Math.signum(moveBy.y - 1);

        if (keyEvent.getCode() == KeyCode.D)
            moveBy.x = Math.signum(moveBy.x - 1);
        if (keyEvent.getCode() == KeyCode.A)
            moveBy.x = Math.signum(moveBy.x + 1);
    }

    public Coord getMoveBy() {
        return moveBy;
    }

    public PlayerGO getPlayerForPlayScene() {
        currPlayerInstance = playerBuilder
                .sizeRect(0.5, 1)
                .position(new Coord(0, 0))
                .setRotate(0)
                .image("rocket")
                .build();
        return currPlayerInstance;
    }

    public PlayerGO getPlayerForVisitScene() {
        currPlayerInstance = playerBuilder
                .sizeRect(2, 4)
                .position(new Coord(0, 0))
                .setRotate(-90)
                .image("rocket")
                .build();
        return currPlayerInstance;
    }
}
