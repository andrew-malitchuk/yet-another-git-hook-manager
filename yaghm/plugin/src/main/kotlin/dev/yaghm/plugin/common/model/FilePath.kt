package dev.yaghm.plugin.common.model

/**
 * Wrapper over file path which widely used to validate input data.
 *
 * @param value file path
 */
@JvmInline
value class FilePath(
    val value: String?,
)
