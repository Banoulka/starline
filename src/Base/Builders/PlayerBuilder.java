package Base.Builders;

import Base.GameObjects.PlayerGO;

public class PlayerBuilder extends GameObjectBuilder<PlayerGO, PlayerBuilder> {

    PlayerGO playerGO = new PlayerGO();

    @Override
    protected PlayerGO getObj() {
        return playerGO;
    }

    @Override
    protected PlayerBuilder self() {
        return this;
    }

    public PlayerBuilder setRotate(double rotate) {
        playerGO.setRotate(rotate);
        return this;
    }

    private void updatePlayer() {
        playerGO.update();
    }

    @Override
    public void clear() {
        playerGO = new PlayerGO();
    }

    @Override
    public PlayerGO build() {
        playerGO.buildBoundingBox();
        playerGO.update();

        PlayerGO playerToReturn = playerGO;
        clear();

        return playerToReturn;
    }
}
