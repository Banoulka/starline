package Base.Utility;

import java.text.NumberFormat;
import java.util.Random;

public class Utils {
    // Random util with the system time as seed
    public final static Random random = new Random(System.currentTimeMillis());
    public final static NumberFormat numberFormatter = NumberFormat.getCurrencyInstance();

    public static double strToNum(String s) {
        return Double.parseDouble(s.replaceAll("[^\\.0123456789]",""));
    }
}
