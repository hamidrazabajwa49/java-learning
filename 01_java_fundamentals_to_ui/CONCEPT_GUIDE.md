# Module 01: Java Fundamentals to UI

## Learning Objectives

By the end of this module you will understand:

1. How Java's primitive types are stored and why that matters on a memory-constrained device.
2. How operators and control flow (`if`/`else`, `switch`, loops) map directly onto decisions
   an Android app makes about what to show a user.
3. How methods, parameters, return values, and overloading let you build the reusable logic
   that backs every button click and text update in an app.
4. How every one of these concepts appears, unmodified, inside a real `Activity` class.

## 1. Primitive Types and Variables

Java is statically typed. Every variable has a type fixed at compile time, and that type
determines exactly how many bytes are reserved for it and where those bytes live.

```java
int score = 42;
double price = 19.99;
boolean isLoggedIn = true;
char grade = 'A';
long userId = 9001234567L;
```

### The Stack vs. the Heap

This is the single most important mental model for writing efficient Android code, and it
starts here, with primitives.

- **Primitives declared as local variables** (`int`, `double`, `boolean`, `char`, `byte`,
  `short`, `long`, `float`) live on the **stack**. The stack is a small, fast region of
  memory. Each method call gets its own "frame" on the stack, and when the method returns,
  the frame (and every primitive in it) is popped and instantly reclaimed. There is no
  garbage collection involved for these values.
- **Objects** (anything created with `new`, including `String`, arrays, and your own classes)
  live on the **heap**. The heap is larger, slower to allocate from, and managed by the
  garbage collector (GC). A variable that looks like it "holds" an object actually holds a
  *reference* (a pointer-like value) to that object's location on the heap. The reference
  itself is a primitive-sized value and can live on the stack; the object it points to lives
  on the heap.

This distinction is why Android engineering guides constantly warn you to avoid unnecessary
object allocation inside frequently-called code such as `onDraw()` or `onBindViewHolder()`.
Every `new` you write there is heap pressure. A method that only uses primitives and stack
frames costs almost nothing by comparison; the JVM/ART can allocate and free stack frames far
faster than it can allocate and later garbage-collect heap objects.

### Type Sizes You Should Actually Know

| Type | Size | Typical Android Use |
|---|---|---|
| `boolean` | JVM-dependent, conceptually 1 bit of information | Feature flags, `isVisible`, `isEnabled` |
| `int` | 32-bit | View IDs, pixel dimensions, list indices, RGB-adjacent math |
| `long` | 64-bit | Timestamps (`System.currentTimeMillis()`), database row IDs |
| `float` | 32-bit | Android's UI coordinate system (`View.getX()` returns `float`) |
| `double` | 64-bit | General-purpose decimal math, currency before formatting |
| `char` | 16-bit (UTF-16 code unit) | Single-character validation, e.g. checking an input mask |

Android specifically prefers `int` for resource IDs and `float` for on-screen coordinates.
When you call `view.setX(120f)` you are passing a primitive `float`, not an object, which is
why that call is cheap enough to run every frame during an animation.

## 2. Operators

Java's operators are grouped into categories you will use constantly when deciding what to
render:

- **Arithmetic**: `+ - * / %`. Note that `/` between two `int` values performs integer
  division (`7 / 2` is `3`, not `3.5`). This single fact causes a huge number of real bugs in
  Android code that computes things like scroll percentages or progress bar fill amounts.
  Always cast at least one operand to `double` or `float` when you need a fractional result:
  `(float) completed / total`.
- **Relational**: `== != < > <= >=`. For primitives, `==` compares values. For objects
  (including `String`), `==` compares references, not content. This is why comparing two
  `String` values for equality in Java, and therefore in Android, must use `.equals()`, not
  `==`.
- **Logical**: `&& || !`. Both `&&` and `||` are short-circuiting: the right-hand side is not
  evaluated if the left-hand side already determines the result. This matters when the
  right-hand side has a side effect or could throw, e.g.
  `if (user != null && user.isVerified())` safely avoids a `NullPointerException` because
  `user.isVerified()` is never reached when `user` is `null`.
- **Assignment and compound assignment**: `= += -= *= /= %=`.

## 3. Control Flow

### `if` / `else if` / `else`

This is the mechanism behind every conditional layout decision in Android: showing a loading
spinner versus content, showing an error message versus a success message, enabling versus
disabling a submit button.

```java
if (inputText.isEmpty()) {
    showError("Field cannot be empty");
} else if (inputText.length() < 8) {
    showError("Must be at least 8 characters");
} else {
    submitForm(inputText);
}
```

### `switch`

A `switch` statement (or, in modern Java, a `switch` expression) is a cleaner alternative to
a long `if`/`else if` chain when you are branching on a single value, such as a view type in
a `RecyclerView.Adapter` or a request code from an intent result.

```java
switch (viewType) {
    case TYPE_HEADER:
        return "Header";
    case TYPE_ITEM:
        return "Item";
    case TYPE_FOOTER:
        return "Footer";
    default:
        throw new IllegalArgumentException("Unknown view type: " + viewType);
}
```

Always include a `default` branch. In Android, an unhandled `viewType` in an adapter is a
common source of crashes; failing loudly with an exception during development is safer than
silently doing nothing.

### Loops

- `for` loops are best when you know the number of iterations in advance, such as iterating
  over a fixed-size array of form fields to validate each one.
- `while` loops are best when the number of iterations depends on a runtime condition, such
  as reading chunks from a network stream until no more data is available.
- Enhanced `for` (`for (Type item : collection)`) is the idiomatic way to walk a `List` when
  you do not need the index, which you will use extensively once Module 03 introduces
  `RecyclerView` data sources.

```java
int[] scores = {88, 92, 75, 60, 99};
int total = 0;
for (int i = 0; i < scores.length; i++) {
    total += scores[i];
}
double average = (double) total / scores.length;
```

## 4. Methods

A method is a named, reusable block of code with a defined input (parameters) and output
(return type). Every callback in Android, from `onClick()` to `onCreate()`, is a method that
the framework calls for you at a specific point in the app's lifecycle.

```java
public static String formatCurrency(double amount) {
    return String.format("$%.2f", amount);
}
```

Key vocabulary:

- **Signature**: the method's name plus the number and types of its parameters. Java uses the
  signature, not the return type, to distinguish overloaded methods.
- **Overloading**: defining multiple methods with the same name but different parameter
  lists. Android's own `TextView.setText()` is overloaded to accept a `String`, a `CharSequence`,
  or an `int` resource ID, and the compiler picks the correct version based on what you pass.
- **`void`**: a return type meaning the method produces no value, only side effects (such as
  updating a UI element).
- **`static`**: a method that belongs to the class itself rather than to any instance. Utility
  methods like the currency formatter above are commonly `static` because they do not need
  any per-instance state.

### Pass-by-Value, Always

Java is strictly pass-by-value. For primitives, the value itself is copied into the method's
parameter. For objects, the *reference* is copied, which means the method can mutate the
object the reference points to, but reassigning the parameter inside the method does not
affect the caller's original reference.

```java
public static void appendExclamation(StringBuilder builder) {
    builder.append("!");
}
```

This function mutates the caller's `StringBuilder` because both the caller and the method
hold a reference to the same heap object. Understanding this precisely is essential once you
start passing `Context`, `View`, or your own model objects into methods in the Android app
below.

## 5. Bridging to Android: What This Module's App Demonstrates

The `android_app/` project in this module is a single-screen app with:

- A `TextView` whose text is generated at runtime using `String.format` and primitive
  arithmetic (mirroring the currency formatter above).
- An `EditText` for numeric input and a `Button` whose `onClick` handler runs `if`/`else`
  validation logic identical in structure to the `pure_java` control-flow examples.
- A method, `computeDiscountedPrice(double price, int discountPercent)`, that is called
  directly from the click handler, demonstrating that an Android callback is just a normal
  Java method invocation triggered by the framework.
- A `switch` statement that selects a message string based on a computed grade band, and
  displays the result conditionally, directly parallel to the `switch` example above.

Nothing in the Android app uses any concept beyond what is taught in this module. Classes,
inheritance, interfaces, and collections are intentionally deferred to Modules 02 and 03 so
that you can see fundamentals in isolation before the object-oriented layer is added on top.
