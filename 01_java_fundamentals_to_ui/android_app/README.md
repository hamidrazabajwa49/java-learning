# Module 01 Android App: Discount Calculator

A minimal, complete Android app demonstrating every Java concept from this module's
`CONCEPT_GUIDE.md` and `pure_java/` files running on a real screen: primitive types,
operators, `if`/`else`, `switch`, loops (via the underlying data used), and methods
including a static-style formatting helper.

## What It Does

The user enters an original price and a discount percentage. Tapping the button:

1. Validates that neither field is empty (`if`/`else`, mirrors `ControlFlowDemo.java`).
2. Parses the text into `double` and `int` primitives.
3. Validates the discount is within `0..100`.
4. Calls `computeDiscountedPrice(double, int)`, the exact method proven correct in
   `MethodsDemo.java`.
5. Formats the result with `String.format`, identical to `formatCurrency` in
   `MethodsDemo.java`.
6. Classifies the discount size using a `switch` statement, identical in structure to
   `gradeBandFor(int)` in `ControlFlowDemo.java`.

## Project Structure

```
android_app/
    settings.gradle.kts
    build.gradle.kts
    gradle/wrapper/gradle-wrapper.properties
    app/
        build.gradle.kts
        src/main/AndroidManifest.xml
        src/main/java/com/curriculum/module01/MainActivity.java
        src/main/res/layout/activity_main.xml
        src/main/res/values/strings.xml
        src/main/res/values/themes.xml
```

## Opening the Project

1. Launch Android Studio.
2. Choose "Open" and select this `android_app` folder directly (not the repo root).
3. Let Gradle sync. Android Studio will regenerate the Gradle wrapper jar and
   `gradlew`/`gradlew.bat` scripts automatically on first sync if they are not present;
   this repository intentionally omits the binary wrapper jar since binary files do not
   belong in a hand-authored source tree.
4. Select an emulator running API 24 or higher, or connect a physical device with USB
   debugging enabled.
5. Run the `app` configuration.

## Build Configuration Summary

- `compileSdk = 34`, `targetSdk = 34`, `minSdk = 24`
- Java source and target compatibility: Java 17
- Dependencies: AndroidX AppCompat, Material Components, ConstraintLayout (ConstraintLayout
  is included for parity with typical Android Studio templates, though this module's layout
  uses a simple `LinearLayout` since `ConstraintLayout` is not required to demonstrate the
  Java concepts here)
