![img_logo_big_filled.png](docs%2Fimg%2Fimg_logo_big_filled.png)

# YAGHM

![Gradle Plugin Portal Version](https://img.shields.io/gradle-plugin-portal/v/io.github.andrew-malitchuk.yaghm)

## Overview

__YAGHM__ - Yet Another Git Hook Manager - is a cutting-edge Gradle plugin designed to streamline
the management of Git hooks within your project. Utilizing the power of Gradle Kotlin DSL (KTS),
YAGHM enables you to define and store your Git hook configurations directly within your build
scripts, offering a seamless and integrated approach to project configuration. Additionally, YAGHM
supports the option to copy and use configurations from existing files, providing flexibility for
teams with established setups.

## Features

- Install Git Hooks from Gradle DSL.
- Install Git Hooks from Configuration Files.
- Review Git Hook Status.
- Flexible Configuration Options.

## Installation

Apply the plugin in your project module and configure plugin via DSL:

<details open><summary>Kotlin</summary>

```kt
plugins {
    id("io.github.andrew-malitchuk.yaghm") version "0.0.1-a.1"
}
```

</details>

<details><summary>Groovy</summary>

```groovy
plugins {
    id 'io.github.andrew-malitchuk.yaghm' version '0.0.1-a.1'
}
```

</details>

## Usage

YAGHM provides an intuitive and streamlined approach to managing Git hooks within your Gradle
projects. Here’s a step-by-step guide to get you started with YAGHM:

### Install Git Hook

`installGitHook` - scans your Gradle configuration for defined hooks and installs them into the
`.git/hooks` directory of your repository.

Examples:

```shell
./gradlew installGitHook
```

### Remove Git Hook

`removeGitHook` - allows you to remove Git hooks.

Examples:

```shell
./gradlew removeGitHook
```

### Review Git Hook

`reviewGitHook` - generates a detailed list of all the Git hooks installed in your repository.

Examples:

```shell
./gradlew reviewGitHook 
```

## Configuration

```shell
yaghm {
    gitHook {
        configure("pre-commit") {
            onFile {
                "foobar.txt"
            }
        }
        preCommit {
            doFirst {
                echo("hello world")
            }
            action {
                "echo \"main action\""
            }
            doLast {
                gradle("detekt")
            }
            useShebang {
                Interpreter.BASH
            }
        }
    }
}
```

## Troubleshooting

Encountering issues while using yaghm? Check out the troubleshooting section for common problems
and solutions. If you still need assistance, feel free to reach out to the yaghm community
for support.

## Contributing

I welcome contributions from the community to help improve yaghm. Whether you want to report a bug,
suggest a new feature, or submit a pull request, follow the contribution guidelines outlined in the
project's repository. Together, we can make yaghm even better.

## TODO

- [ ] auto mode.

## License

MIT License

```
Copyright (c) [2024] [Andrew Malitchuk]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```