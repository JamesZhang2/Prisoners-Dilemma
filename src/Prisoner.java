import java.util.ArrayList;

/**
 * Prisoner is the abstract class that includes many common states and behaviors
 * for the different types of Prisoners.
 */
public abstract class Prisoner {

    protected ArrayList<Boolean> history;
    protected double probOfMistake;
    protected double score = 0;  // Score for the current generation

    public abstract boolean coopOrBetray(ArrayList<Boolean> oppHistory);
    public abstract Prisoner clone();
    public abstract String getType();

    public void addToHistory(boolean response) {
        history.add(response);
    }

    public Prisoner(double probOfMistake) {
        history = new ArrayList<>();
        this.probOfMistake = probOfMistake;
    }

    public ArrayList<Boolean> getHistory() {
        return history;
    }

    public void resetScore() {
        score = 0;
    }

    public void clearHistory() {
        history = new ArrayList<>();
    }

    public void addScore(double scoreToAdd) {
        score += scoreToAdd;
    }

    public double getScore() {
        return score;
    }

    protected boolean makeMistake(boolean response) {
        return response ^ Math.random() < probOfMistake;  // ^ is the XOR operator
    }

    @Override
    public String toString() {
        return getType() +  "\nTotal Score: " + score;
    }
}
