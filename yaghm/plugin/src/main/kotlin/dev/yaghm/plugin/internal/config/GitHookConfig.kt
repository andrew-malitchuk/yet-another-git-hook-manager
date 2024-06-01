package dev.yaghm.plugin.internal.config

import dev.yaghm.plugin.common.model.FilePath
import dev.yaghm.plugin.internal.core.dsl.bash.Shebang
import dev.yaghm.plugin.internal.model.Command

class GitHookConfig {
    var type: GitHookType? = null
    var doFirst: Command? = null
    var action: Command? = null
    var doLast: Command? = null
    var shebang: Shebang? = null
    var filePath: FilePath? = null

    val gitHook: Command
        get() {
            return Command(
                """
${doFirst?.value ?: ""}
${action?.value ?: ""}
${doLast?.value ?: ""}
                """.trimIndent().trim()
            )
        }
}
