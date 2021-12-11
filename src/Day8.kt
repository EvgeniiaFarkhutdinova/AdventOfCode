import java.io.File
import java.util.*

fun main() {
    val scanner = Scanner(File("src/inputs/8.txt"))
    var outputs = mutableListOf<String>()

    while (scanner.hasNext()) {
        outputs.add(scanner.nextLine())
    }
    val counts = outputs
        .map { it.split("|")[1].trim() }
        .map { it.split(" ")
            .count { o -> o.length == 2 || o.length == 3 || o.length == 7 || o.length == 4} }

    println("result is ${counts.sum()}")

    val sum = outputs.map {parseNumbers(it)}.sum()
    println("sum is $sum")
}

fun parseNumbers(line: String): Int {
    val split = line.split(" | ")
    val input = split[0].split(" ")

    val one = input.single { it.length == 2 }
    val four = input.single { it.length == 4 }
    val seven = input.single { it.length == 3 }
    val eight = input.single { it.length == 7 }

    val nine = input.single { num -> num.length == 6 && num.filter { it !in seven }.filter { it !in four }.length == 1 }

    val six = input.single { it.length == 6 && it != nine && one.filter { it1 -> it1 !in it }.length == 1 }

    val zero = input.single { it.length == 6 && it != nine && it != six }

    val e = eight.single { it !in nine }
    val c = eight.single { it !in six }
    val f = one.single { it !in arrayOf(c) }

    val five = input.single { it.length == 5 && !it.contains(c) && !it.contains(e) }

    val two = input.single { it.length == 5 && it != five && it.contains(c) && !it.contains(f) }

    val three = input.single { it.length == 5 && it != five && it != two }

    val numbers = arrayOf(zero, one, two, three, four, five, six, seven, eight, nine)

    val numbersString = split[1].split(" ")
        .map { digit ->
            numbers.indices.single { n ->
                numbers[n].length == digit.length &&
                !numbers[n].any { c -> c !in digit } &&
                !digit.any { c -> c !in numbers[n] } } }

    return numbersString.joinToString("").toInt()
}
