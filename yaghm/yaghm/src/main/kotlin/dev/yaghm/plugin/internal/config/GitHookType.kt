package dev.yaghm.plugin.internal.config

/**
 * Represents the different types of Git hooks available.
 *
 * Git hooks are scripts that are executed at specific points in the Git workflow.
 * They can be used to automate tasks, enforce coding standards, or perform other checks.
 */
enum class GitHookType(val type: String?) {
    /**
     * Executes before a commit is created. It can be used to enforce coding standards,
     * run tests, or perform other checks on the changes being committed.
     */
    PRE_COMMIT("pre-commit"),

    /**
     * Executes before the commit message editor is displayed to the user.
     * It can be used to modify the commit message or provide default content.
     */
    PREPARE_COMMIT_MSG("prepare-commit-msg"),

    /**
     * Executes after the user has entered a commit message but before the commit is finalized.
     * It can be used to validate the commit message format or perform other checks.
     */
    COMMIT_MSG("commit-msg"),

    /**
     * Executes after a commit is made.
     * It can be used to trigger actions such as updating documentation or sending notifications.
     */
    POST_COMMIT("post-commit"),

    /**
     * Executes before remote references are updated during a push operation.
     * It can be used to perform checks on the changes being pushed or prevent certain updates.
     */
    PRE_PUSH("prepush"),
    NI(null),
}
