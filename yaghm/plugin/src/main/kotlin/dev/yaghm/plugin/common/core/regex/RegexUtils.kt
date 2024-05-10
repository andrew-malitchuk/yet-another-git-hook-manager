@file:Suppress("TooGenericExceptionCaught", "SwallowedException")

package dev.yaghm.plugin.common.core.regex

import java.io.File
import java.nio.file.InvalidPathException
import java.nio.file.Paths


/**
 * Is [path] contains valid file system path?
 */
@Suppress("TooGenericExceptionCaught", "SwallowedException", "MemberVisibilityCanBePrivate")
val String.isPathValid: Boolean
    get() {
        try {
            Paths.get(this)
        } catch (ex: InvalidPathException) {
            return false
        } catch (ex: NullPointerException) {
            return false
        }
        return true
    }

/**
 * Is file which follow [path] valid?
 */
val String.isFileValid: Boolean
    get() {
        if (!this.isPathValid) return false
        val tempFile = File(this)
        return tempFile.exists() && tempFile.length() > 0
    }