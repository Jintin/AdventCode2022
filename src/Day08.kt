import kotlin.math.max

fun main() {

    fun buildMap(input: List<String>): Array<IntArray> {
        val map: Array<IntArray> = Array(input.size) {
            IntArray(input[0].length)
        }
        input.forEachIndexed { i, s ->
            s.forEachIndexed { j, c ->
                map[i][j] = c - '0'
            }
        }
        return map
    }

    fun part1(input: List<String>): Int {
        val map: Array<IntArray> = buildMap(input)
        val record: Array<BooleanArray> = Array(input.size) {
            BooleanArray(input[0].length)
        }
        var max: Int
        for (i in map.indices) {
            max = -1
            for (j in map[0].indices) {
                if (map[i][j] > max) {
                    record[i][j] = true
                    max = map[i][j]
                }
            }
            max = -1
            for (j in map[0].indices.reversed()) {
                if (map[i][j] > max) {
                    record[i][j] = true
                    max = map[i][j]
                }
            }
        }

        for (j in map[0].indices) {
            max = -1
            for (i in map.indices) {
                if (map[i][j] > max) {
                    record[i][j] = true
                    max = map[i][j]
                }
            }
            max = -1
            for (i in map.indices.reversed()) {
                if (map[i][j] > max) {
                    record[i][j] = true
                    max = map[i][j]
                }
            }
        }
        return record.sumOf { it.count { it } }
    }


    fun part2(input: List<String>): Int {
        val map: Array<IntArray> = buildMap(input)

        var max = 0
        for (i in map.indices) {
            for (j in map[0].indices) {
                var countA = 0
                var countB = 0
                var countC = 0
                var countD = 0
                val target = map[i][j]
                for (a in i - 1 downTo 0) {
                    if (map[a][j] <= target) {
                        countA++
                    }
                    if (map[a][j] >= target) {
                        break
                    }
                }
                for (b in i + 1 until map.size) {
                    if (map[b][j] <= target) {
                        countB++
                    }
                    if (map[b][j] >= target) {
                        break
                    }
                }
                for (c in j - 1 downTo 0) {
                    if (map[i][c] <= target) {
                        countC++
                    }
                    if (map[i][c] >= target) {
                        break
                    }
                }
                for (d in j + 1 until map[0].size) {
                    if (map[i][d] <= target) {
                        countD++
                    }
                    if (map[i][d] >= target) {
                        break
                    }
                }
                max = max(max, countA * countB * countC * countD)
            }
        }
        return max
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
