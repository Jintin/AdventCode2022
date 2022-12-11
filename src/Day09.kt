import kotlin.math.abs

fun main() {
    fun buildSnack(input: List<String>, length: Int): Int {
        val list = mutableListOf<Pair<Int, Int>>()
        repeat(length) {
            list.add(Pair(0, 0))
        }
        val visit = mutableSetOf<Pair<Int, Int>>()
        visit.add(Pair(0, 0))
        input.forEach {
            val command = it.split(" ")
            val count = command[1].toInt()
            repeat(count) {
                list[0] = when (command[0]) {
                    "R" -> Pair(list[0].first + 1, list[0].second)
                    "L" -> Pair(list[0].first - 1, list[0].second)
                    "U" -> Pair(list[0].first, list[0].second + 1)
                    "D" -> Pair(list[0].first, list[0].second - 1)
                    else -> list[0]
                }

                for (i in 1 until list.size) {
                    val h = list[i - 1]
                    var t = list[i]
                    if (abs(h.first - t.first) + abs(h.second - t.second) > 2) {
                        t = Pair(
                            t.first - if (h.first < t.first) 1 else -1,
                            t.second - if (h.second < t.second) 1 else -1
                        )
                    } else if (abs(h.first - t.first) > 1) {
                        t = Pair(t.first - if (h.first < t.first) 1 else -1, t.second)
                    } else if (abs(h.second - t.second) > 1) {
                        t = Pair(t.first, t.second - if (h.second < t.second) 1 else -1)
                    }
                    list[i] = t
                }
                visit.add(list[list.size - 1])
            }
        }
        return visit.size
    }
    fun part1(input: List<String>): Int {
        return buildSnack(input, 2)
    }

    fun part2(input: List<String>): Int {
        return buildSnack(input, 10)
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
