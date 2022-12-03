import java.lang.Integer.max

fun main() {
    fun part1(input: List<String>): Int {
        var max = 0
        input.map {
            it.toIntOrNull() ?: 0
        }.reduce { acc, i ->
            if (i == 0) {
                max = max(max, acc)
                0
            } else {
                acc + i
            }
        }
        return max
    }

    fun part2(input: List<String>): Int {
        val list = mutableListOf<Int>()
        input.map {
            it.toIntOrNull() ?: 0
        }.toMutableList().also {
            it.add(0)
        }.reduce { acc, i ->
            if (i == 0) {
                list.add(acc)
                0
            } else {
                acc + i
            }
        }
        list.sortDescending()
        return list.take(3).sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
