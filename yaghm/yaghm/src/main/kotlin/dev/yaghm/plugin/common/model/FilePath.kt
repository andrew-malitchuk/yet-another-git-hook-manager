package dev.yaghm.plugin.common.model

/**
 * Represents a file path as a value class.
 *
 * This value class wraps a nullable `String` representing a file path. It provides type safety
 * and potential performance optimizations (due to inlining) when targeting the JVM backend
 * with the `@JvmInline` annotation.
 *
 * @property value The nullable string representing the file path.
 */
@JvmInline
value class FilePath(
    val value: String?,
)
