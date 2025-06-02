#!/bin/bash

# Default to 10 days if no argument is provided
DAYS=${1:-10}

# Compile the project
mvn clean install

# Run the simulation
java -cp target/test-classes:target/classes com.gildedrose.TexttestFixture "$DAYS"
