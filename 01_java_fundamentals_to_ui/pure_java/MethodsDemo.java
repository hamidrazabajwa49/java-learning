/**
 * Demonstrates method signatures, overloading, static utility methods,
 * and Java's pass-by-value parameter semantics for both primitives and
 * object references.
 *
 * Run with:
 *   javac MethodsDemo.java
 *   java MethodsDemo
 */
public class MethodsDemo {

    public static void main(String[] args) {
        System.out.println(formatCurrency(19.999));

        System.out.println(computeDiscountedPrice(100.0, 25));

        System.out.println(describe(5));
        System.out.println(describe(5.5));
        System.out.println(describe("five"));

        int original = 10;
        incrementPrimitive(original);
        System.out.println("After incrementPrimitive, original = " + original);

        StringBuilder message = new StringBuilder("Loading");
        appendDots(message);
        System.out.println("After appendDots, message = " + message);
    }

    /**
     * A static utility method with no dependency on instance state.
     * String.format applies standard printf-style formatting; %.2f
     * rounds to two decimal places, matching how currency should be
     * displayed in a UI regardless of the precision of the underlying
     * double.
     */
    public static String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }

    /**
     * Demonstrates a method with two parameters of different types and
     * a computed return value. This exact method is called from the
     * Android app's click handler in this module.
     *
     * @param originalPrice the price before discount, must be non-negative
     * @param discountPercent the discount to apply, expected range 0-100
     * @return the discounted price rounded by formatCurrency's caller
     */
    public static double computeDiscountedPrice(double originalPrice, int discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("discountPercent must be between 0 and 100");
        }
        double multiplier = (100 - discountPercent) / 100.0;
        return originalPrice * multiplier;
    }

    /*
     * Method overloading: three methods share the name "describe" but
     * differ in parameter type, so the compiler selects the correct
     * one at compile time based on the argument's static type. This is
     * exactly how TextView.setText(String) versus
     * TextView.setText(int resId) is resolved by the Android SDK.
     */

    public static String describe(int value) {
        return "int value: " + value;
    }

    public static String describe(double value) {
        return "double value: " + value;
    }

    public static String describe(String value) {
        return "String value: " + value;
    }

    /**
     * Because int is a primitive, the value is copied into the
     * parameter n. Mutating n inside this method has no effect on the
     * caller's variable, since they occupy separate stack slots after
     * the call.
     */
    private static void incrementPrimitive(int n) {
        n = n + 1;
        System.out.println("Inside incrementPrimitive, n = " + n);
    }

    /**
     * Because builder is a reference to a heap-allocated StringBuilder,
     * the reference value is copied, but both the caller's variable and
     * this parameter point at the same object. Mutating the object
     * through either reference is visible to both.
     */
    private static void appendDots(StringBuilder builder) {
        builder.append("...");
    }
}
