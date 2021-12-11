import java.io.File
import java.util.*

fun main() {
    val scanner = Scanner(File("src/inputs/10.txt"))
    val startSymbols = arrayOf("(","[","{","<")
    val endSymbols = arrayOf(")","]","}",">")
    val errors = mutableMapOf(")" to 0,"]" to 0,"}" to 0,">" to 0)
    val incompleteScores = mutableListOf<Long>()
    while(scanner.hasNextLine()) {
        val starts = ArrayDeque<String>()
        var hasErrors = false
        scanner.nextLine().split("").forEach {
            if (it in startSymbols) starts.push(it)
            if (it in endSymbols) {
                val i = endSymbols.indexOf(it)
                val requiredStart = startSymbols[i]
                val actualStart = starts.pop()
                if (requiredStart != actualStart) {
                    errors[it] = errors[it]!!.plus(1)
                    hasErrors = true
                    return@forEach
                }
            }
        }
        if (!hasErrors) {
            var lineScore = 0L
            while (!starts.isEmpty()) {
                val s = starts.pop()
//                print(s)
                when (s) {
                    "(" -> lineScore = lineScore * 5L + 1L
                    "[" -> lineScore = lineScore * 5L + 2L
                    "{" -> lineScore = lineScore * 5L + 3L
                    "<" -> lineScore = lineScore * 5L + 4L
                }
            }
//            println()
//            println(lineScore)
            incompleteScores.add(lineScore)
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

    println("Syntax error score is $score")

    val middleIndex = incompleteScores.count() / 2

    val sorted = incompleteScores.sorted()

    val middleScore = sorted[middleIndex]

    println("Middle score is $middleScore") // 43704420 is too low

}