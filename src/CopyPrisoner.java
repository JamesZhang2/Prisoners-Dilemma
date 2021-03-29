import java.util.ArrayList;

public class CopyPrisoner extends Prisoner{
    private LogicGate gate;
    private int memory;

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

    @Override
    public Prisoner clone() {
        return new CopyPrisoner(probOfMistake, gate, memory);
    }

    @Override
    public String getType() {
        return "Type: Copy(" + gate + ", " + memory + ")";
    }

    @Override
    public String toString() {
        return getType() + "\nTotal Score: " + score;
    }

    public enum LogicGate {
        AND("AND"),
        OR("OR");

        public final String gate;

        LogicGate(String gate) {
            this.gate = gate;
        }

        public String toString() {
            return gate;
        }
    }
}
