public class Prison {

    private static Prisoner[] prisoners;
    public static final double PROB_OF_MISTAKE = 0.1;
    public static final int ROUNDS_PER_GAME = 10;
    public static final double DC = 5.0;
    public static final double CC = 3.0;
    public static final double DD = 1.0;
    public static final double CD = 0.0;

    public static void main(String[] args) {
        prisoners = new Prisoner[5];
        prisoners[0] = new RandomPrisoner(PROB_OF_MISTAKE, 0);
        prisoners[1] = new RandomPrisoner(PROB_OF_MISTAKE, 1);
        prisoners[2] = new Grudger(PROB_OF_MISTAKE);
        prisoners[3] = new CopyPrisoner(PROB_OF_MISTAKE, CopyPrisoner.LogicGate.OR, 1);
        prisoners[4] = new CopyPrisoner(PROB_OF_MISTAKE, CopyPrisoner.LogicGate.OR, 2);
        startTournament();
        printResults();
    }

    private static void startTournament() {
        for (int i = 0; i < prisoners.length; i++) {
            for (int j = i + 1; j < prisoners.length; j++) {
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

    private static void printResults() {
        for (int i = 0; i < prisoners.length; i++) {
            System.out.println(prisoners[i]);
        }
    }
}
