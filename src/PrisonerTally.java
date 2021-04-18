import java.util.ArrayList;

/**
 * The PrisonerTally class is a structure that contains two parallel ArrayLists.
 * It tallies the types of prisoners and the number of each type of prisoners.
 */
public class PrisonerTally {
    private final ArrayList<String> prisonerTypes = new ArrayList<>();
    private final ArrayList<Integer> prisonerNumbers = new ArrayList<>();

    public PrisonerTally(Prisoner[] prisoners) {
        tallyPrisoners(prisoners);
    }

    public PrisonerTally() {}

    /**
     * Count how many prisoners are of each type
     * @param prisoners The prisoners to be tallied
     */
    public void tallyPrisoners(Prisoner[] prisoners) {
        for (Prisoner prisoner : prisoners) {
            boolean isFound = false;
            for (int i = 0; i < prisonerTypes.size(); i++) {
                if (prisoner.getType().equals(prisonerTypes.get(i))) {
                    // Current type of prisoner is found, increment prisonerNumbers
                    prisonerNumbers.set(i, prisonerNumbers.get(i) + 1);
                    isFound = true;
                    break;
                }
            }
            // Current type of prisoner not found, add this type to the prisonerTypes list
            if (!isFound) {
                prisonerTypes.add(prisoner.getType());
                prisonerNumbers.add(1);
            }
        }
    }

    /**
     * Combine two tallies of prisoners and save the results
     * @param tally The tally to be combined
     */
    public void combine(PrisonerTally tally) {
        for (int i = 0; i < tally.prisonerTypes.size(); i++) {
            boolean isFound = false;
            for (int j = 0; j < prisonerTypes.size(); j++) {
                if (tally.prisonerTypes.get(i).equals(prisonerTypes.get(j))) {
                    // Current type of prisoner is found, increase prisonerNumbers
                    prisonerNumbers.set(j, prisonerNumbers.get(j) + tally.prisonerNumbers.get(i));
                    isFound = true;
                    break;
                }
            }
            // Current type of prisoner not found, add this type to the prisonerTypes list
            if (!isFound) {
                prisonerTypes.add(tally.prisonerTypes.get(i));
                prisonerNumbers.add(tally.prisonerNumbers.get(i));
            }
        }
    }

    public ArrayList<String> getPrisonerTypes() {
        return prisonerTypes;
    }

    public ArrayList<Integer> getPrisonerNumbers() {
        return prisonerNumbers;
    }

    @Override
    public String toString() {
        String msg = "";
        for (int i = 0; i < prisonerTypes.size() - 1; i++) {
            int num = prisonerNumbers.get(i);
            msg = msg + prisonerTypes.get(i) + ": " + num + (num == 1 ? " Prisoner" : " Prisoners") + "\n";
        }
        int num = prisonerNumbers.get(prisonerTypes.size() - 1);
        msg = msg + prisonerTypes.get(prisonerTypes.size() - 1) + ": " + num + (num == 1 ? " Prisoner" : " Prisoners");
        return msg;
    }
}
