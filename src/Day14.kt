import kotlin.math.max
import kotlin.math.min

fun main() {
    val factor = 1000

    fun Set<Int>.contains(x: Int, y: Int): Boolean {
        return this.contains(x * factor + y)
    }

    fun MutableSet<Int>.add(x: Int, y: Int) {
        this.add(x * factor + y)
    }

    fun drawX(fill: MutableSet<Int>, start: Int, end: Int, y: Int) {
        for (i in start..end) {
            fill.add(i, y)
        }
    }

    fun drawY(fill: MutableSet<Int>, start: Int, end: Int, x: Int) {
        for (i in start..end) {
            fill.add(x, i)
        }
    }

    fun buildMap(input: List<String>): Pair<MutableSet<Int>, Int> {
        val fill = mutableSetOf<Int>()
        var maxY = 0
//        var minX = Int.MAX_VALUE
//        var maxX = Int.MIN_VALUE
        input.forEach { str ->
            val list = str.split(" -> ").map { pair ->
                pair.split(",").map { value ->
                    value.toInt()
                }
            }
            list.windowed(2) {
                val (x0, y0) = it[0]
                val (x1, y1) = it[1]
                maxY = max(max(y0, y1), maxY)
//                maxX = max(max(x0, x1), maxX)
//                minX = min(min(x0, x1), minX)
                if (x0 == x1) {
                    drawY(fill, min(y0, y1), max(y0, y1), x0)
                } else {
                    drawX(fill, min(x0, x1), max(x0, x1), y0)
                }
            }
        }
        return Pair(fill, maxY)
    }

    fun part1(input: List<String>): Int {
        val (fill, maxY) = buildMap(input)

        var count = 0
        while (true) {
            var (x, y) = Pair(500, 0)
            while (true) {
                if (!fill.contains(x, y + 1)) {
                    y += 1
                } else if (!fill.contains(x - 1, y + 1)) {
                    x -= 1
                    y += 1
                } else if (!fill.contains(x + 1, y + 1)) {
                    x += 1
                    y += 1
                } else {
                    count++
                    fill.add(x, y)
                    break
                }

                if (y > maxY) {
                    return count
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        val (fill, maxY) = buildMap(input)
        var count = 0
        while (true) {
            var (x, y) = Pair(500, 0)
            while (true) {
                if (!fill.contains(x, y + 1)) {
                    y += 1
                } else if (!fill.contains(x - 1, y + 1)) {
                    x -= 1
                    y += 1
                } else if (!fill.contains(x + 1, y + 1)) {
                    x += 1
                    y += 1
                } else {
                    if (fill.contains(x, y)) {
                        return count
                    }
                    count++
                    fill.add(x, y)
                    break
                }
                if (y == maxY + 1) {
                    count++
                    fill.add(x, y)
                    break
                }
            }
        }
    }

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
