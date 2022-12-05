import java.util.regex.Pattern

fun main() {
    solution1()
    solution2()
}

private fun solution1() {
    val (towers: Array<ArrayDeque<String>>, parsedMoves) = getTowersAndMoves()

    for ((count, from, to) in parsedMoves) {
        repeat(count) {
            towers[to - 1].addLast(towers[from - 1].removeLast())
        }
    }
    val sol1 = towers.joinToString(separator = "") { deq -> deq.last() }

    println("Solution = $sol1")
}

private fun solution2() {
    val (towers: Array<ArrayDeque<String>>, parsedMoves) = getTowersAndMoves()

    for ((count, from, to) in parsedMoves) {
        printTowers(towers)
        println("move $count from $from to $to")
        val movedBoxes = (1..count).map { towers[from - 1].removeLast() }
        towers[to - 1].addAll(movedBoxes.reversed())

    }
    val sol1 = towers.joinToString(separator = "") { deq -> deq.last() }

    println("Solution = $sol1")
}

private fun getTowersAndMoves(): Pair<Array<ArrayDeque<String>>, List<List<Int>>> {
    val text = readContent("input05.txt")

    val (initialTowers, moves) = text.split("\r\n\r\n")

    val (maxArray, parsed) = parsed(initialTowers)

    val towers: Array<ArrayDeque<String>> = Array(maxArray) { ArrayDeque() }

    for (towerLine in parsed) {
        for ((index, str) in towerLine.withIndex()) {
            if (str.isNotBlank()) {
                towers[index].add(str)
            }
        }
    }

    val commandPattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)")!!
    val parsedMoves = moves.lines().map { move ->
        val matcher = commandPattern.matcher(move)
        assert(matcher.matches())
        with(matcher) {
            (1..3).map {
                group(it).toInt()
            }
        }
    }
    return Pair(towers, parsedMoves)
}

private fun parsed(initialTowers: String): Pair<Int, List<List<String>>> {
    val reversedTowers = initialTowers.lines().reversed()

    val maxArray = reversedTowers[0].trim().split(Pattern.compile("\\s+")).count()

    val parsed = reversedTowers.subList(1, reversedTowers.size)
        .map { line -> line.windowed(3, 4) { it[1].toString() } }
    return Pair(maxArray, parsed)
}

private fun printTowers(towers: Array<ArrayDeque<String>>) {
    println("---")
    println(towers.joinToString(separator = "\n"))
}