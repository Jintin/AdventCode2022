fun main() {

    fun part1(input: List<String>): Int {
        var target = 20
        var clock = 0
        var value = 1
        val check: () -> Int = {
            if (clock >= target) {
                target * value.also {
                    target += 40
                }
            } else {
                0
            }
        }
        return input.sumOf { line ->
            if (target > 220) {
                return@sumOf 0
            }
            clock++
            if (line.startsWith("addx")) {
                clock++
                check.invoke().also {
                    value += line.split(" ")[1].toInt()
                }
            } else {
                check.invoke()
            }
        }
    }

    fun part2(input: List<String>): Int {
        var clock = 0
        var value = 0
        fun printPixel() {
            if (clock - value in 0..2) {
                print("#")
            } else {
                print(".")
            }
            clock++
            if (clock % 40 == 0) {
                println()
                clock -= 40
            }
        }
        input.forEach {
            printPixel()
            if (it.startsWith("addx")) {
                printPixel()
                value += it.split(" ")[1].toInt()
            }
        }
        return 0
    }

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
