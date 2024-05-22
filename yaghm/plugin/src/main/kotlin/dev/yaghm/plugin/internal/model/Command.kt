package dev.yaghm.plugin.internal.model

import dev.yaghm.plugin.common.core.regex.isFileValid


@JvmInline
value class Command(
    val value: String?,
) {
//    init {
//        require(!value.isNullOrBlank() && value.isFileValid) {
//            "Invalid value for `Command`"
//        }
//    }
}