import java.util.ArrayList;

public class CopyPrisoner extends Prisoner{
    private LogicGate gate;
    private int memory;
    private boolean opponentBetrayed = false;

    public CopyPrisoner(double probOfMistake, LogicGate gate, int memory) {
        super(probOfMistake);
        this.gate = gate;
        this.memory = memory;
    }

    @Override
    public boolean coopOrBetray(ArrayList<Boolean> oppHistory) {
        if (gate == LogicGate.OR) {
            if (oppHistory.size() < memory)
                return makeMistake(true);
            else {
                boolean ret = oppHistory.get(oppHistory.size() - 1);
                for (int i = 2; i <= memory; i++) {
                    ret = ret || oppHistory.get(oppHistory.size() - i);
                }
                return makeMistake(ret);
            }
        } else {  // AND gate
            if (oppHistory.size() == 0)
                return makeMistake(true);
            else {
                boolean ret = oppHistory.get(oppHistory.size() - 1);
                for (int i = 2; i <= memory && i <= oppHistory.size(); i++) {
                    ret = ret && oppHistory.get(oppHistory.size() - i);
                }
                return makeMistake(ret);
            }
        }
    }

    public String toString() {
        String str = "Type: Copy(" + gate + ", " + memory + ")\n";
        str = str + "Total Score: " + score;
        return str;
    }

    public enum LogicGate {
        AND("AND"),
        OR("OR");

        public final String type;

        LogicGate(String type) {
            this.type = type;
        }

        public String toString() {
            return type;
        }
    }
}
