package dev.yaghm.plugin.internal.core.dsl.bash

import dev.yaghm.plugin.internal.model.Command

class Bash {
    var shebang: String? = null
    var content: String = ""

    fun formatFile(): String {
        return """
            $shebang\n
            $content
        """.trimIndent()
    }
}

fun Bash.shebang(configure: () -> String): Bash {
    val shebang = configure.invoke()
    return this.also {
        it.shebang = shebang
    }
}

fun Bash.shebang(executeBy: String): Bash {
    return this.also {
        it.shebang = executeBy
    }
}

fun Bash.log(configure: () -> String): Bash {
    val command = configure.invoke()
    return this.also {
        it.content += "$command\n"
    }
}

fun Bash.command(configure: () -> Command): Bash {
    val command = configure.invoke()
    return this.also {
        it.content += "${command.value}\n"
    }
}


