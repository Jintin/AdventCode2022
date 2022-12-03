fun main() {
    fun part1(input: List<String>): Int {
        var score = 0
        input.forEach {
            score += it[2] - 'X' + 1
            if (it[2] - 'X' == it[0] - 'A') {
                score += 3
            } else if (it[2] - 'X' == (it[0] - 'A' + 1) % 3) {
                score += 6
            }
        }
        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0
        input.forEach {
            score += (it[2] - 'X') * 3
            when (it[2]) {
                'X' -> score += (it[0] - 'A' + 2) % 3 + 1
                'Y' -> score += it[0] - 'A' + 1
                'Z' -> score += (it[0] - 'A' + 1) % 3 + 1
            }
        }
        return score
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
