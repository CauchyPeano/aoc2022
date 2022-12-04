import java.lang.RuntimeException

val lower = ('a'..'z').zip(1..26).toMap()
val upper = ('A'..'Z').zip(27..52).toMap()
val score = lower + upper

fun main() {

    val text = getResourceAsText("input03.txt")

    val sum = text.lines()
        .map { line -> line.substring(0, line.length / 2) to line.substring(line.length / 2, line.length) }
        .map { (s1, s2) -> s1.intersects(s2) }
        .map { if (it.size != 1) throw RuntimeException("shiiii") else it.first() }
        .sumOf { letterScore(it) }

    println("Solution1 = $sum")

    val solution2 = text.lines().map { it.toCharArray().toSet() }
        .windowed(size = 3, step = 3)
        .map { (a, b, c) -> a.intersect(b).intersect(c) }
        .map { set -> if (set.size != 1) throw RuntimeException("hohoho $set") else set.first() }
        .sumOf { letterScore(it) }

    println("Solution2 = $solution2")
}

private fun String.intersects(s2: String) = toCharArray().toSet().intersect(s2.toCharArray().toSet())

private fun letterScore(letter: Char): Int {
    return score[letter]!!
}