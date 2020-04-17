package MVCs.VisitScene;

import Abstracts.CelestialBody;
import Abstracts.Model;

public class M_VisitScene extends Model {

    private CelestialBody currentlyVisiting;

    public M_VisitScene(CelestialBody currentlyVisiting) {
        this.currentlyVisiting = currentlyVisiting;
    }

    public CelestialBody getCurrentlyVisiting() {
        return currentlyVisiting;
    }
}
