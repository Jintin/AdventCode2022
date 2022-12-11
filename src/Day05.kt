import java.util.*

fun main() {
    fun init(input: List<String>): List<Stack<Char>> {
        val list = mutableListOf<Stack<Char>>()
        val emptyIndex = input.indexOf("")
        repeat(input[emptyIndex - 1].split(" ").last().toInt()) {
            list.add(Stack())
        }
        for (index in emptyIndex - 2 downTo 0) {
            for (i in list.indices) {
                if (i * 4 + 1 < input[index].length) {
                    val c = input[index][i * 4 + 1]
                    if (c != ' ') {
                        list[i].push(c)
                    }
                }
            }
        }
        return list
    }

    fun part1(input: List<String>): String {
        val list = init(input)

        for (index in input.indexOf("") + 1 until input.size) {
            val parts = input[index].split(" ")
            val count = parts[1].toInt()
            val from = parts[3].toInt() - 1
            val to = parts[5].toInt() - 1
            repeat(count) {
                list[to].push(list[from].pop())
            }
        }
        return list.map { it.peek() }.joinToString(separator = "")
    }

    fun part2(input: List<String>): String {
        val list = init(input)

        val temp = Stack<Char>()
        for (index in input.indexOf("") + 1 until input.size) {
            val parts = input[index].split(" ")
            val count = parts[1].toInt()
            val from = parts[3].toInt() - 1
            val to = parts[5].toInt() - 1
            repeat(count) {
                temp.push(list[from].pop())
            }
            while (temp.isNotEmpty()) {
                list[to].push(temp.pop())
            }
        }
        return list.map { it.peek() }.joinToString(separator = "")
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
