import Result.*
import Symbol.*
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

fun main() {

    fun part1(input: List<Round1>): Int {
        return input.sumOf { it.evaluate() }
    }

    fun part2(input: List<Round2>): Int {
        return input.sumOf { it.evaluate() }
    }

    val testInput1 = InputHelper("Day02_test.txt").readLines { parse1(it) }
    val testInput2 = InputHelper("Day02_test.txt").readLines { parse2(it) }
    check(part1(testInput1) == 15)
    check(part2(testInput2) == 12)

    val input1 = InputHelper("Day02.txt").readLines { parse1(it) }
    val input2 = InputHelper("Day02.txt").readLines { parse2(it) }
    println("Part 1: ${part1(input1)}")
    println("Part 2: ${part2(input2)}")
}

private fun parse1(input: String) = Round1(input[0].toSymbol(), input[2].toSymbol())
private fun parse2(input: String) = Round2(input[0].toSymbol(), input[2].toResult())

class Round1(
    private val opponent: Symbol,
    private val you: Symbol,
) {
    fun evaluate(): Int {
        return you.value + when(opponent) {
            you -> DRAW.value
            you.beats() -> WIN.value
            you.looses() -> LOOSE.value
            else -> throw IllegalStateException()
        }
    }
}

class Round2(
    private val opponent: Symbol,
    private val result: Result,
) {

    fun evaluate(): Int {
        return result.value + when(result) {
            DRAW -> opponent.value
            WIN -> opponent.looses().value
            LOOSE -> opponent.beats().value
        }
    }
}

enum class Symbol(val value: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3),
    ;

    fun beats(): Symbol =
        when (this) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }

    fun looses(): Symbol =
        this.beats().beats()
}

fun Char.toSymbol(): Symbol {
    return when (this) {
        'A', 'X' -> ROCK
        'B', 'Y' -> PAPER
        'C', 'Z' -> SCISSORS
        else -> throw IllegalArgumentException("Not a valid symbol: $this")
    }
}

enum class Result(val value: Int) {
    LOOSE(0),
    DRAW(3),
    WIN(6),
    ;
}

fun Char.toResult(): Result {
    return when (this) {
        'X' -> LOOSE
        'Y' -> DRAW
        'Z' -> WIN
        else -> throw IllegalArgumentException("Not a valid result: $this")
    }
}
