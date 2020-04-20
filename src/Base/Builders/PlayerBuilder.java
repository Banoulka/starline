package Base.Builders;

import Base.Misc.PlayerGO;
import MVCs.PlayerData.M_PlayerData;

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

    public PlayerBuilder playerDataReference(M_PlayerData ref) {
        playerGO.setPlayerData(ref);
        return self();
    }

    @Override
    public void clear() {

    }

    @Override
    public PlayerGO build() {
        playerGO.buildBoundingBox();

        return playerGO;
    }
}
