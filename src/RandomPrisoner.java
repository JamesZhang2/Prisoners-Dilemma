import java.util.ArrayList;

public class RandomPrisoner extends Prisoner {

    private final double probOfCoop;

    public RandomPrisoner(double probOfMistake, double probOfCoop) {
        super(probOfMistake);
        this.probOfCoop = probOfCoop;
    }

    @Override
    public boolean coopOrBetray(ArrayList<Boolean> oppHistory) {
        return makeMistake(Math.random() < probOfCoop);
    }

    @Override
    public Prisoner clone() {
        return new RandomPrisoner(probOfMistake, probOfCoop);
    }

    @Override
    public String getType() {
        return "Type: Random(" + probOfCoop + ")";
    }
}
