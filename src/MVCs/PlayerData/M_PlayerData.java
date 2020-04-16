package MVCs.PlayerData;

import Abstracts.Model;

public class M_PlayerData extends Model {

    private String playerName = "TestPlayer";
    private int playerMoney = 0;

    private static M_PlayerData self = null;

    private M_PlayerData() {}

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
       this.playerName = playerName;
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public void setPlayerMoney(int playerMoney) {
        this.playerMoney = playerMoney;
    }

    public static M_PlayerData getInstance() {
        if (self == null)
            self = new M_PlayerData();

        return self;
    }

    @Override
    public String toString() {
        return "Player Data Model";
    }
}
