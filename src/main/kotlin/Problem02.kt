import Hand.*
import Game.*
import java.lang.IllegalArgumentException

enum class Game { Win, Loose, Draw }

enum class Hand { Rock, Paper, Scissors }

data class Rule(val player1: Hand, val player2: Hand, val outcome: Game)
fun main() {
    val text = getResourceAsText("input02.txt")

    val opponentMap = mapOf("A" to Rock, "B" to Paper, "C" to Scissors)
    val playerMap = mapOf("X" to Rock, "Y" to Paper, "Z" to Scissors)

    val playbook =
        Hand.values().flatMap { x -> Hand.values().map { y -> x to y } }.map { (x, y) -> Rule(x, y, y.vs(x)) }

    val lines = text.lines().map { line ->
        line.split(" ")
            .map { it.trim() }
    }

    val sumOfPoints = lines
        .map { (p1, p2) -> opponentMap[p1]!! to playerMap[p2]!! }
        .sumOf { (elf, you) -> calcPoints(elf, you) }

    println("Part1 = $sumOfPoints")

    val strategy2Points = lines
        .map { (p1, p2) ->
            opponentMap[p1]!! to when (p2) {
                "X" -> Loose
                "Y" -> Draw
                "Z" -> Win
                else -> throw IllegalArgumentException(p2)
            }
        }
        .map { (p1, outcome) ->
            p1 to playbook.find { rule -> rule.player1 == p1 && rule.outcome == outcome }!!.player2
        }.sumOf { (elf, you) -> calcPoints(elf, you) }

    println("Part2 = $strategy2Points")
}


private fun calcPoints(elf: Hand, you: Hand): Int {
    return when (you.vs(elf)) {
        Win -> 6
        Draw -> 3
        Loose -> 0
    } + when (you) {
        Rock -> 1
        Paper -> 2
        Scissors -> 3
    }
}

private fun Hand.vs(other: Hand): Game {
    return when {
        this == other -> Draw
        this == Rock && other == Scissors -> Win
        this == Scissors && other == Paper -> Win
        this == Paper && other == Rock -> Win
        else -> Loose
    }
}

