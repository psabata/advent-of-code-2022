fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { rucksack ->
            val size = rucksack.length
            val compartment1 = rucksack.substring(0, size / 2).toSet()
            val compartment2 = rucksack.substring(size / 2, size).toSet()

            val item = compartment1.intersect(compartment2).first()

            item.priority()
        }
    }

    fun part2(input: List<String>): Int {
        return input.withIndex().groupBy(
            keySelector = { it.index / 3 },
            valueTransform = { it.value.toSet() }
        ).values.sumOf { group ->
            val badge = group.fold(group.first()) { acc, bag -> acc.intersect(bag) }.first()

            badge.priority()
        }
    }

    val testInput = InputHelper("Day03_test.txt").readLines()
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = InputHelper("Day03.txt").readLines()
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

private fun Char.priority(): Int =
    if (this.isUpperCase()) {
        52 - ('Z'.code - this.code)
    } else {
        26 - ('z'.code - this.code)
    }
