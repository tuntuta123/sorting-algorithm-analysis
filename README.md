ALGOTRIS – Sorting Algorithms Project
====================================

Inncludes:
- Random, entropy-based, and reverse-entropy generators
- Performance tracking
- Benchmark execution
- JUnit tests

REQUIREMENTS
------------
- Java JDK 8+
- Apache Ant

USAGE (ANT)
-----------

Compile:
    ant compile

Run application:
    ant run

Run benchmark:
    ant benchmark

Create JAR:
    ant jar

Run tests:
    ant test

Generate javadoc:
    ant javadoc


OUTPUT
------
- Compiled classes → build/
- Executable JAR → dist/algotris.jar
- Documentation → docs/javadoc/


NOTES
-----
- Benchmark configs can be modified in src/benchmark/BenchmarkRunner.java
- After running ant benchmark, the results are exported to results.csv in the project root directory.

MAIN CLASS
----------
view.menu.MainMenu
