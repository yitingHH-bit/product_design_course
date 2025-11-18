open class Employee(
    protected val name: String,
    protected val baseSalary: Double
) {
    open fun calculateSalary(): Double {
        return baseSalary
    }

    fun getName(): String {
        return name
    }
}

class FullTimeEmployee(
    name: String,
    baseSalary: Double,
    private val bonus: Double
) : Employee(name, baseSalary) {

    override fun calculateSalary(): Double {
        return baseSalary + bonus
    }
}

class PartTimeEmployee(
    name: String,
    private val hourlyRate: Double,
    private val hoursWorked: Int
) : Employee(name, 0.0) { // baseSalary not used directly here

    override fun calculateSalary(): Double {
        return hourlyRate * hoursWorked
    }
}

fun main() {
    // Create some employees
    val emp1 = FullTimeEmployee("Alice Johnson", 3000.0, 500.0)
    val emp2 = FullTimeEmployee("Bob Smith", 3200.0, 750.0)
    val emp3 = PartTimeEmployee("Charlie Brown", 20.0, 80)
    val emp4 = PartTimeEmployee("Diana White", 18.0, 60)

    // Use mapOf to store employees and their calculated salaries
    val employeeSalaries = mapOf(
        emp1.getName() to emp1.calculateSalary(),
        emp2.getName() to emp2.calculateSalary(),
        emp3.getName() to emp3.calculateSalary(),
        emp4.getName() to emp4.calculateSalary()
    )

    // Loop through the map and display each employee's salary
    println("Employee salaries:")
    for ((employeeName, salary) in employeeSalaries) {
        println("$employeeName: ${"%.2f".format(salary)}")
    }
}

