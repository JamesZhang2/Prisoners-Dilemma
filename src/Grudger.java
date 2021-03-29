import java.util.ArrayList;

public class Grudger extends Prisoner {

    private boolean opponentBetrayed = false;

    public Grudger(double probOfMistake) {
        super(probOfMistake);
    }

    @Override
    public boolean coopOrBetray(ArrayList<Boolean> oppHistory) {
        if (oppHistory.size() != 0 && !oppHistory.get(oppHistory.size() - 1))
            opponentBetrayed = true;
        return makeMistake(!opponentBetrayed);
    }

    @Override
    public Prisoner clone() {
        return new Grudger(probOfMistake);
    }

    public String toString() {
        String str = "Type: Grudger\n";
        str = str + "Total Score: " + score;
        return str;
    }
}
