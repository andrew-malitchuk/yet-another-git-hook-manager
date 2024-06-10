@file:Suppress("TooGenericExceptionCaught", "SwallowedException")

package dev.yaghm.plugin.common.core.regex

import java.io.File
import java.nio.file.InvalidPathException
import java.nio.file.Paths

/**
 * Checks if the current string represents a valid file path.
 *
 * This extension property attempts to create a `Path` object from the string using
 * `Paths.get(this)`. If successful (no exceptions thrown), the path is considered valid.
 *
 * @throws InvalidPathException if the string contains invalid path characters or syntax errors.
 * @throws NullPointerException if the string is null.
 *
 * @return `true` if the path is valid, `false` otherwise.
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
 * Checks if the current string represents a valid file that exists and is not empty.
 *
 * This extension property first checks if the string is a valid file path using the `isPathValid` property.
 * If the path is invalid, the property returns `false` immediately. Otherwise, it creates a `File` object
 * from the path and verifies two conditions:
 *  - The file exists on the file system (`tempFile.exists()`).
 *  - The file size is greater than zero (`tempFile.length() > 0`), indicating it's not empty.
 *
 * @throws exceptions inherited from `isPathValid`.
 *
 * @return `true` if the file is valid (exists and not empty), `false` otherwise.
 */
val String.isFileValid: Boolean
    get() {
        if (!this.isPathValid) return false
        val tempFile = File(this)
        return tempFile.exists() && tempFile.length() > 0
    }
