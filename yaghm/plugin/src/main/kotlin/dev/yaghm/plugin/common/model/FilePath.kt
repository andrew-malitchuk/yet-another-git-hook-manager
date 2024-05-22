package dev.yaghm.plugin.common.model

import dev.yaghm.plugin.common.core.regex.isFileValid


/**
 * Wrapper over file path which widely used to validate input data.
 *
 * @param value file path
 */
@JvmInline
value class FilePath(
    val value: String?,
) {
//    init {
//        require(!value.isNullOrBlank() && value.isFileValid) {
//            "Invalid value for `ArtifactPath`"
//        }
//    }
}