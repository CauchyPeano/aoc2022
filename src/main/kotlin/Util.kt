
fun readContent(path: String): String =
    object {}.javaClass.getResource(path)!!.readText()