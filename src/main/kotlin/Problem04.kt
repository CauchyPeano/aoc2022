fun main() {
    val text = getResourceAsText("input04.txt")

    val parsed = text.lines().map(::convertLineToRanges)
        .map { (r1, r2) -> IntRange(r1.first, r1.second) to IntRange(r2.first, r2.second) }
    val solution1 = parsed
        .count { (r1, r2) -> r1.contains(r2) || r2.contains(r1) }

    println("Solution1 = $solution1")

    val solution2 = parsed.count { (r1, r2) -> r1.contains(r2.last) || r1.contains(r2.first) || r2.contains(r1.first) }
    println("Solution2 = $solution2")
}

private fun IntRange.contains(other: IntRange) : Boolean =
    // a........b
    //    x..y
    this.contains(other.first) && this.contains(other.last)


private fun convertLineToRanges(line: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val list = line.split(",")
        .map { range ->
            range.split("-")
                .map { it.toInt() }
        }
        .map { (a, b) -> a to b }

    assert(list.size == 2)

    return list[0] to list[1]
}