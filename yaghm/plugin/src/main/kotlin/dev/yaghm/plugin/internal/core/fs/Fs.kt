package dev.yaghm.plugin.internal.core.fs

import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException

class Fs(private val fileName: String) {

    var file: File? = null

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

    fun deleteFile() {
        require(file?.exists() == true)
        file?.delete()
    }

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

    fun makeFileExecutable(): Boolean {
        require(file?.exists() == true)
        return if (file?.exists() == true) {
            file?.setExecutable(true)!!
        } else {
            false
        }
    }


    constructor(fileName: String, block: Fs.() -> Unit) : this(fileName) {
        block.invoke(this)
    }

}