/**
 * A prison is a tournament with many prisoners.
 * Every two prisoners compete with each other in a match of several rounds.
 * At the end of all the matches, the prisoners' scores are sorted.
 * The prisoners with the highest scores clone themselves and replace the prisoners
 * with the lowest scores, and a new generation begins.
 *
 * A simulation runs many prisons with the same initial conditions and tallies up the results.
 */
public class Simulation {

    public static final boolean PRISONER_DEBUG = false;
    public static final boolean GENERATION_DEBUG = false;
    public static final boolean SIMULATION_DEBUG = true;

    // The following constants and private instance variables are used for each prison
    private static Prisoner[] prisoners;
    public static final double PROB_OF_MISTAKE = 0.2;
    public static final int ROUNDS_PER_GAME = 10;
    public static final int GENERATIONS = 100;
    // The number of prisoners to be replaced by the end of each generation
    public static final int REPLACEMENT_PER_GEN = 3;
    // B: Betray, C: Cooperate
    // BC: The payoff of betraying when opponent cooperates
    public static final double BC = 3.0;
    public static final double CC = 2.0;
    public static final double BB = 0.0;
    public static final double CB = -1.0;

    // The following constants and private instance variables are for simulations
    public static final double PRISONS_PER_SIMULATION = 10;
    private static final PrisonerTally totalTally = new PrisonerTally();

    public static void main(String[] args) {
        for (int i = 0; i < PRISONS_PER_SIMULATION; i++) {
            runPrison();
            // Merge the results of the current tournament to the total results
            PrisonerTally tally = new PrisonerTally(prisoners);
            totalTally.combine(tally);
            if (SIMULATION_DEBUG) {
                System.out.println("Simulation " + (i + 1) + ":\n" + tally);
                System.out.println("--------------------------");
            }
        }
        System.out.println("Final results:\n" + totalTally);
    }

    /**
     * Run the prison (tournament) once
     */
    public static void runPrison() {
        // Initialize prisoners
        if (PRISONER_DEBUG)
            initializeTestPrisoners();
        else
            initializePrisoners();
        // Run the prison
        for (int i = 0; i < GENERATIONS; i++) {
            resetAllScores();
            startTournament();
            sortPrisoners();
            if (checkDomination(i))  // One type of prisoner has dominated the prison
                break;
            evolve();
        }
    }

    /**
     * Initialize prisoners into different types
     */
    private static void initializePrisoners() {
        prisoners = new Prisoner[50];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++)
                prisoners[i * 5 + j] = new RandomPrisoner(PROB_OF_MISTAKE, i / 4.0);
        }
        for (int i = 25; i < 30; i++) {
            prisoners[i] = new Grudger(PROB_OF_MISTAKE);
        }
        for (int i = 30; i < 35; i++) {
            // Copycats
            prisoners[i] = new CopyPrisoner(PROB_OF_MISTAKE, CopyPrisoner.LogicGate.OR, 1);
        }
        for (int i = 35; i < 40; i++) {
            // Copykittens
            prisoners[i] = new CopyPrisoner(PROB_OF_MISTAKE, CopyPrisoner.LogicGate.OR, 2);
        }
        for (int i = 40; i < 45; i++) {
            // Copylions
            prisoners[i] = new CopyPrisoner(PROB_OF_MISTAKE, CopyPrisoner.LogicGate.AND, 2);
        }
        for (int i = 45; i < 50; i++) {
            boolean[] probe = {true, false, true, true};
            prisoners[i] = new Detective(PROB_OF_MISTAKE, probe);
        }
    }

    /**
     * Debug
     * PROB_OF_MISTAKE = 0
     * ROUNDS_PER_GAME = 10
     * GENERATIONS = 1
     * Payoff: 3,2,0,-1
     * Correct Results:
     * Copycat: 57
     * Grudger: 46
     * Detective: 45
     * Cheater: 45
     * Cooperator: 29
     */
    private static void initializeTestPrisoners() {
        prisoners = new Prisoner[5];
        prisoners[0] = new RandomPrisoner(PROB_OF_MISTAKE,0);
        prisoners[1] = new RandomPrisoner(PROB_OF_MISTAKE, 1);
        prisoners[2] = new Grudger(PROB_OF_MISTAKE);
        prisoners[3] = new CopyPrisoner(PROB_OF_MISTAKE, CopyPrisoner.LogicGate.OR, 1);
        boolean[] probe = {true, false, true, true};
        prisoners[4] = new Detective(PROB_OF_MISTAKE, probe);
    }

    /**
     * Reset the scores of all the prisoners at the start of each generation
     */
    private static void resetAllScores() {
        for (Prisoner prisoner : prisoners) {
            prisoner.resetScore();
        }
    }

    /**
     * Every two prisoners compete with each other (round-robin) in a match of several rounds
     */
    private static void startTournament() {
        for (int i = 0; i < prisoners.length; i++) {
            for (int j = i + 1; j < prisoners.length; j++) {
                prisoners[i].clearHistory();
                prisoners[j].clearHistory();
                startMatch(prisoners[i], prisoners[j]);
            }
        }
    }

    /**
     * Play one match between prisoners p1 and p2
     * @param p1 The first prisoner
     * @param p2 The second prisoner
     */
    private static void startMatch(Prisoner p1, Prisoner p2) {
        for (int i = 0; i < ROUNDS_PER_GAME; i++) {
            // Cache choices so that the later player
            // doesn't get their opponent's choice for the current round
            boolean p1Coop = p1.coopOrBetray(p2.getHistory());
            boolean p2Coop = p2.coopOrBetray(p1.getHistory());
            p1.addToHistory(p1Coop);
            p2.addToHistory(p2Coop);
            // Update scores
            if (p1Coop) {
                if (p2Coop) {
                    p1.addScore(CC);
                    p2.addScore(CC);
                } else {
                    p1.addScore(CB);
                    p2.addScore(BC);
                }
            } else {
                if (p2Coop) {
                    p1.addScore(BC);
                    p2.addScore(CB);
                } else {
                    p1.addScore(BB);
                    p2.addScore(BB);
                }
            }
        }

        if (PRISONER_DEBUG) {
            System.out.println(p1);
            System.out.println(p2);
            System.out.println("-----------");
        }
    }

    /**
     * Sort the prisoners' scores in descending order using selection sort
     */
    private static void sortPrisoners() {
        for (int i = 0; i < prisoners.length; i++) {
            int maxIndex = findMax(i);
            // Swap
            Prisoner temp = prisoners[i];
            prisoners[i] = prisoners[maxIndex];
            prisoners[maxIndex] = temp;
        }
    }

    /**
     * Find the index of the maximum score from startIndex to prisoners.length - 1
     * @param startIndex The index to start searching from
     * @return The index of the maximum score from startIndex to prisoners.length - 1
     */
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

    /**
     * Check whether one type of prisoner has dominated the prison
     * When debugging, print the results for each generation
     * @param gen The generation to print results
     * @return Whether one type of prisoner has dominated the prison
     */
    private static boolean checkDomination(int gen) {
        PrisonerTally tally = new PrisonerTally(prisoners);
        if (GENERATION_DEBUG) {
            System.out.println("Generation " + (gen + 1) + ": ");
            System.out.println(tally);
            System.out.println("--------------------------");
        }
        return tally.getPrisonerTypes().size() == 1;  // One type of prisoner has dominated the prison
    }

    /**
     * Debug
     * Printing the detailed results (type and score) of each prisoner
     * @param gen The generation to print results
     */
    private static void printDetailedResults(int gen) {
        System.out.println("Generation " + (gen + 1) + ": ");
        for (Prisoner prisoner : prisoners) {
            System.out.println(prisoner);
        }
        System.out.println("--------------------------");
    }

    /**
     * Evolve using the genetic algorithm
     * The prisoners with the highest scores are cloned to replace the prisoners with the lowest scores
     */
    private static void evolve() {
        for (int i = 0; i < REPLACEMENT_PER_GEN; i++) {
            prisoners[prisoners.length - i - 1] = prisoners[i].clone();
        }
    }
}