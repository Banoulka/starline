package Base.Scenes;

import Abstracts.CelestialBody;
import Base.Builders.PlanetBuilder;
import Base.Interfaces.IRunAfter;
import Base.PlanetData.UranusData;
import Base.Utility.Config;
import Base.Utility.Coord;
import MVCs.PlayerData.C_PlayerData;
import MVCs.ViewScene.C_ViewScene;
import javafx.scene.layout.BorderPane;

public class ViewScene extends BorderPane implements IRunAfter {

    private C_PlayerData playerData;
    private C_ViewScene controller;

    public ViewScene(CelestialBody body) {
        super();

        playerData = new C_PlayerData(this);

        if (body == null && Config.DEBUG) {
            // We are debugging so create a new body to 'visit'
            PlanetBuilder pb = new PlanetBuilder();

            body = pb
                    .name("Uranus")
                    .hasAtmosphere(false)
                    .gravityPull(4)
                    .sizeSquare(6)
                    .position(new Coord(3200, 460))
                    .planetData(new UranusData())
                    .build();
        }

        controller = new C_ViewScene(this, body);
    }

    @Override
    public void runAfter() {
        controller.runAfter();
    }
}
