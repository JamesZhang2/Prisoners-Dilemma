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
            if (history.size() == 0)
                oppHasBetrayed = false;
            else
                oppHasBetrayed = oppHasBetrayed || !oppHistory.get(oppHistory.size() - 1);
            return makeMistake(probe[history.size()]);
        } else if (history.size() == probe.length){
            oppHasBetrayed = oppHasBetrayed || !oppHistory.get(oppHistory.size() - 1);
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

    @Override
    public String getType() {
        String probeStr = "";
        for (int i = 0; i < probe.length - 1; i++) {
            probeStr = probeStr + probe[i] + ", ";
        }
        probeStr += probe[probe.length - 1];
        return "Type: Detective(" + probeStr + ")";
    }
}
