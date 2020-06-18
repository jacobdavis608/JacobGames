#!/bin/sh
javac -d ./snakecob/classes ./snakecob/SnakeSegment.java
javac -d ./snakecob/classes ./snakecob/Snack.java
javac -d ./snakecob/classes -classpath ./snakecob/classes ./snakecob/GamePanel.java
javac -d ./snakecob/classes -classpath ./snakecob/classes ./snakecob/Main.java
java -classpath ./snakecob/classes snakecob.Main