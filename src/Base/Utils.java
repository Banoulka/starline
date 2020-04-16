package Base;

import java.util.Random;

public class Utils {
    // Random util with the system time as seed
    public final static Random random = new Random(System.currentTimeMillis());
}
