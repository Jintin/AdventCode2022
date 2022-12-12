import java.util.LinkedList

fun main() {

    fun buildMap(input: List<String>, sMapping: Char, eMapping: Char): List<List<Int>> {
        return input.map { s ->
            s.replace('S', sMapping).replace('E', eMapping).map { it.code }
        }
    }

    fun find(map: List<List<Int>>, start: Char, end: Char, check: (Int, Int) -> Boolean): Int {
        val q = LinkedList<Pair<Int, Int>>()
        val levels = MutableList(map.size) { MutableList(map[0].size) { Int.MAX_VALUE } }
        map.forEachIndexed { i, chars ->
            val j = chars.indexOf(start.code)
            if (j != -1) {
                levels[i][j] = 0
                q.offer(Pair(i, j))
            }
        }
        while (q.isNotEmpty()) {
            val (x, y) = q.poll()
            if (end.code == map[x][y]) {
                return levels[x][y]
            }
            if (x > 0 && check(map[x - 1][y], map[x][y]) && levels[x - 1][y] > levels[x][y] + 1) {
                levels[x - 1][y] = levels[x][y] + 1
                q.offer(Pair(x - 1, y))
            }
            if (x < map.size - 1 && check(map[x + 1][y], map[x][y]) && levels[x + 1][y] > levels[x][y] + 1) {
                levels[x + 1][y] = levels[x][y] + 1
                q.offer(Pair(x + 1, y))
            }
            if (y > 0 && check(map[x][y - 1], map[x][y]) && levels[x][y - 1] > levels[x][y] + 1) {
                levels[x][y - 1] = levels[x][y] + 1
                q.offer(Pair(x, y - 1))
            }
            if (y < map[0].size - 1 && check(map[x][y + 1], map[x][y]) && levels[x][y + 1] > levels[x][y] + 1) {
                levels[x][y + 1] = levels[x][y] + 1
                q.offer(Pair(x, y + 1))
            }
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        val map = buildMap(input, 'a' - 1, 'z' + 1)
        return find(map, 'a' - 1, 'z' + 1) { new, old ->
            new <= old + 1
        }
    }


    fun part2(input: List<String>): Int {
        val map = buildMap(input, 'a', 'z' + 1)
        return find(map, 'z' + 1, 'a') { new, old ->
            old <= new + 1
        }
    }

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
