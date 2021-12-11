private fun readLn() = readLine()!! // string line
private fun readInt() = readLn().toInt() // single int
private fun readStrings() = readLn().split(" ") // list of strings
private fun readInts() = readStrings().map { it.toInt() }.toMutableList() // list of ints
// println(a.joinToString("\n")) // each element of array/list of a separate line

inline fun <T> MutableList<T>.mapInPlace(mutator: (T)->T) {
    val iterate = this.listIterator()
    while (iterate.hasNext()) {
        val oldValue = iterate.next()
        val newValue = mutator(oldValue)
        if (newValue !== oldValue) {
            iterate.set(newValue)
        }
    }
}

fun main() {
    println("Hello")
}