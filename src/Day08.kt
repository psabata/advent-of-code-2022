import kotlin.math.max

fun main() {

    val testInput = Day08.Forrest(InputHelper("Day08_test.txt").readLines())
    check(testInput.part1() == 21)
    check(testInput.part2() == 8)

    val input = Day08.Forrest(InputHelper("Day08.txt").readLines())
    println("Part 1: ${input.part1()}")
    println("Part 2: ${input.part2()}")

}

object Day08 {

    class Forrest(input: List<String>) {

        private val trees: List<List<Int>> = input.map { line -> line.map { it.digitToInt() } }

        fun part1(): Int {
            var visibleTrees = 0

            trees.forEachIndexed { y, row ->
                row.forEachIndexed { x, _ ->
                    if (trees.isVisible(x, y)) visibleTrees++
                }
            }

            return visibleTrees
        }
        fun part2(): Int {

            var scenicScore = 0

            trees.forEachIndexed { y, row ->
                row.forEachIndexed { x, _ ->
                    scenicScore = max(trees.scenicScore(x, y), scenicScore)
                }
            }

            return scenicScore

        }

    }

    private fun List<List<Int>>.scenicScore(x: Int, y: Int): Int {

        val scenicScore = scenicScore(x, y, Direction.UP) *
                scenicScore(x, y, Direction.DOWN) *
                scenicScore(x, y, Direction.LEFT) *
                scenicScore(x, y, Direction.RIGHT)

//    println("x: $x, y: $y, tree: ${this[y][x]}, scenicScore: $scenicScore")

        return scenicScore
    }

    private fun List<List<Int>>.isVisible(x: Int, y: Int): Boolean {
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
    private enum class Direction(val dx: Int, val dy: Int) {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0)
    }

}