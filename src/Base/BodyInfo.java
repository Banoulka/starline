package Base;

import Abstracts.CelestialBody;

public class BodyInfo {
    private CelestialBody parent;
    private boolean isKnown;

    public BodyInfo(CelestialBody parent) {
        this.parent = parent;
        isKnown = false;
    }

    public void setKnown(boolean known) {
        isKnown = known;
    }

    public String getInfo() {
        return isKnown ? parent.toString() : "Unknown Body";
    }

    public boolean isKnown() {
        return isKnown;
    }
}
