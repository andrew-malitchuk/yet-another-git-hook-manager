package dev.yaghm.plugin.internal.core.dsl.bash

import dev.yaghm.plugin.internal.model.Command

class Bash {
    var shebang: Shebang? = null
    var content: String = ""

    val fileContent: String
        get() {
            return """
${shebang?.interpreter}
$content""".trimIndent().trim()
        }
}

fun Bash.shebang(configure: () -> Shebang): Bash {
    val shebang = configure.invoke()
    return this.also {
        it.shebang = shebang
    }
}

fun Bash.shebang(executeBy: Shebang): Bash {
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
        it.content += "${command.value ?: ""}\n"
    }
}

fun Bash.command(command: Command): Bash {
    return this.also {
        it.content += "${command.value ?: ""}\n"
    }
}

fun bash(configure: Bash.() -> Unit): Bash {
    val bash = Bash()
    bash.configure()
    return bash
}



