import java.io.File
import java.lang.IllegalArgumentException
import java.net.URI

class InputHelper(private val resourceFileName: String) {

    fun readText(): String {
        return getFile().readText()
    }

    fun <R> readGroups(transform: (String) -> R): List<R> {
        return getFile().readText()
            .split(Regex("^\\s*$", RegexOption.MULTILINE))
            .map(transform)
    }

    fun readStringGroups(): List<String> {
        return readGroups { it }
    }

    fun readIntGroups(): List<List<Int>> {
        return readGroups { group -> group.trim().split(System.lineSeparator()).map { it.toInt() } }
    }

    fun <R> readLines(transform: (String) -> R): List<R> {
        return getFile().useLines {
            it.map(transform).toList()
        }
    }

    fun readLines(): List<String> {
        return getFile().readLines()
    }

    fun readIntLines(): List<Int> {
        return readLines {
            it.toInt()
        }
    }

    private fun getFile(): File {
        return File(getUri())
    }

    private fun getUri(): URI {
        val resource = javaClass.classLoader.getResource(resourceFileName)

        if (resource == null) {
            throw IllegalArgumentException("No resource file found for $resourceFileName.")
        }

        return resource.toURI()
    }


}