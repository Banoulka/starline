package Base;

public class Config {

    // Window Values
    public static final int WINDOW_HEIGHT = 1080;
    public static final int WINDOW_WIDTH = 1920;

    // Memory leaks somewhere slowing the game down
    // Dragging canvas layer is bad news apparently
    public static final boolean DEBUG = false;

    public static final boolean IS_RESIZABLE = false;

    public static double SCALE_MODIFIER = 65;
    public static final double PLAY_AREA_SIZE = 14000;
}
