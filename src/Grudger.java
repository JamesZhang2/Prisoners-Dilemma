import java.util.ArrayList;

public class Grudger extends Prisoner {

    private boolean opponentBetrayed = false;

    public Grudger() {
        super();
    }

    @Override
    public boolean coopOrBetray(ArrayList<Boolean> oppPrevious) {
        boolean ret = !opponentBetrayed;
        if (oppPrevious.size() != 0 && !oppPrevious.get(oppPrevious.size() - 1))
            opponentBetrayed = true;
        previousResponses.add(ret);
        return ret;
    }
}
