public class Prison {

    private static Prisoner[] prisoners;

    public static void main(String[] args) {
        prisoners = new Prisoner[100];
        for (int i = 0; i < prisoners.length; i += 2) {
            prisoners[i] = new RandomPrisoner(Math.random());
            prisoners[i + 1] = new Grudger();
        }
        startMatch();
    }

    public static void startMatch() {
        for (int i = 0; i < prisoners.length; i += 2) {
            prisoners[i].coopOrBetray(prisoners[i + 1].getPrevious());
            prisoners[i + 1].coopOrBetray(prisoners[i].getPrevious());
        }
    }
}
