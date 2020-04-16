package Base;

public class Coord {
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
}
