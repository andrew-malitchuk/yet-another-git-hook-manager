package dev.yaghm.plugin.internal.core.fs

import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException

class Fs(private val fileName: String) {

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

    fun deleteFile(fileName: String) {
        val file = File(fileName)
        require(file.exists())
        file.delete()
    }

    fun deleteFile() {
        deleteFile(fileName)
    }

    fun appendTextToFile(textToAdd: String) {
        val file = File(fileName)

        require(file.exists())
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

    fun clearFile() {
        val file = File(fileName)

        require(file.exists())

        try {
            val writer = FileWriter(file)
            writer.write("")
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun makeFileExecutable(): Boolean {
        val file = File(fileName)
        require(file.exists())
        return if (file.exists()) {
            file.setExecutable(true)
        } else {
            false
        }
    }


    constructor(fileName: String, block: Fs.() -> Unit) : this(fileName) {
        block.invoke(this)
    }

}