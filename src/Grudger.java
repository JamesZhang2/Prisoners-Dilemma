import java.util.ArrayList;

/**
 * The Grudger cooperates at first, but if its opponent betrays,
 * it will always betray thereafter.
 */
public class Grudger extends Prisoner {

    private boolean opponentBetrayed;

    public Grudger(double probOfMistake) {
        super(probOfMistake);
    }

    @Override
    public boolean coopOrBetray(ArrayList<Boolean> oppHistory) {
        if (oppHistory.size() == 0)
            opponentBetrayed = false;
        if (oppHistory.size() != 0 && !oppHistory.get(oppHistory.size() - 1))
            opponentBetrayed = true;
        return makeMistake(!opponentBetrayed);
    }

    @Override
    public Prisoner clone() {
        return new Grudger(probOfMistake);
    }

    @Override
    public String getType() {
        return "Type: Grudger";
    }
}
