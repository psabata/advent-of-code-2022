fun main() {

    data class Monkey(
        val items: MutableList<Int>,
        val test: Int,
        val testTrue: Int,
        val testFalse: Int,
        var inspected: Int = 0,
        val operation: (item: Long) -> Long,
    ) {

        fun inspect(monkeys: List<Monkey>, worryReduce: Int = 1, modulus: Int) {

            for (item in items) {
                val newItem = ((operation(item.toLong()) / worryReduce) % modulus).toInt()

                if (newItem % test == 0) {
                    monkeys[testTrue].items.add(newItem)
                } else {
                    monkeys[testFalse].items.add(newItem)
                }

                inspected++
            }

            items.clear()
        }

    }

    fun testInput() = listOf(
        Monkey( // Monkey 0
            mutableListOf(79, 98),
            23,
            2,
            3
        ) { item ->
            item * 19
        },
        Monkey( // Monkey 1
            mutableListOf(54, 65, 75, 74),
            19,
            2,
            0
        ) { item ->
            item + 6
        },
        Monkey( // Monkey 2
            mutableListOf(79, 60, 97),
            13,
            1,
            3
        ) { item ->
            item * item
        },
        Monkey( // Monkey 3
            mutableListOf(74),
            17,
            0,
            1
        ) { item ->
            item + 3
        },
    )

    fun input() = listOf(
        Monkey(
            // Monkey 0
            mutableListOf(73, 77),
            11,
            6,
            5,
        ) { old ->
            old * 5
        },
        Monkey(
            // Monkey 1
            mutableListOf(57, 88, 80),
            19,
            6,
            0,
        ) { old ->
            old + 5
        },
        Monkey(
            // Monkey 2
            mutableListOf(61, 81, 84, 69, 77, 88),
            5,
            3,
            1,
        ) { old ->
            old * 19
        },
        Monkey(
            // Monkey 3
            mutableListOf(78, 89, 71, 60, 81, 84, 87, 75),
            3,
            1,
            0,
        ) { old ->
            old + 7
        },
        Monkey(
            // Monkey 4
            mutableListOf(60, 76, 90, 63, 86, 87, 89),
            13,
            2,
            7,
        ) { old ->
            old + 2
        },
        Monkey(
            // Monkey 5
            mutableListOf(88),
            17,
            4,
            7,
        ) { old ->
            old + 1
        },
        Monkey(
            // Monkey 6
            mutableListOf(84, 98, 78, 85),
            7,
            5,
            4,
        ) { old ->
            old * old
        },
        Monkey(
            // Monkey 7
            mutableListOf(98, 89, 78, 73, 71),
            2,
            3,
            2,
        ) { old ->
            old + 4
        },
    )

    fun List<Monkey>.simulate(rounds: Int, worryReduce: Int): Long {

        val modulus = this.fold(1) { acc, monkey -> acc * monkey.test }

        for (round in 1..rounds) {

            forEach { monkey ->
                monkey.inspect(this, worryReduce, modulus)
            }

        }

        return this.map { it.inspected }.sorted().takeLast(2).fold(1L) { acc, item -> acc * item }
    }

    check(testInput().simulate(20, 3) == 10605L)
    check(testInput().simulate(10000, 1) == 2713310158L)

    println("Part 1: ${input().simulate(20, 3)}")
    println("Part 2: ${input().simulate(10000, 1)}")

}