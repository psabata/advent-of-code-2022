fun main() {

    fun part1(input: List<Entry>): Int {

        return input.count {
            it.range1.contains(it.range2) || it.range2.contains(it.range1)
        }

    }

    fun part2(input: List<Entry>): Int {

        return input.count {
            it.range1.overlaps(it.range2) || it.range2.overlaps(it.range1)
        }

    }

    val testInput = InputHelper("Day04_test.txt").readLines { parse(it) }
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = InputHelper("Day04.txt").readLines { parse(it) }
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

private fun parse(it: String): Entry {
    val result = Regex("^(\\d+)-(\\d+),(\\d+)-(\\d+)\$").matchEntire(it)!!.groupValues

    return Entry(
        IntRange(result[1].toInt(), result[2].toInt()),
        IntRange(result[3].toInt(), result[4].toInt()),
    )
}

data class Entry(
    val range1: IntRange,
    val range2: IntRange,
)

fun IntRange.contains(other: IntRange): Boolean {
    return contains(other.first) && contains(other.last)
}

fun IntRange.overlaps(other: IntRange): Boolean {
    return contains(other.first) || contains(other.last)
}


