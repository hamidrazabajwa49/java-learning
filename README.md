# Java for Android Development: A Concept-to-Application Curriculum

This repository is a self-paced curriculum for engineers who already understand programming
basics and want to master Java specifically as the language underneath native Android
development. Every module follows the same rule: no Java concept is taught in isolation.
Each one is immediately mapped to the exact Android API or pattern that depends on it.

## Why This Structure Exists

Most Java tutorials teach the language as if it runs on a server or a desktop JVM. Android
does not use a standard JVM (it uses ART, the Android Runtime), and the Android framework
imposes constraints that change how you should think about ordinary Java features:

- Object allocation matters because mobile devices have limited RAM and aggressive garbage
  collection pauses are visible to the user as UI jank.
- Threading matters because the OS kills your app if the main thread is blocked for more
  than a few seconds (Application Not Responding).
- Interfaces matter because the entire Android event system (clicks, lifecycle callbacks,
  adapter binding) is built on top of them.
- Exceptions matter because a single uncaught exception crashes the whole app instantly,
  unlike a server process that can isolate a failing request.

This curriculum treats Java syntax as the foundation and the Android framework as the proof
that the foundation matters.

## Prerequisites

- A working knowledge of at least one procedural or object-oriented language (Python, C++,
  Kotlin, C#, etc.). You do not need prior Java experience.
- JDK 17 installed and on your PATH (`java -version` should report 17.x).
- Android Studio (Koala or newer) with an Android Virtual Device (API 34, "UpsideDownCake" or
  later) configured, or a physical device with USB debugging enabled.
- Basic command line familiarity (`cd`, running `javac`/`java`, running Gradle wrapper
  scripts).

## Curriculum Map

| Module | Java Concepts | Android Payoff |
|---|---|---|
| [`01_java_fundamentals_to_ui`](./01_java_fundamentals_to_ui) | Primitive types, operators, control flow, methods, method overloading | Building dynamic UI text, conditional layout logic, validating basic user input |
| [`02_oop_to_components`](./02_oop_to_components) | Classes, constructors, inheritance, polymorphism, interfaces, encapsulation, abstract classes | Custom Views, `AppCompatActivity` subclassing, `View.OnClickListener` |
| [`03_collections_to_lists`](./03_collections_to_lists) | `List`, `Map`, `Set`, generics, iterators, comparators | `RecyclerView`, `Adapter`/`ViewHolder` pattern, `DiffUtil` |
| [`04_exceptions_and_memory`](./04_exceptions_and_memory) | `try`/`catch`/`finally`, custom exceptions, checked vs. unchecked, garbage collection, `WeakReference` | Form validation, API error handling, preventing `Context` leaks |
| [`05_concurrency_to_background`](./05_concurrency_to_background) | `Thread`, `Runnable`, `ExecutorService`, thread pools, callback interfaces | Offloading work from the UI thread, posting results back with `Handler` |

Modules are ordered so that each one depends only on concepts introduced earlier. Module 01
assumes no prior Java knowledge. Module 05 assumes you are comfortable with interfaces
(Module 02) and collections (Module 03), since real background work usually processes a
list of results.

## How Each Module Is Organized

Every module folder follows an identical layout so that navigation is predictable:

```
0X_module_name/
    CONCEPT_GUIDE.md      Full tutorial: syntax, mechanics, and the Android link
    pure_java/             Standalone .java files, runnable with javac/java, no Android SDK needed
    android_app/           A minimal, complete Android Studio project demonstrating the concept live
```

### Recommended Workflow Per Module

1. Read `CONCEPT_GUIDE.md` fully before touching code.
2. Compile and run every file in `pure_java/` from the command line. Confirm you understand
   the output before moving on. These files require nothing but a JDK.
3. Open `android_app/` in Android Studio, run it on an emulator or device, and trace how the
   exact same Java mechanics from step 2 are now driving a real UI.
4. Modify the Android app in small ways (change a value, add a branch) and re-run to confirm
   your mental model of the underlying Java behavior is correct.

## Running the `pure_java` Files

Each `pure_java` folder is a flat set of standalone `.java` files with a `main` method. From
inside the folder:

```bash
javac FileName.java
java FileName
```

No build tool or external dependency is required for these files.

## Running the `android_app` Projects

Each `android_app` folder is a complete, independent Gradle project targeting Java 17 source
compatibility and Android API 34. Open the folder directly in Android Studio ("Open" not
"Import"), let Gradle sync, then run the `app` configuration on an emulator or device running
API 24 or higher (`minSdk 24`, `targetSdk 34`, `compileSdk 34`).

## License

This curriculum is provided for educational use.
