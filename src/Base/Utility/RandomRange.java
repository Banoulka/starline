package Base.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomRange {

    private List<Double> numberRanges;

    public RandomRange() {
        numberRanges = new ArrayList<>();
    }

    public RandomRange addRange(double min, double max) {
        for (double i = min; i <= max; i ++ )
            this.numberRanges.add(i);
        return this;
    }

    public double getRandomNumber() {
        return numberRanges.get(new Random().nextInt(numberRanges.size()));
    }
}
