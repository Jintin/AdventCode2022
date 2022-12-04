fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val a = line.substring(0, line.length / 2).toSet().toMutableSet()
            val b = line.substring(line.length / 2).toSet()
            a.retainAll(b)
            a.sumOf {
                if (it in 'A'..'Z') {
                    it - 'A' + 1 + 26
                } else {
                    it - 'a' + 1
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        var score = 0
        for (index in 0 until input.size / 3) {
            val a = input[index * 3].toSet().toMutableSet()
            val b = input[index * 3 + 1].toSet()
            val c = input[index * 3 + 2].toSet()
            a.retainAll(b)
            a.retainAll(c)
            score += a.sumOf {
                if (it in 'A'..'Z') {
                    it - 'A' + 1 + 26
                } else {
                    it - 'a' + 1
                }
            }
        }
        return score
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
