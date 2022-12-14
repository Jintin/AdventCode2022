fun main() {

    class Container {
        var items: MutableList<Container>? = null
        var value: Int = -1
        var parent: Container? = null

        fun addContainer(container: Container) {
            if (items == null) {
                items = mutableListOf()
            }
            items?.add(container)
        }
    }

    fun parse(data: String): Container {
        val root = Container()
        var current = root
        var value = -1
        data.forEach {
            when (it) {
                '[' -> {
                    val next = Container()
                    current.addContainer(next)
                    next.parent = current
                    current = next
                }

                ']' -> {
                    if (value != -1) {
                        current.addContainer(Container().also { it.value = value })
                    }
                    value = -1
                    current = current.parent!!
                }

                ',' -> {
                    if (value != -1) {
                        current.addContainer(Container().also { it.value = value })
                    }
                    value = -1
                }

                else -> {
                    if (value == -1) {
                        value = 0
                    }
                    value = value * 10 + (it - '0')
                }
            }
        }
        return root
    }

    fun wrap(container: Container): Container {
        return Container().also {
            it.items = mutableListOf(Container().also { it.value = container.value })
        }
    }

    fun compare(left: Container?, right: Container?): Int {
        if (left == null && right == null) {
            return 0
        }
        if (left == null) {
            return -1
        }
        if (right == null) {
            return 1
        }

        return if (left.items == null && right.items == null) {
            left.value - right.value
        } else if (left.items == null) {
            compare(wrap(left), right)
        } else if (right.items == null) {
            compare(left, wrap(right))
        } else {
            var index = 0
            while (index < left.items!!.size && index < right.items!!.size) {
                val result = compare(left.items!![index], right.items!![index])
                if (result != 0) {
                    return result
                }
                index++
            }
            left.items!!.size - right.items!!.size
        }
    }

    fun part1(input: List<String>): Int {
        var count = 0
        input.windowed(2, step = 3).forEachIndexed { index, strings ->
            val left = parse(strings[0])
            val right = parse(strings[1])
            val result = compare(left, right)
            if (result < 0) {
                count += index + 1
            }
        }
        return count
    }


    fun part2(input: List<String>): Int {
        val two = parse("[[2]]")
        val six = parse("[[6]]")
        val list =
            input.asSequence().filter { it.isNotEmpty() }.map { parse(it) }.plus(two).plus(six).sortedWith { a1, a2 ->
                compare(a1, a2)
            }.toList()

        return (list.indexOfFirst { compare(it, two) == 0 } + 1) * (list.indexOfFirst { compare(it, six) == 0 } + 1)
    }

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
