import java.lang.RuntimeException

fun main() {
//    val content = readContent("input06_test.txt")
//    content.lines().forEach {
//        println("Test solution: ${solve(it)}")
//    }
    val content2 = readContent("input06.txt")
    println("Solution 1 = ${solve(content2, 4)}")
    println("Solution 2 = ${solve(content2, 14)}")
}

private fun solve(line: String, size: Int): Int {
    line.windowed(size, 1).forEachIndexed { idx, token ->
        if (token.isCharStringUnique(size)) {
            return idx + size
        }
    }
    throw RuntimeException("hohoho")
}

// this was written by ChatGPT3!
fun String.isCharStringUnique(size: Int): Boolean {
    // Check that the string has exactly 4 characters
    if (length != size) {
        return false
    }

    // Convert the string to a set of characters
    val charSet = toCharArray().toSet()

    // Check that the set has exactly 4 elements (i.e. all characters are unique)
    return charSet.size == size
}