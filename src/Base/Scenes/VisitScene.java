package Base.Scenes;

import Abstracts.CelestialBody;
import Base.Builders.PlanetBuilder;
import Base.Config;
import Base.Coord;
import Base.Interfaces.IRunAfter;
import MVCs.VisitScene.C_VisitScene;
import javafx.scene.layout.BorderPane;

public class VisitScene extends BorderPane implements IRunAfter {

    private C_VisitScene visitScene;

    public VisitScene(CelestialBody body) {
        super();

        if (body == null && Config.DEBUG) {
            // We are debugging so create a new body to 'visit'
            PlanetBuilder pb = new PlanetBuilder();

            body = pb
                    .name("Uranus")
                    .hasAtmosphere(false)
                    .gravityPull(4)
                    .sizeSquare(6)
                    .position(new Coord(10000, 460))
                    .build();
        }

        visitScene = new C_VisitScene(this, body);
    }

    @Override
    public void runAfter() {
        visitScene.runAfter();
    }
}
