HiltIncrementality
===

## Overview

This project demonstrates an issue with Hilt's incremental annotation processing. It consists of
two Gradle modules:
- `:app`: The application, which is just a single activity with a `TextView` which `toString`'s the
injected dependencies. See [`MainActivity.kt`](app/src/main/java/com/example/hiltincrementality/MainActivity.kt).
- `:library`: A library which provides multiple implementations of `CoffeeMaker` using multi-
bindings. See [`Coffee.kt`](library/src/main/java/com/example/library/Coffee.kt).

## Issue

The issue has something to do with failing to regenerate code after making changes in `library`. To
start, build the app from clean:

```
./gradlew :app:assembleDebug
```

This should build successfully. Then, uncomment the `val water: Water` lines from [`Coffee.kt`](library/src/main/java/com/example/library/Coffee.kt), and
build `:app:assembleDebug` again. The expected behavior is that this builds successfully. Instead,
the build will fail with:

```
/Users/damian/Development/HiltIncrementality/app/build/generated/source/kapt/debug/com/example/hiltincrementality/DaggerApp_HiltComponents_SingletonC.java:61: error: constructor DripCoffeeMaker in class DripCoffeeMaker cannot be applied to given types;
          local = new DripCoffeeMaker();
                  ^
  required: Water
  found: no arguments
  reason: actual and formal argument lists differ in length
/Users/damian/Development/HiltIncrementality/app/build/generated/source/kapt/debug/com/example/hiltincrementality/DaggerApp_HiltComponents_SingletonC.java:75: error: constructor PourOverCoffeeMaker in class PourOverCoffeeMaker cannot be applied to given types;
          local = new PourOverCoffeeMaker();
                  ^
  required: Water
  found: no arguments
  reason: actual and formal argument lists differ in length
/Users/damian/Development/HiltIncrementality/app/build/generated/source/kapt/debug/com/example/hiltincrementality/DaggerApp_HiltComponents_SingletonC.java:89: error: constructor EspressoCoffeeMaker in class EspressoCoffeeMaker cannot be applied to given types;
          local = new EspressoCoffeeMaker();
                  ^
  required: Water
  found: no arguments
  reason: actual and formal argument lists differ in length
```

Cleaning (either which Gradle or `rm -rf app/build`) will fix the problem (allowing the app to build
again) at the loss of incrementality. The same problem now exists in reverse, if you were to re-
comment the `val water: Water` lines.
