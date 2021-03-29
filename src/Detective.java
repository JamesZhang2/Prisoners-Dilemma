import java.util.ArrayList;

public class Detective extends Prisoner{
    private final boolean[] probe;
    private boolean oppHasBetrayed;

    public Detective(double probOfMistake, boolean[] probe) {
        super(probOfMistake);
        this.probe = probe;
    }

    @Override
    public boolean coopOrBetray(ArrayList<Boolean> oppHistory) {
        if (history.size() < probe.length) {
            return makeMistake(probe[history.size()]);
        } else if (history.size() == probe.length){
            oppHasBetrayed = !probe[0];
            for (int i = 1; i < probe.length; i++) {
                oppHasBetrayed = oppHasBetrayed || !probe[i];
            }
            // If opp has never betrayed, become a Cheater
            // If opp has betrayed, become a Copycat
            boolean ret = oppHasBetrayed ? oppHistory.get(oppHistory.size() - 1) : false;
            return makeMistake(ret);
        } else {
            boolean ret = oppHasBetrayed ? oppHistory.get(oppHistory.size() - 1) : false;
            return makeMistake(ret);
        }
    }

    @Override
    public Prisoner clone() {
        return new Detective(probOfMistake, probe);
    }

    public String toString() {
        String probeStr = "";
        for (int i = 0; i < probe.length - 1; i++) {
            probeStr = probeStr + probe[i] + ", ";
        }
        probeStr += probe[probe.length - 1];
        String str = "Type: Detective(" + probeStr + ")\n";
        str = str + "Total Score: " + score;
        return str;
    }
}
