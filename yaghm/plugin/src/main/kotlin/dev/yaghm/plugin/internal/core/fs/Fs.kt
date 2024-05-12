package dev.yaghm.plugin.internal.core.fs

import java.io.File
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

}