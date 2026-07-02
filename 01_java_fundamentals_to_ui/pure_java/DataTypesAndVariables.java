/**
 * Demonstrates Java's primitive types, their default values, and the
 * integer-division pitfall that later shows up in Android progress and
 * percentage calculations.
 *
 * Every variable declared in main() below is a local variable. Local
 * variables of primitive type are stored on the stack frame created for
 * main(). When main() returns, that entire frame, and every value in it,
 * is popped in constant time. No garbage collector involvement is needed
 * for any of these values because none of them are heap objects.
 *
 * Run with:
 *   javac DataTypesAndVariables.java
 *   java DataTypesAndVariables
 */
public class DataTypesAndVariables {

    public static void main(String[] args) {
        /*
         * boolean: conceptually one bit of information, true or false.
         * Android uses this constantly for state flags such as
         * isLoading, isEnabled, isVisible.
         */
        boolean isLoggedIn = true;

        /*
         * int: 32-bit signed integer, range -2^31 to 2^31 - 1.
         * This is the default choice for counts, indices, and Android
         * resource IDs (e.g. R.id.button_submit is an int under the hood).
         */
        int itemCount = 7;

        /*
         * long: 64-bit signed integer. Required whenever a value can
         * exceed roughly 2.1 billion, which happens immediately with
         * millisecond timestamps. The L suffix tells the compiler to
         * treat the literal as a long rather than the default int.
         */
        long timestampMillis = 1735689600000L;

        /*
         * double: 64-bit floating point, the default choice for
         * general decimal math.
         */
        double accountBalance = 1042.55;

        /*
         * float: 32-bit floating point. Android's View coordinate
         * system (getX, getY, translationX, translationY) is built on
         * float rather than double to save memory and because screen
         * precision does not require 64 bits.
         */
        float viewTranslationX = 12.5f;

        /*
         * char: a single 16-bit UTF-16 code unit, written with single
         * quotes.
         */
        char firstInitial = 'A';

        /*
         * byte and short are rarely used explicitly in application code
         * but appear when reading raw network or file data efficiently.
         */
        byte smallFlag = 1;
        short mediumCount = 300;

        System.out.println("isLoggedIn = " + isLoggedIn);
        System.out.println("itemCount = " + itemCount);
        System.out.println("timestampMillis = " + timestampMillis);
        System.out.println("accountBalance = " + accountBalance);
        System.out.println("viewTranslationX = " + viewTranslationX);
        System.out.println("firstInitial = " + firstInitial);
        System.out.println("smallFlag = " + smallFlag);
        System.out.println("mediumCount = " + mediumCount);

        demonstrateIntegerDivisionPitfall();
        demonstrateReferenceVsPrimitiveAssignment();
    }

    /**
     * The most common real bug that starts here: dividing two ints
     * truncates toward zero instead of producing a fraction. This is
     * exactly the bug that produces a progress bar stuck at 0% when a
     * beginner writes (completed / total) with two int variables.
     */
    private static void demonstrateIntegerDivisionPitfall() {
        int completedSteps = 3;
        int totalSteps = 8;

        int wrongPercentage = (completedSteps / totalSteps) * 100;
        double correctPercentage = ((double) completedSteps / totalSteps) * 100;

        System.out.println("Wrong (int division) percentage: " + wrongPercentage);
        System.out.println("Correct (cast to double) percentage: " + correctPercentage);
    }

    /**
     * Shows the difference between copying a primitive value and
     * copying an object reference. Both variables end up on the stack,
     * but one holds an actual value and the other holds an address to
     * a shared heap object.
     */
    private static void demonstrateReferenceVsPrimitiveAssignment() {
        int a = 10;
        int b = a;
        b = 20;
        System.out.println("Primitive copy: a = " + a + ", b = " + b);

        StringBuilder builderOne = new StringBuilder("Hello");
        StringBuilder builderTwo = builderOne;
        builderTwo.append(" World");
        System.out.println("Reference copy: builderOne = " + builderOne
                + ", builderTwo = " + builderTwo);
    }
}
