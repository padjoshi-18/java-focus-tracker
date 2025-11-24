# Focus Session Tracker

## Overview
Java CLI application for tracking focus sessions and breaking distraction cycles.

## Quick Start
1. Download sqlite-jdbc-3.42.0.0.jar
2. javac -cp ".;sqlite-jdbc-3.42.0.0.jar" FocusTracker.java
3. java -cp ".;sqlite-jdbc-3.42.0.0.jar" FocusTracker

## Features
- Session timing with task descriptions
- Energy level tracking (1-5)
- Session history with SQLite storage
- Modular Java architecture

## Project Structure
- FocusTracker.java (Single-file executable)
- src/ (Multi-package version)
- pom.xml (Maven configuration)
