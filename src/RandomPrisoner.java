import java.util.ArrayList;

public class RandomPrisoner extends Prisoner {

    private final double cooProbability;

    public RandomPrisoner(double cooProbability) {
        super();
        this.cooProbability = cooProbability;
    }

    @Override
    public boolean coopOrBetray(ArrayList<Boolean> oppPrevious) {
        boolean ret = Math.random() < cooProbability;
        previousResponses.add(ret);
        return ret;
    }
}
