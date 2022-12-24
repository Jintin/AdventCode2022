import java.util.LinkedList

fun main() {
    var width = 0
    var height = 0

    fun checkBlockPosition(x: Int, y: Int, value: Char, map: List<List<Char>>): Boolean {
        val w = width - 2
        val h = height - 2
        return map[1 + ((x - 1 + w) % w)][1 + ((y - 1 + h) % h)] == value
    }

    fun check(map: List<List<Char>>, x: Int, y: Int, diffX: Int, diffY: Int): Boolean {
        if (x < 0 || x >= map.size || y < 0 || y >= map[0].size) {
            return false
        }

        if (map[x][y] == '#') {
            return false
        }
        if (x == 0 || x == width - 1) {
            return true
        }
        if (checkBlockPosition(x - diffX, y, 'v', map)) {
            return false
        }
        if (checkBlockPosition(x + diffX, y, '^', map)) {
            return false
        }
        if (checkBlockPosition(x, y - diffY, '>', map)) {
            return false
        }
        if (checkBlockPosition(x, y + diffY, '<', map)) {
            return false
        }
        return true
    }

    fun iterate(start: Pair<Int, Int>, goal: Pair<Int, Int>, offset: Int, map: List<List<Char>>): Int {
        val q = LinkedList<Pair<Int, Int>>()
        q.offer(start)
        var step = offset
        while (!q.isEmpty()) {
            var size = q.size
            val set = mutableSetOf<Pair<Int, Int>>()
            while (size-- > 0) {
                val current = q.poll()
                val x = current.first
                val y = current.second
                val diffX = (step + 1) % (width - 2)
                val diffY = (step + 1) % (height - 2)
                if (x == goal.first && y == goal.second) {
                    return step
                }

                if (check(map, x - 1, y, diffX, diffY)) {
                    set.add(Pair(x - 1, y))
                }
                if (check(map, x + 1, y, diffX, diffY)) {
                    set.add(Pair(x + 1, y))
                }
                if (check(map, x, y - 1, diffX, diffY)) {
                    set.add(Pair(x, y - 1))
                }
                if (check(map, x, y + 1, diffX, diffY)) {
                    set.add(Pair(x, y + 1))
                }
                if (check(map, x, y, diffX, diffY)) {
                    set.add(Pair(x, y))
                }
            }
            q.addAll(set)
            step++
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        val map = input.map { line -> line.map { it } }
        return iterate(Pair(0, 1), Pair(width - 1, height - 2), 0, map)
    }

    fun part2(input: List<String>): Int {
        val map = input.map { line -> line.map { it } }
        val start = Pair(0, 1)
        val end = Pair(width - 1, height - 2)
        val a = iterate(start, end, 0, map)
        val b = iterate(end, start, a, map)
        val c = iterate(start, end, b, map)
        println("$a $b $c")
        return c
    }

    val input = readInput("Day24")
    width = input.size
    height = input[0].length

    println(part1(input))
    println(part2(input))
}
