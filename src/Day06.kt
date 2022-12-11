fun main() {

    fun offset(line: String, count: Int): Int {
        val set = mutableSetOf<Char>()
        for (i in line.indices) {
            set.clear()
            for (j in 0 until count) {
                set.add(line[i + j])
            }
            if (set.size == count) {
                return i + count
            }
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        return offset(input[0], 4)
    }

    fun part2(input: List<String>): Int {
        return offset(input[0], 14)
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
