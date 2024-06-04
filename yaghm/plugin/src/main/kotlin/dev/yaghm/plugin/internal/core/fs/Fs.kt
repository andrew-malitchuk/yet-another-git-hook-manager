package dev.yaghm.plugin.internal.core.fs

import dev.yaghm.plugin.common.core.ext.findGitHookFolder
import org.gradle.api.Project
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException

class Fs(private val fileName: String) {
    var file: File? = null

    constructor(fileName: String, block: Fs.() -> Unit) : this(fileName) {
        block.invoke(this)
    }

    constructor(project: Project, fileName: String, block: Fs.() -> Unit) : this(fileName) {
        val filePath = "${project.findGitHookFolder()?.absolutePath}${File.separator}$fileName"
        file = File(filePath)
        block.invoke(this)
    }

    /**
     * Creates a new file or replaces an existing file with the same name in the specified directory.
     *
     * This function attempts to create a file at the provided `directoryPath` with the name specified by the
     * missing `fileName` parameter (likely defined elsewhere). If the directory doesn't exist, it will be created.
     * If a file with the same name already exists, it will be deleted and replaced with the new file.
     *
     * @param directoryPath The path to the directory where the file will be created or replaced.
     * @throws IllegalStateException If an error occurs during file creation or replacement. The exception message
     * will include details about the file and the cause of the failure.
     * @return The newly created or replaced `File` object.
     */
    // TODO: recode
    // TODO: test
    fun createOrReplaceFile(directoryPath: String): File {
        val directory = File(directoryPath)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, fileName)
        try {
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
            this@Fs.file = file
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    /**
     * Clones a file by creating a copy with the same content at the specified destination.
     *
     * This function attempts to create a copy of the file located at `sourceFilePath`. It first verifies
     * if the source file exists. If the source file doesn't exist, an `IllegalArgumentException` will be thrown.
     * Additionally, it checks if a destination file (`file`) has already been assigned before copying.
     * If `file` is null or doesn't exist, another `IllegalArgumentException` will be thrown.
     * Once both checks pass, the function opens streams for the source and destination files,
     * copies the content from the source to the destination using `copyTo`, and closes the streams
     * using automatic resource management with `use`.
     *
     * @param sourceFilePath The path to the source file to be cloned.
     * @throws IllegalArgumentException if the source file doesn't exist or the destination file (`file`)
     * is not set or doesn't exist.
     */
    fun cloneFile(sourceFilePath: String) {
        val sourceFile = File(sourceFilePath)

        require(sourceFile.exists())
        require(file?.exists() == true)

        FileInputStream(sourceFile).use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }

    /**
     * Deletes the file associated with the `file` property.
     *
     * This function attempts to delete the file referenced by the `file` property of the class. It first checks
     * if the `file` property has been set and the file exists at the specified location. If the file doesn't
     * exist or the `file` property is null, an `IllegalStateException` will be thrown. Otherwise, the function
     * uses the `delete` method on the `file` object to delete the file.
     *
     * @throws IllegalStateException if the file referenced by `file` doesn't exist or the `file` property is null.
     */
    fun deleteFile() {
        require(file?.exists() == true)
        file?.delete()
    }

    /**
     * Appends text to the content of a file using UTF-8 encoding.
     *
     * This function attempts to append the provided `textToAdd` string to the content of the file referenced
     * by the `file` property. It performs the following checks:
     *  - Checks if the `file` property has been set and the file exists. If not, an `IllegalArgumentException`
     *    is thrown.
     *  - Verifies that the `textToAdd` string is not empty. If it is, an `IllegalArgumentException` is thrown.
     *
     * If the checks pass, the function opens a `BufferedWriter` in append mode using UTF-8 encoding
     * and performs the following actions within a `try-catch` block:
     *  - Appends the `textToAdd` string to the content of the file.
     *  - Closes the `BufferedWriter` to release resources.
     *
     * In case of an exception during the process, the function catches a generic `Exception`
     * (not ideal for production) and prints the stack trace using `e.printStackTrace()`. Consider
     * throwing a more specific exception with details about the failure for better error handling.
     *
     * @param textToAdd The string containing the text to be appended to the file.
     * @throws IllegalArgumentException if the file referenced by `file` doesn't exist, or if the `textToAdd`
     * is empty.
     */
    fun appendTextToFile(textToAdd: String) {
        require(file?.exists() == true)
        require(textToAdd.isNotEmpty())

        try {
            BufferedWriter(FileWriter(file, true)).apply {
                append(textToAdd)
                close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Clears the content of a file by overwriting it with an empty string.
     *
     * This function attempts to clear the content of the file referenced by the `file` property. It first checks
     * if the `file` property has been set and the file exists. If not, an `IllegalArgumentException` is thrown.
     *
     * If the check passes, the function opens a `FileWriter` and performs the following actions within a
     * `try-catch` block:
     *  - Writes an empty string to the file, effectively clearing its content.
     *  - Closes the `FileWriter` to release resources.
     *
     * In case of an exception during the process, the function catches a generic `Exception`
     * (not ideal for production) and prints the stack trace using `e.printStackTrace()`. Consider
     * throwing a more specific exception with details about the failure for better error handling.
     *
     * **Note:** This function simulates clearing a file by writing an empty string. It might be more
     * efficient to truncate the file depending on your use case.
     *
     * @throws IllegalArgumentException if the file referenced by `file` doesn't exist.
     */
    fun clearFile() {
        require(file?.exists() == true)
        try {
            FileWriter(file).apply {
                write("")
                close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Attempts to set the executable bit on the file referenced by `file`.
     *
     * This function tries to make the file associated with the `file` property executable. It first checks
     * if the `file` property has been set and the file exists. If not, an `IllegalArgumentException` is thrown.
     *
     * If the check passes, the function attempts to set the executable bit on the file using `setExecutable(true)`.
     * Due to platform-specific behaviors and potential permission issues, there's no guaranteed success. The function
     * returns `true` if the operation is successful, otherwise it returns `false`.
     *
     * **Note:** This function might not work on all platforms or for all file types. It's recommended to consult
     * platform-specific documentation for reliable executable bit manipulation.
     *
     * @throws IllegalArgumentException if the file referenced by `file` doesn't exist.
     * @return `true` if the executable bit is successfully set, `false` otherwise.
     */
    fun makeFileExecutable(): Boolean {
        require(file?.exists() == true)
        return if (file?.exists() == true) {
            file?.setExecutable(true)!!
        } else {
            false
        }
    }

}
