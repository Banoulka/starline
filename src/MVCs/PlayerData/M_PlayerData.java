package MVCs.PlayerData;

import Abstracts.CelestialBody;
import Abstracts.Model;
import Base.Builders.PlayerBuilder;
import Base.Coord;
import Base.GameObjects.PlayerGO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class M_PlayerData extends Model {

    // Player stats
    private String playerName = "TestPlayer";
    private int playerMoney = 0;
    private CelestialBody currPlanet = null;
    private int fuelLevel = 1;

    // Game Object
    private PlayerGO playerGO;

    // Body Info
    private List<CelestialBody> knownBodies;

    // Singleton pattern
    private static M_PlayerData self = null;

    private M_PlayerData() {
        knownBodies = new ArrayList<>();

        PlayerBuilder playerBuilder = new PlayerBuilder();

        // Build the new player object
        playerGO = playerBuilder
                .sizeRect(0.5, 1)
                .position(new Coord(0, 0))
                .image("rocket")
                .playerDataReference(self)
                .build();
    }

    public PlayerGO getPlayerGO() {
        return playerGO;
    }

    public CelestialBody getCurrPlanet() {
        return currPlanet;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public void increaseFuelLevel() {
        fuelLevel++;
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
