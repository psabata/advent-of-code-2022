import kotlin.math.max

fun main() {

    fun part1(input: List<List<Int>>): Int {

        var visible = 0

        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, _ ->
                if (input.isVisible(x, y)) visible++
            }
        }

        return visible

    }

    fun part2(input: List<List<Int>>): Int {

        var scenicScore = 0

        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, _ ->
                scenicScore = max(input.scenicScore(x, y), scenicScore)
            }
        }

        return scenicScore

    }

    val testInput = InputHelper("Day08_test.txt").readLines { line -> line.map { it.digitToInt() } }
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = InputHelper("Day08.txt").readLines { line -> line.map { it.digitToInt() } }
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun List<List<Int>>.scenicScore(x: Int, y: Int): Int {

    val scenicScore = scenicScore(x, y, Direction.UP) *
            scenicScore(x, y, Direction.DOWN) *
            scenicScore(x, y, Direction.LEFT) *
            scenicScore(x, y, Direction.RIGHT)

//    println("x: $x, y: $y, tree: ${this[y][x]}, scenicScore: $scenicScore")

    return scenicScore
}

fun List<List<Int>>.isVisible(x: Int, y: Int): Boolean {
    val isVisible = isVisible(x, y, Direction.UP)
            || isVisible(x, y, Direction.DOWN)
            || isVisible(x, y, Direction.LEFT)
            || isVisible(x, y, Direction.RIGHT)

//    println("x: $x, y: $y, tree: ${this[y][x]}, visible: $isVisible")

    return isVisible
}

private fun List<List<Int>>.isVisible(
    x: Int,
    y: Int,
    direction: Direction,
): Boolean {
    var newX = x
    var newY = y

    do {
        newX += direction.dx
        newY += direction.dy

        if (newY in indices && newX in this[newY].indices) {
            if (this[newY][newX] >= this[y][x]) return false
        } else {
            return true
        }
    } while (true)
}

private fun List<List<Int>>.scenicScore(
    x: Int,
    y: Int,
    direction: Direction,
): Int {
    var newX = x
    var newY = y
    var scenicScore = 0

    do {
        newX += direction.dx
        newY += direction.dy

        if (newY in indices && newX in this[newY].indices) {
            scenicScore++
            if (this[newY][newX] >= this[y][x]) return scenicScore
        } else {
            return scenicScore
        }
    } while (true)
}

enum class Direction(val dx: Int, val dy: Int) {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0)
}