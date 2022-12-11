import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        val root = buildFileTree(input)
        return countSize(root)
    }


    fun part2(input: List<String>): Int {
        val root = buildFileTree(input)
        return findMin(root, root.size - 40000000)
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private enum class FileType {
    DIR, File
}

private class Tree(val name: String, val type: FileType, val fileSize: Int = 0) {
    val child = mutableMapOf<String, Tree>()
    val size: Int by lazy {
        when (type) {
            FileType.File -> fileSize
            FileType.DIR -> child.values.sumOf { it.size }
        }
    }

    fun add(tree: Tree) {
        child[tree.name] = tree
    }
}

private fun buildFileTree(input: List<String>): Tree {
    val stack = Stack<Tree>()

    input.forEachIndexed { index, s ->
        if (s.startsWith("$ ls")) {
            var start = index + 1
            while (start < input.size && !input[start].startsWith("$")) {
                val strings = input[start].split(" ")
                if (strings[0].startsWith("dir")) {
                    stack.peek().add(Tree(strings[1], FileType.DIR))
                } else {
                    stack.peek().add(Tree(strings[1], FileType.File, strings[0].toInt()))
                }
                start++
            }
        } else if (s.startsWith("$ cd")) {
            when (val target = s.split(" ")[2]) {
                "/" -> stack.push(Tree("/", FileType.DIR))
                ".." -> stack.pop()
                else -> stack.push(stack.peek().child[target])
            }
        }
    }
    while (stack.size > 1) {
        stack.pop()
    }
    return stack.peek()
}

private fun countSize(current: Tree): Int {
    var total = current.child.values.sumOf { countSize(it) }
    if (current.type == FileType.DIR && current.size <= 100000) {
        total += current.size
    }
    return total
}

private fun findMin(root: Tree, target: Int): Int {
    if (root.type != FileType.DIR) {
        return Int.MAX_VALUE
    }
    val value = root.child.values.minOfOrNull {
        findMin(it, target)
    } ?: Int.MAX_VALUE
    return if (root.size >= target) {
        minOf(root.size, value)
    } else {
        value
    }
}