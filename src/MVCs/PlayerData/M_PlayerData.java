package MVCs.PlayerData;

import Abstracts.CelestialBody;
import Abstracts.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class M_PlayerData extends Model {

    // Player stats
    private String playerName = "TestPlayer";
    private double playerMoney = 10;
    private CelestialBody currPlanet = null;
    private int fuelLevel = 3;
    private int health = 100;

    // Body Info
    private List<CelestialBody> knownBodies;

    // Singleton pattern
    private static M_PlayerData self = null;

    private M_PlayerData() {
        knownBodies = new ArrayList<>();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public double getPlayerMoney() {
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
