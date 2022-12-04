import kotlin.math.max
import kotlin.math.min

fun main() {

    fun String.toPoints(): Pair<List<Int>, List<Int>> {
        val pair = this.split(",")
        val a = pair[0].split("-").map { it.toInt() }
        val b = pair[1].split("-").map { it.toInt() }
        return Pair(a, b)
    }

    fun part1(input: List<String>): Int {
        return input.map(String::toPoints)
            .count { (a, b) ->
                max(a[1] - a[0] + 1, b[1] - b[0] + 1) == max(a[1], b[1]) - min(a[0], b[0]) + 1
            }
    }

    fun part2(input: List<String>): Int {
        return input.map(String::toPoints)
            .count { (a, b) ->
                a[1] - a[0] + 1 + b[1] - b[0] + 1 > max(a[1], b[1]) - min(a[0], b[0]) + 1
            }
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
