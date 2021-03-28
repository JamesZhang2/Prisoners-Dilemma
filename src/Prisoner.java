import java.util.ArrayList;

public abstract class Prisoner {

    protected ArrayList<Boolean> previousResponses;

    public abstract boolean coopOrBetray(ArrayList<Boolean> oppPrevious);

    public Prisoner() {
        previousResponses = new ArrayList<>();
    }

    public ArrayList<Boolean> getPrevious() {
        return previousResponses;
    }
}
