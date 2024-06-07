#!/bin/bash

# Compile the Java files
javac -d out -cp "src/test/resources/*:src/main/java:src/test/java:lib/*" $(find src/main/java src/test/java -name "*.java")

# Run the tests
java -cp "out:lib/*" org.junit.platform.console.ConsoleLauncher --scan-class-path
