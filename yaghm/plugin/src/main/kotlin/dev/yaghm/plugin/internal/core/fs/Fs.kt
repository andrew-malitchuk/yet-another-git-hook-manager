package dev.yaghm.plugin.internal.core.fs

import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException

class Fs {

    var gitHookFile: File? = null

    // TODO: recode
    // TODO: test
    fun createOrReplaceFile(directoryPath: String, fileName: String): File {
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
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    fun cloneFile(sourceFilePath: String, destinationFilePath: String) {
        val sourceFile = File(sourceFilePath)
        val destinationFile = File(destinationFilePath)

        require(sourceFile.exists())
        require(destinationFile.exists())

        FileInputStream(sourceFile).use { inputStream ->
            FileOutputStream(destinationFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }

    fun appendTextToFile(filePath: String, textToAdd: String) {
        val file = File(filePath)

        require(file.exists())
        require(textToAdd.isNotEmpty())

        try {
            val writer = BufferedWriter(FileWriter(file, true))
            writer.append(textToAdd)
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clearFile(filePath: String) {
        val file = File(filePath)

        require(file.exists())

        try {
            val writer = FileWriter(file)
            writer.write("")
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}