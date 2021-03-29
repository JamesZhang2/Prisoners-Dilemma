import java.util.ArrayList;

/**
 * The RandomPrisoner randomly chooses to cooperate or betray,
 * with its probability of cooperating stored as a private instance variable.
 *
 * Special RandomPrisoners:
 * RandomPrisoner(0.0) is called a Cheater.
 * RandomPrisoner(1.0) is called a Cooperator.
 */
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
        if (probOfCoop == 0.0)
            return "Cheater";
        else if (probOfCoop == 1.0)
            return "Cooperator";
        else
            return "Type: Random(" + probOfCoop + ")";
    }
}
