fun main() {
    // Create a list of integers 1 to 100
    val numbers = (1..100).toList()

    // Filter even numbers
    val evens = numbers.filter { it % 2 == 0 }

    // Map to squares
    val squares = numbers.map { it * it }

    // Reduce to sum
    val sum = numbers.reduce { acc, n -> acc + n }

    // Print results
    println("Evens:")
    println(evens)

    println("\nSquares:")
    println(squares)

    println("\nSum:")
    println(sum)
}

