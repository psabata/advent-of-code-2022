fun main() {

    fun part1(input: List<List<Int>>): Int {

        return input.maxOf { it.sum() }

    }

    fun part2(input: List<List<Int>>): Int {

        return input.sortedBy { it.sum() }.takeLast(3).sumOf { it.sum() }

    }

    val testInput = InputHelper("Day01_test.txt").readIntGroups()
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = InputHelper("Day01.txt").readIntGroups()
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
