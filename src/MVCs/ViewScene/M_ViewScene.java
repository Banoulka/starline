package MVCs.ViewScene;

import Abstracts.CelestialBody;
import Abstracts.Model;

public class M_ViewScene extends Model {
    private CelestialBody currViewing;

    public M_ViewScene(CelestialBody currViewing) {
        try {
            this.currViewing = (CelestialBody) currViewing.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public CelestialBody getCurrViewing() {
        return currViewing;
    }
}
