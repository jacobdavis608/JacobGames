#!/bin/sh
javac -d ./practice/classes ./practice/Node.java
javac -d ./practice/classes ./practice/DoublyLinkedList.java
javac -d ./practice/classes ./practice/Main.java
java -classpath ./practice/classes practice.Main