fun main() {
    val d = readContent("input08.txt").lines().map { it.toCharArray() }

    val notVisible = HashSet<Pair<Int, Int>>()
    val N = d.indices.last
    val M = d[0].indices.last
    for (i in d.indices.drop(1).dropLast(1)) {
        for (j in d[i].indices.drop(1).dropLast(1)) {
            notVisible.add(i to j)
        }
    }

    traverseAllDirections(d, notVisible, M, N)

    val totalSize = (M + 1) * (N + 1)
    println("Total grid : $totalSize")
    println("Visible trees: ${totalSize - notVisible.size}")

    val dd: Array<IntArray> = d.map { it.map { c -> c.toString().toInt() }.toIntArray() }.toTypedArray()
    var maxScenic = 0
    for (i in d.indices.drop(1).dropLast(1)) {
        for (j in d[i].indices.drop(1).dropLast(1)) {
            val scenic = score(i, j, dd)
            if (scenic > maxScenic) {
                maxScenic = scenic
            }
        }
    }

    println("Scenic score: $maxScenic")
}

private fun score(i: Int, j: Int, m: Array<IntArray>): Int {
    val dirs = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
    var score = 1
    for (d in dirs) {
        var dirscore = 0
        var di = i + d.first
        var dj = j + d.second
        while (di in m.indices && dj in m[0].indices) {
            dirscore++
            if (m[di][dj] >= m[i][j]) break
            di += d.first
            dj += d.second
        }
        score *= dirscore

    }
    return score
}

private fun traverseAllDirections(
    d: List<CharArray>,
    notVisible: HashSet<Pair<Int, Int>>,
    M: Int,
    N: Int
) {
    //    fun isVisible(p: Pair<Int, Int>) : Boolean {
//        if (p.first == 0 || p.second == 0 || p.first == N || p.second == M)
//            return true;
//    }
// L TO R
    for (i in d.indices) {
        var cur: Char
        var maxLast = d[i][0]
        for (j in d[i].indices.drop(1)) {
            cur = d[i][j]
            if (cur > maxLast) {
                maxLast = cur
                notVisible.remove(i to j)
            }
        }
    }

//R to L
    for (i in d.indices) {
        var cur: Char
        var maxLast = d[i][d[i].indices.last]
        for (j in d[i].indices.reversed().drop(1)) {
            cur = d[i][j]
            if (cur > maxLast) {
                maxLast = cur
                notVisible.remove(i to j)
            }
        }
    }

// U to D
    for (j in 1..M) {
        var cur: Char
        var maxLast = d[0][j]
        for (i in 1..N) {
            cur = d[i][j]
            if (cur > maxLast) {
                maxLast = cur
                notVisible.remove(i to j)
            }
        }
    }

    // D to U
    for (j in 1..M) {
        var cur: Char
        var maxLast = d[d.indices.last][j]
        for (i in (1..N).reversed()) {
            cur = d[i][j]
            if (cur > maxLast) {
                maxLast = cur
                notVisible.remove(i to j)
            }
        }
    }
}

