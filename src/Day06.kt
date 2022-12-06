fun main() {

    fun part1(input: String): Int {

        for (i in 4 until input.length) {
            if (input.subSequence(i - 4, i).toSet().size == 4) {
                return i
            }
        }

        throw IllegalArgumentException()
    }

    fun part2(input: String): Int {

        for (i in 14 until input.length) {
            if (input.subSequence(i - 14, i).toSet().size == 14) {
                return i
            }
        }

        throw IllegalArgumentException()
    }

    val testInput = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"
    check(part1(testInput) == 10)
    check(part2(testInput) == 29)

    val input = InputHelper("Day06.txt").readText()
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
