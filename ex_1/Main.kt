fun main() {
    val primes = mutableListOf<Int>()
    var number = 2
    while (primes.size < 50) {
        if (isPrime(number)) primes.add(number)
        number++
    }
    println("Primes: $primes")

    val evens = (1..50).map { it * 2 }
    println("Evens: $evens")

    // 
    primes.addAll(evens)
    println("After addAll (primes mutated): $primes")
}

fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (i in 2..Math.sqrt(n.toDouble()).toInt()) if (n % i == 0) return false
    return true
}

