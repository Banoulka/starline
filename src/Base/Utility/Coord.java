package Base.Utility;

public class Coord implements Cloneable {
    public double x, y;

    public Coord() {
        x = 0;
        y = 0;
    }

    public Coord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "X: " + x + " :: Y: " + y;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Coord cloned = (Coord) super.clone();
        return cloned;
    }
}
