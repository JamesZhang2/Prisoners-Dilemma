import java.util.ArrayList;

/**
 * The CopyPrisoner, in general, copies the response of their opponent's history responses
 * If its logic gate is OR, it cooperates in the first m rounds
 * and only betrays in the current round
 * if all of its opponent's responses in the previous m rounds have been to betray.
 * If its logic gate is AND, it only cooperates in the current round
 * if all of its opponent's responses in the previous m rounds have been to cooperate.
 * m is the memory of the CopyPrisoner.
 *
 * Special CopyPrisoners:
 * CopyPrisoner(OR, 1) and CopyPrisoner(AND, 1) is called a Copycat (TIT-FOR-TAT).
 * CopyPrisoner(OR, 2) is called a Copykitten (TIT-FOR-TWO-TATS).
 * CopyPrisoner(AND, 2) is called a Copylion (TWO-TITS-FOR-TAT).
 */
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
