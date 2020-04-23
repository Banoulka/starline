package Base.GameObjects;

import Abstracts.CelestialBody;
import Abstracts.GameObject;
import MVCs.PlayerData.M_PlayerData;

public class PlayerGO extends GameObject {


    private M_PlayerData playerData;

    public void setPlayerData(M_PlayerData playerData) {
        this.playerData = playerData;
    }

    public CelestialBody getCurrPlanet() {
        return playerData.getCurrPlanet();
    }

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
