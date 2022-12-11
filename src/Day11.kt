import java.lang.RuntimeException

fun main() {

    data class Monkey(
        val list: MutableList<MutableList<Long>>,
        var op: String,
        val extraOp: (Monkey, Long) -> Long,
        val divide: Int,
        val success: Int,
        val fail: Int,
    ) {
        var count: Long = 0

        fun check(index: Int, monkeys: List<Monkey>) {
            while (list.size > 0) {
                count++
                val values = list.removeLast()
                for (i in monkeys.indices) {
                    values[i] = extraOp(monkeys[i], operate(values[i]))
                }
                monkeys[
                        if (values[index] % divide == 0L) {
                            success
                        } else {
                            fail
                        }
                ].list.add(values)
            }
        }

        private fun operate(value: Long): Long {
            val list = op.split(" ")
            val first = if (list[0] == "old") value else list[0].toLong()
            val sec = if (list[2] == "old") value else list[2].toLong()
            return when (list[1]) {
                "+" -> first + sec
                "-" -> first - sec
                "*" -> first * sec
                "/" -> first / sec
                else -> throw RuntimeException("illegal op")
            }
        }
    }

    fun buildMonkeys(input: List<String>, extraOp: (Monkey, Long) -> Long): List<Monkey> {
        val size = input.size / 7 + 1
        return input.windowed(6, 7)
            .map { lines ->
                val list = lines[1].substringAfter(": ").split(", ").map {
                    val value = it.toLong()
                    MutableList(size) { value }
                }.toMutableList()
                val op = lines[2].substringAfter("new = ")
                val d = lines[3].substringAfter("by ").toInt()
                val s = lines[4].substringAfterLast(" ").toInt()
                val f = lines[5].substringAfterLast(" ").toInt()
                Monkey(list, op, extraOp, d, s, f)
            }.toMutableList()
    }

    fun part1(input: List<String>): Long {
        val monkeys = buildMonkeys(input) { _: Monkey, l: Long ->
            l / 3
        }
        repeat(20) {
            monkeys.forEachIndexed { index, monkey ->
                monkey.check(index, monkeys)
            }
        }
        val list = monkeys.sortedByDescending { it.count }
        return list[0].count * list[1].count
    }


    fun part2(input: List<String>): Long {
        val monkeys = buildMonkeys(input) { monkey: Monkey, l: Long ->
            l % monkey.divide
        }
        repeat(10000) {
            monkeys.forEachIndexed { index, monkey ->
                monkey.check(index, monkeys)
            }
        }
        val list = monkeys.sortedByDescending { it.count }
        return list[0].count * list[1].count
    }

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
