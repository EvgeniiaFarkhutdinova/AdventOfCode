package aoc2021

import java.io.File
import java.util.*

fun main() {

    val scanner = Scanner(File("src/aoc2021/inputs/6.txt"))
    scanner.useDelimiter(",")
    val lanternfish = LongArray(9)
    while (scanner.hasNext()) {
        lanternfish[scanner.nextInt()]++
    }

    val t = 256
    for (i in 0 until t) {
        lanternfish[(i + 7) % 9] += lanternfish[i % 9];
    }

    println("after $t days, there are a total of ${lanternfish.sum()} fish")
}