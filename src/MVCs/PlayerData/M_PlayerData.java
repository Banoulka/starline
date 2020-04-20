package MVCs.PlayerData;

import Abstracts.CelestialBody;
import Abstracts.Model;
import Base.Misc.PlayerGO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class M_PlayerData extends Model {

    private String playerName = "TestPlayer";
    private int playerMoney = 0;

    private CelestialBody currPlanet = null;

    private List<CelestialBody> knownBodies;

    private static M_PlayerData self = null;

    private PlayerGO playerGO;

    private M_PlayerData() {
        knownBodies = new ArrayList<>();
        playerGO = new PlayerGO();
        playerGO.setGoHeight(50);
        playerGO.setGoWidth(50);
        playerGO.setImg("rocket");
        playerGO.buildBoundingBox();
    }

    public CelestialBody getCurrPlanet() {
        return currPlanet;
    }

    public void setCurrPlanet(CelestialBody currPlanet) {
        this.currPlanet = currPlanet;
    }

    public void addKnownBody(CelestialBody body) {
        if (!isKnown(body))
            knownBodies.add(body);
    }

    public boolean isKnown(CelestialBody body) {
        return !knownBodies.stream()
                .filter(compare -> body.getName().equalsIgnoreCase(compare.getName()))
                .collect(Collectors.toList()).isEmpty();
    }

    public List<CelestialBody> getKnownBodies() {
        return knownBodies;
    }

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
        return "PlayerGO Data Model";
    }
}
