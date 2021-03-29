import java.util.ArrayList;

public class Prison {

    private static Prisoner[] prisoners;
    public static final double PROB_OF_MISTAKE = 0.1;
    public static final int ROUNDS_PER_GAME = 10;
    public static final int GENERATIONS = 30;
    // The number of prisoners to be replaced by the end of each generation
    public static final int REPLACEMENT_PER_GEN = 3;
    public static final double DC = 5.0;
    public static final double CC = 3.0;
    public static final double DD = 1.0;
    public static final double CD = 0.0;

    public static void main(String[] args) {
        initializePrisoners();
        for (int i = 0; i < GENERATIONS; i++) {
            resetAllScores();
            startTournament();
            sortPrisoners();
            printResults(i);
            evolve();
        }
    }

    private static void resetAllScores() {
        for (Prisoner prisoner : prisoners) {
            prisoner.resetScore();
        }
    }

    private static void initializePrisoners() {
        prisoners = new Prisoner[20];
        for (int i = 0; i < 20; i++) {
            prisoners[i] = new RandomPrisoner(PROB_OF_MISTAKE, i / 20.0);
        }

        /*
        prisoners[0] = new RandomPrisoner(PROB_OF_MISTAKE, 0);
        prisoners[1] = new RandomPrisoner(PROB_OF_MISTAKE, 1);
        prisoners[2] = new Grudger(PROB_OF_MISTAKE);
        prisoners[3] = new CopyPrisoner(PROB_OF_MISTAKE, CopyPrisoner.LogicGate.OR, 1);
        prisoners[4] = new CopyPrisoner(PROB_OF_MISTAKE, CopyPrisoner.LogicGate.OR, 2);
        boolean[] probe = {true, false, true};
        prisoners[5] = new Detective(PROB_OF_MISTAKE, probe);
        */
    }

    private static void startTournament() {
        for (int i = 0; i < prisoners.length; i++) {
            for (int j = i + 1; j < prisoners.length; j++) {
                prisoners[i].clearHistory();
                prisoners[j].clearHistory();
                startMatch(prisoners[i], prisoners[j]);
            }
        }
    }

    private static void startMatch(Prisoner p1, Prisoner p2) {
        for (int i = 0; i < ROUNDS_PER_GAME; i++) {
            boolean p1Coop = p1.coopOrBetray(p2.getHistory());
            boolean p2Coop = p2.coopOrBetray(p2.getHistory());
            p1.addToHistory(p1Coop);
            p2.addToHistory(p2Coop);
            // Update scores
            if (p1Coop) {
                if (p2Coop) {
                    p1.addScore(CC);
                    p2.addScore(CC);
                } else {
                    p1.addScore(CD);
                    p2.addScore(DC);
                }
            } else {
                if (p2Coop) {
                    p1.addScore(DC);
                    p2.addScore(CD);
                } else {
                    p1.addScore(DD);
                    p2.addScore(DD);
                }
            }
        }
    }

    private static void sortPrisoners() {
        // Selection sort
        for (int i = 0; i < prisoners.length; i++) {
            int maxIndex = findMax(i);
            // Swap
            Prisoner temp = prisoners[i];
            prisoners[i] = prisoners[maxIndex];
            prisoners[maxIndex] = temp;
        }
    }

    private static int findMax(int startIndex) {
        int maxID = startIndex;
        double maxScore = prisoners[startIndex].getScore();
        for (int i = startIndex; i < prisoners.length; i++) {
            if (prisoners[i].getScore() > maxScore) {
                maxScore = prisoners[i].getScore();
                maxID = i;
            }
        }
        return maxID;
    }

    private static void printResults(int gen) {
        System.out.println("Generation " + (gen + 1) + ": ");
        printClasses();
        System.out.println("--------------------------");
    }

    private static void printClasses() {
        ArrayList<String> prisonerTypes = new ArrayList<>();
        ArrayList<Integer> prisonerNumbers = new ArrayList<>();
        for (Prisoner prisoner : prisoners) {
            boolean isFound = false;
            for (int i = 0; i < prisonerTypes.size(); i++) {
                if (prisoner.getType().equals(prisonerTypes.get(i))) {
                    prisonerNumbers.set(i, prisonerNumbers.get(i) + 1);
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                prisonerTypes.add(prisoner.getType());
                prisonerNumbers.add(1);
            }
        }
        for (int i = 0; i < prisonerTypes.size(); i++) {
            int num = prisonerNumbers.get(i);
            System.out.println(prisonerTypes.get(i) + ": " + num + (num == 1 ? " Prisoner" : " Prisoners"));
        }
    }

    // For debugging
    private static void printDetailedResults(int gen) {
        System.out.println("Generation " + (gen + 1) + ": ");
        for (Prisoner prisoner : prisoners) {
            System.out.println(prisoner);
        }
        System.out.println("--------------------------");
    }

    private static void evolve() {
        for (int i = 0; i < REPLACEMENT_PER_GEN; i++) {
            prisoners[prisoners.length - i - 1] = prisoners[i].clone();
        }
    }
}
