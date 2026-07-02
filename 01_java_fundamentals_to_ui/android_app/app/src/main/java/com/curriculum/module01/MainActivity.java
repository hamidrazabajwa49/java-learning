package com.curriculum.module01;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * A single screen Activity that puts every concept from this module's
 * pure_java files directly on screen. The Activity class itself is an
 * example of inheritance (it extends AppCompatActivity), but that
 * mechanism is explained in Module 02. Here, treat AppCompatActivity
 * as the framework-provided container that gives onCreate() a place to
 * run, and focus entirely on the primitive types, operators, control
 * flow, and methods used inside it.
 *
 * The screen lets the user enter an original price and a discount
 * percentage, then computes and displays the discounted price along
 * with a qualitative grade band for the discount size, using the same
 * logic proven correct in ControlFlowDemo.java and MethodsDemo.java.
 */
public class MainActivity extends AppCompatActivity {

    private EditText editPrice;
    private EditText editDiscount;
    private TextView textResult;
    private TextView textGradeBand;

    /**
     * onCreate is called once by the Android framework when the
     * Activity is first created. It is a normal Java method; the
     * framework simply guarantees when it will call it. Every
     * statement inside behaves exactly as it would in any other Java
     * method, including the local variable declarations, which live on
     * this call's stack frame just like in the pure_java examples.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPrice = findViewById(R.id.edit_price);
        editDiscount = findViewById(R.id.edit_discount);
        textResult = findViewById(R.id.text_result);
        textGradeBand = findViewById(R.id.text_grade_band);

        Button computeButton = findViewById(R.id.button_compute);
        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleComputeClick();
            }
        });
    }

    /**
     * The click handler. This method reads two strings from the UI,
     * validates them using the same if/else control flow shape taught
     * in ControlFlowDemo.java, converts them to primitives, and then
     * delegates the actual math to computeDiscountedPrice(double, int),
     * the exact method defined and unit-tested in isolation inside
     * MethodsDemo.java.
     */
    private void handleComputeClick() {
        String rawPrice = editPrice.getText().toString();
        String rawDiscount = editDiscount.getText().toString();

        if (rawPrice.isEmpty() || rawDiscount.isEmpty()) {
            textResult.setText(getString(R.string.error_empty_field));
            textGradeBand.setText("");
            return;
        }

        double originalPrice;
        int discountPercent;

        /*
         * Parsing text into primitives can fail if the user types
         * something that is not a valid number. Full exception design
         * is the subject of Module 04; here only the minimum try/catch
         * needed to avoid a crash on bad input is used, with no custom
         * exception types yet.
         */
        try {
            originalPrice = Double.parseDouble(rawPrice);
            discountPercent = Integer.parseInt(rawDiscount);
        } catch (NumberFormatException exception) {
            textResult.setText(getString(R.string.error_invalid_number));
            textGradeBand.setText("");
            return;
        }

        if (discountPercent < 0 || discountPercent > 100) {
            textResult.setText(getString(R.string.error_discount_range));
            textGradeBand.setText("");
            return;
        }

        double discountedPrice = computeDiscountedPrice(originalPrice, discountPercent);
        textResult.setText(formatCurrency(discountedPrice));
        textGradeBand.setText(gradeBandForDiscount(discountPercent));
    }

    /**
     * Identical in structure and behavior to the method of the same
     * name in MethodsDemo.java. Given a non-negative original price and
     * a discount percentage between 0 and 100 inclusive, returns the
     * price after applying that discount.
     *
     * @param originalPrice the price before discount
     * @param discountPercent the discount to apply, 0 to 100
     * @return the discounted price
     */
    private double computeDiscountedPrice(double originalPrice, int discountPercent) {
        double multiplier = (100 - discountPercent) / 100.0;
        return originalPrice * multiplier;
    }

    /**
     * A static-style formatting helper mirroring formatCurrency in
     * MethodsDemo.java. Kept as an instance method here only because
     * getString() and other Activity conveniences are instance-scoped;
     * the formatting logic itself is unchanged from the pure_java
     * version.
     *
     * @param amount the value to format
     * @return the value formatted as US currency with two decimal places
     */
    private String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }

    /**
     * A switch statement that classifies the discount size into a
     * qualitative band, directly parallel to gradeBandFor(int) in
     * ControlFlowDemo.java. The switch operates on a derived value
     * (discountPercent divided by 10 through integer division) rather
     * than the raw input, the same pattern used later for
     * RecyclerView.Adapter view type dispatch in Module 03.
     *
     * @param discountPercent the discount percentage, 0 to 100
     * @return a human readable classification of the discount size
     */
    private String gradeBandForDiscount(int discountPercent) {
        int band = discountPercent / 10;

        switch (band) {
            case 10:
            case 9:
            case 8:
                return "Excellent discount";
            case 7:
            case 6:
            case 5:
                return "Good discount";
            case 4:
            case 3:
                return "Moderate discount";
            case 2:
            case 1:
                return "Small discount";
            default:
                return "No meaningful discount";
        }
    }
}
