/**
 * Demonstrates if/else chains, switch statements, and loops using the
 * exact shape of decisions an Android app makes: validating a form
 * field, mapping a numeric score to a grade band, and summing values
 * from a fixed-size dataset.
 *
 * Run with:
 *   javac ControlFlowDemo.java
 *   java ControlFlowDemo
 */
public class ControlFlowDemo {

    public static void main(String[] args) {
        validatePassword("abc");
        validatePassword("longenoughpassword");
        validatePassword("");

        System.out.println(gradeBandFor(95));
        System.out.println(gradeBandFor(82));
        System.out.println(gradeBandFor(58));

        int[] quizScores = {88, 92, 75, 60, 99};
        printAverage(quizScores);

        countdownWhileLoop(3);
    }

    /**
     * Mirrors the exact validation pattern used in the Android app's
     * button click handler. Each branch corresponds to one on-screen
     * error state.
     */
    private static void validatePassword(String input) {
        if (input.isEmpty()) {
            System.out.println("[\"" + input + "\"] Error: field cannot be empty");
        } else if (input.length() < 8) {
            System.out.println("[\"" + input + "\"] Error: must be at least 8 characters");
        } else {
            System.out.println("[\"" + input + "\"] Accepted");
        }
    }

    /**
     * A switch statement operating on a derived value (a grade band
     * index computed from a raw score). This is the same pattern used
     * by RecyclerView.Adapter.getItemViewType() dispatch logic, which
     * Module 03 builds on directly.
     */
    private static String gradeBandFor(int score) {
        int band = score / 10;

        switch (band) {
            case 10:
            case 9:
                return score + " -> A";
            case 8:
                return score + " -> B";
            case 7:
                return score + " -> C";
            case 6:
                return score + " -> D";
            default:
                return score + " -> F";
        }
    }

    /**
     * A standard for loop over a fixed-size primitive array, the same
     * traversal pattern later reused to walk adapter data sets.
     */
    private static void printAverage(int[] scores) {
        int total = 0;
        for (int i = 0; i < scores.length; i++) {
            total += scores[i];
        }
        double average = (double) total / scores.length;
        System.out.println("Average score: " + average);
    }

    /**
     * A while loop driven by a runtime condition rather than a fixed
     * count, matching the shape of polling or retry logic used when
     * waiting on a background result.
     */
    private static void countdownWhileLoop(int start) {
        int current = start;
        while (current > 0) {
            System.out.println("Countdown: " + current);
            current--;
        }
        System.out.println("Countdown: liftoff");
    }
}
