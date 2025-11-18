# Kotlin Employee Salary Calculation

This project demonstrates basic object-oriented programming and collections in Kotlin.

## Features

- Base class `Employee` with:
  - Protected `name`
  - Protected `baseSalary`
  - `open fun calculateSalary()` that returns the base salary

- `FullTimeEmployee` subclass:
  - Inherits from `Employee`
  - Adds a `bonus`
  - Overrides `calculateSalary()` to return `baseSalary + bonus`

- `PartTimeEmployee` subclass:
  - Inherits from `Employee`
  - Uses `hourlyRate` and `hoursWorked`
  - Overrides `calculateSalary()` to return `hourlyRate * hoursWorked`

- Uses `mapOf` to store employee names and their calculated salaries
- Loops through the map and prints each employee's salary

## Files

- `Main.kt` — main Kotlin source file with classes and `main` function
- `output.txt` — sample program output
- `README.md` — project description
