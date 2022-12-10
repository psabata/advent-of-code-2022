fun main() {

    val testInput = Day10.Computer(InputHelper("Day10_test.txt").readLines())
    check(testInput.part1() == 13140)
    check(testInput.part2() == 0)

    val input = Day10.Computer(InputHelper("Day10.txt").readLines())
    println("Part 1: ${input.part1()}")
    println("Part 2: ${input.part2()}")

}

object Day10 {

    class Computer(input: List<String>) {

        private val operations: List<Operation>
        private val sampleTime = listOf(20, 60, 100, 140, 180, 220)
        private var x = 1
        private var timer: Int = 0
        private val signalStrengths: MutableList<Int> = mutableListOf()
        private val screen: Screen = Screen()

        init {
            operations = input.map {
                when {
                    it.startsWith("noop") -> {
                        Operation.Noop()
                    }
                    it.startsWith("addx") -> {
                        Operation.Addx(it.split(' ')[1].toInt())
                    }
                    else -> throw IllegalArgumentException(it)
                }
            }
        }

        fun part1(): Int {
            reset()
            return execute()
        }

        fun part2(): Int {
            reset()
            execute()
            screen.print()
            return 0
        }

        private fun reset() {
            x = 1
            timer = 0
            signalStrengths.clear()
            screen.reset()
        }

        private fun execute(): Int {
            for (operation in operations) {

                for (tick in 1..operation.cycleTime) {
                    timer++

                    if (sampleTime.contains(timer)) {
                        signalStrengths.add(timer * x)
                    }

                    screen.draw(timer, x)
                }

                x += operation.execute()
            }

            return signalStrengths.sum()
        }
    }

    sealed class Operation(val cycleTime: Int) {

        open fun execute(): Int = 0

        class Noop: Operation(1)

        class Addx(private val argument: Int) : Operation(2) {
            override fun execute(): Int {
                return argument
            }
        }

    }

    class Screen {
        private val screenWidth: Int = 40
        private val screenHeight: Int = 6
        private val pixels = Array(screenHeight) { CharArray(screenWidth) }

        fun reset() {
            pixels.forEach { it.fill('.') }
        }

        fun draw(timer: Int, sprite: Int) {
            val row = timer / screenWidth % screenHeight
            val column = (timer-1) % screenWidth
            val pixel = if (column in sprite - 1 .. sprite + 1)  '#' else '.'

//            println("timer: $timer, row: $row, column: $column, sprite: $sprite, pixel: $pixel")

            if (pixel == '#') {
                pixels[row][column] = pixel
            }
        }

        fun print() {
            pixels.forEach { println(it) }
        }
    }


}