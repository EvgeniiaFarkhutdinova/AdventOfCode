import java.io.File
import java.util.*
import kotlin.math.abs

fun main() {
    val scanner = Scanner(File("src/inputs/7.txt"))
    scanner.useDelimiter(",")
    var crab = mutableListOf<Int>()
    while (scanner.hasNext()) {
        crab.add(scanner.nextInt())
    }

    val max = crab.max()!!
    val groupedInput = crab.groupBy { it }.mapValues { it.value.count() }

    val  fuelConsumption =  (0..max).map {
        it to groupedInput.map { (i, count) ->
            // sum of arithmetic progression with first value of 1 and common difference of 1
            val cost = (abs(i - it).toDouble() / 2) * (2 + (abs(i - it) - 1) * 1)
            (cost * count).toInt()
        }   .sum()
    }

    println("Fuel consumption is ${fuelConsumption.minBy { it.second }!!}")
}