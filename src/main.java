public class main {


    /**
     * Main fonction
     *
     * Change the set sent to prove to try the different tasks asked in the Oblig
     * set1 is the set of 8 clauses made from 3 literals
     * set2 is set1 minus 1 clause
     * set3 is the set of 2^N clauses made from N literals used to experiment how large N can be before the program struggles
     */
    public static void main(String[] args) {

        int[][] set1 = {{1, 2, 3}, {1, 2, -3}, {1, -2, 3}, {1, -2, -3}, {-1, 2, -3}, {-1, -2, 3}, {-1, 2, 3}, {-1, -2, -3}};
        int[][] set2 = {{1, 2, 3}, {1, 2, -3}, {1, -2, 3}, {1, -2, -3}, {-1, 2, -3}, {-1, -2, 3}, {-1, 2, 3}};

        int[][] set3 = makeSet(12);

        int[][] workingSet = prove(set1);
        result(workingSet);
    }


    /**
     * Function prove
     * Apply rules on the set of clauses sent and return the set of clauses after application of the rules
     * Each time a literal can be cancelled because of the presence of its negation somewhere, both the literal and the negation become 0
     *
     * param:
     * int[][] set: the set to test
     *
     * return:
     * int[][] set: the set, tested and modified accordingly
     */
    public static int[][] prove(int[][] set) {

        int[] position = {0, 0};

        for (int i = 0 ; i < set.length ; i++) {
            for (int j = 0 ; j < set[i].length ; j++) {
                position = findNegationInSet(set[i][j], set);

                if (position[0] != -1 && position[1] != -1)
                {
                    set[position[0]][position[1]] = 0;
                    set[i][j] = 0;
                }
            }
        }

        return set;
    }


    /**
     * Function result
     * Given a set that has been through the function prove, gives an answer about the satisfiability of the set
     *
     * param:
     * int[][] set: the set tested
     */
    public static void result(int[][] set) {

        String result = "";

        for (int i = 0 ; i < set.length ; i++) {
            for (int j = 0 ; j < set[i].length ; j++) {
                if (set[i][j] != 0) {
                    result = result + String.valueOf(set[i][j]) + " ";
                }
            }
        }

        if (result == "") {
            System.out.println("The set is unsatisfiable.");
        }
        else {
            System.out.println(result + "is a satisfying interpretation of the set.");
        }
    }


    /**
     * Function findNegationInSet
     * Find the position, if they exist, in the set, of the negation of the literal sent in parameter
     *
     * param:
     * int literal: the literal of which the negation is searched for
     * int[][] set: the set in which the search happens
     *
     * return:
     * int[] position: the position of the negation, if the negation is not found, the value stays {-1, -1}
     */
    public static int[] findNegationInSet(int literal, int[][] set) {

        int[] position = {-1, -1};

        for (int i = 0 ; i < set.length ; i++) {
            for (int j = 0 ; j < set[i].length ; j++) {
                if (literal == -set[i][j]) {
                    position[0] = i;
                    position[1] = j;
                    break;
                }
            }
        }

        return position;
    }


    /**
     * Function makeSet
     * Given a number of literals, make a set of all of the possible combination of clauses
     *
     * param:
     * int N: the number of different literals wanted in the set
     *
     * return:
     * int[][] set: a set made from all the combinations made from N different literals
     */
    public static int[][] makeSet(int N) {

        int[][] set = new int[(int) Math.pow(2, N)][N];

        for (int i = 0 ; i < set.length ; i++) {
            for (int j = 0 ; j < set[i].length ; j++) {
                if (i == 0) {
                    set[i][j] = j + 1;
                }
                else {
                    if (Math.ceil((i + 1) / Math.pow(2, N - (j + 1))) % 2 == 1) {
                        set[i][j] = set[0][j];
                    }
                    else {
                        set[i][j] = -set[0][j];
                    }
                }
            }

			/*System.out.println("Just created the clause " + i + " with the literals:");
			for (int j = 0 ; j < set[i].length ; j++) {
				System.out.println(set[i][j]);
			}*/
        }

        return set;
    }


}
