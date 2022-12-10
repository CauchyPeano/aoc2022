import java.util.regex.Pattern

val FILE_PATTERN: Pattern = Pattern.compile("(\\d+) ([\\w\\W]+)")

sealed class Node(open val name: String) {
    abstract fun size(): Int
}

data class Folder(override val name: String, val parentFolder: Folder?) : Node(name) {

    val folders: HashMap<String, Folder> = HashMap()
    private val files: HashMap<String, File> = HashMap()

    private var computedSize: Int? = null

    fun getOrCreateFolder(nodeName: String): Folder =
        folders.getOrPut(nodeName) {
            Folder(nodeName, this)
        }

    fun addFile(file: File) {
        files[file.name] = file
    }

    override fun size(): Int {
        if (computedSize == null) {
            computedSize = (files + folders).values.sumOf { it.size() }
        }
        return computedSize!!
    }
}

data class File(override val name: String, val size: Int) : Node(name) {
    override fun size(): Int =
        size
}

fun main() {
    val content = readContent("input07.txt")
    val rootFolder = Folder("/", null)
    var currentFolder = rootFolder
    val fileList = ArrayList<File>()
    for (line in content.lines().drop(1)) {
        if (line == "$ cd ..") {
            currentFolder = currentFolder.parentFolder!!
        } else if (line.startsWith("$ cd")) {
            val fileName = line.split(" ")[2]
            currentFolder = currentFolder.getOrCreateFolder(fileName)
        } else if (line.startsWith("dir ")) {
            val fileName = line.split(" ")[1]
            currentFolder.getOrCreateFolder(fileName)
        } else {
            val matcher = FILE_PATTERN.matcher(line)
            if (matcher.matches()) {
                val size = matcher.group(1).toInt()
                val fileName = matcher.group(2)
                val newFile = File(fileName, size)
                fileList.add(newFile)
                currentFolder.addFile(newFile)
            }
        }
    }

    val traverseSolution1 = traverseSolution1(rootFolder)
    println("Solution1 : $traverseSolution1")

    println("Total size: ${rootFolder.size()}")
    val freeSpaceNeeded = 30_000_000L
    val totalSize = 70_000_000L
    val sizeNeededToBeDeleted = freeSpaceNeeded - (totalSize - rootFolder.size().toLong())
    println("Space needed $sizeNeededToBeDeleted")
    val solution2 = traverseSolution2(rootFolder, sizeNeededToBeDeleted)
    println("Solution 2 : $solution2")
}

private fun traverseSolution1(folder: Folder): Long {
    var res = folder.folders.values.filter { f -> f.size() <= 100000 }.sumOf { f -> f.size().toLong() }
    res += folder.folders.values.sumOf { traverseSolution1(it) }
    return res
}

private fun traverseSolution2(folder: Folder, targetSize: Long): Long {
    val sizesOfFolders = folder.folders.values
        .filter { f -> f.size() >= targetSize }.map { it.size().toLong() }
    val sizesOfSubfolders = folder.folders.values.map { traverseSolution2(it, targetSize) }
    return (sizesOfFolders + sizesOfSubfolders).minOrNull() ?: Long.MAX_VALUE
}