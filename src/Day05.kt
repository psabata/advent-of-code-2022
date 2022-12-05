fun main() {

    fun part1(stacks: MutableList<MutableList<Char>>, instructions: List<Instruction>): String {
        instructions.forEach { instruction ->
            instruction.execute1(stacks)
        }

        return stacks.fold("") { acc, stack -> acc + (stack.lastOrNull() ?: "") }
    }

    fun part2(stacks: MutableList<MutableList<Char>>, instructions: List<Instruction>): String {
        instructions.forEach { instruction ->
            instruction.execute2(stacks)
        }

        return stacks.fold("") { acc, stack -> acc + (stack.lastOrNull() ?: "") }
    }

    fun test() {
        val input = InputHelper("Day05_test.txt").readStringGroups()

        val stacks = input[0].split(System.lineSeparator()).dropLast(1)
        val crane = input[1].split(System.lineSeparator()).drop(1)

        val instructions = parseCrane(crane)

        check(part1(parseStacks(stacks), instructions) == "CMZ")
        check(part2(parseStacks(stacks), instructions) == "MCD")
    }

    fun prod() {
        val input = InputHelper("Day05.txt").readStringGroups()

        val stacks = input[0].split(System.lineSeparator()).dropLast(1)
        val crane = input[1].split(System.lineSeparator()).drop(1)

        val instructions = parseCrane(crane)

        println("Part 1: ${part1(parseStacks(stacks), instructions)}")
        println("Part 2: ${part2(parseStacks(stacks), instructions)}")
    }

    test()
    prod()
}

private fun parseStacks(crates: List<String>): MutableList<MutableList<Char>> {
    val stacks: MutableList<MutableList<Char>> = mutableListOf()

    crates.reversed().forEach { row ->
        row.forEachIndexed { index, char ->
            if (char.isLetter()) {
                val stackIndex = (index - 1) / 4 + 1

                if (stackIndex >= stacks.size) {
                    stacks.add(mutableListOf())
                }

                stacks[stackIndex - 1].add(char)
            }
        }
    }

    return stacks
}

private fun parseCrane(crane: List<String>): List<Instruction> {
    val instructions = crane.map { instruction ->
        val values = Regex("\\D*(\\d+)\\D*(\\d+)\\D*(\\d+)").matchEntire(instruction)!!.groupValues

        Instruction(values[1].toInt(), values[2].toInt(), values[3].toInt())
    }

    return instructions
}

data class Instruction(
    val howMany: Int,
    val from: Int,
    val to: Int,
) {

    fun execute1(stacks: MutableList<MutableList<Char>>) {
        for (i in 1..howMany) {
            val crate = stacks[from - 1].removeLast()
            stacks[to - 1].add(crate)
        }
    }

    fun execute2(stacks: MutableList<MutableList<Char>>) {
        val crates: MutableList<Char> = mutableListOf()

        for (i in 1..howMany) {
            crates.add(stacks[from - 1].removeLast())
        }

        stacks[to - 1].addAll(crates.reversed())
    }

}