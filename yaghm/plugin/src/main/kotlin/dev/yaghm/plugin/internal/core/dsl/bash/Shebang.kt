package dev.yaghm.plugin.internal.core.dsl.bash

@JvmInline
value class Shebang(
    val interpreter: String
)


enum class Interpreter(val value: String?) {
    SH("#!/bin/sh"),
    BASH("#!/bin/bash"),
    POWERSHELL("#!/bin/pwsh"),
}