package Base.GameObjects;

import Abstracts.GameObject;

public class PlayerGO extends GameObject {

    @Override
    public void startAnimation() {

    }

    @Override
    public String toString() {
        return "PlayerGO{" +
                "position=" + position +
                ", goHeight=" + goHeight +
                ", goWidth=" + goWidth +
                ", boundingBox=" + boundingBox +
                '}';
    }
}
