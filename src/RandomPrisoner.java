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

    public String toString() {
        String str = "Type: Random(" + probOfCoop + ")\n";
        str = str + "Total Score: " + score;
        return str;
    }
}
