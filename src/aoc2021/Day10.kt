package aoc2021

import java.io.File
import java.util.*

fun main() {
    val scanner = Scanner(File("src/aoc2021/inputs/10.txt"))
    val startSymbols = arrayOf("(","[","{","<")
    val endSymbols = arrayOf(")","]","}",">")
    val errors = mutableMapOf(")" to 0,"]" to 0,"}" to 0,">" to 0)
    while(scanner.hasNextLine()) {
        val starts = ArrayDeque<String>()
        scanner.nextLine().split("").forEach {
            if (it in startSymbols) starts.push(it)
            if (it in endSymbols) {
                val i = endSymbols.indexOf(it)
                val requiredStart = startSymbols[i]
                val actualStart = starts.pop()
                if (requiredStart != actualStart) {
                    errors[it] = errors[it]!!.plus(1)
                }
            }
        }
    }
    var score = 0
    errors.forEach{ e ->
        when (e.key) {
            ")" -> score += e.value * 3
            "]" -> score += e.value * 57
            "}" -> score += e.value * 1197
            ">" -> score += e.value * 25137
        }
    }

    println("Error score is $score")
}