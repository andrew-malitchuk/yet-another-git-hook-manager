package dev.yaghm.plugin.internal.config

@DslMarker
annotation class GitHookDSL

@GitHookDSL
class GitHookConfig {
    var type: String = ""
    var doFirstAction: ((GitHookAction) -> Unit)? = null
    var action: ((GitHookAction) -> Unit)? = null
    var doLastAction: ((GitHookAction) -> Unit)? = null
}

@GitHookDSL
class GitHookAction {
    var command: String? = null
}

@GitHookDSL
fun GitHookConfig.action(configure: (GitHookAction) -> Unit) {
    action = configure
}

//
@GitHookDSL
fun preCommit(configure: GitHookConfig.() -> Unit): GitHookConfig {
    return configure("preCommit", configure)
}

fun prePush(filePath: String): GitHookConfig {
    return GitHookConfig().apply {
        this.type = "preCommit"
        this.action = {
            GitHookAction().apply {
                command = filePath
            }
        }
    }
}
//

@GitHookDSL
fun configure(type: String, configure: GitHookConfig.() -> Unit): GitHookConfig {
    val gitHookConfig = GitHookConfig().apply {
        this.type = type
    }
    gitHookConfig.configure()
    return gitHookConfig
}

fun foo() {
    configure("preCommit") {
        action {

        }
    }
    preCommit {
        action {

        }
    }
}