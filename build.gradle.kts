import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask
import org.jetbrains.intellij.platform.gradle.Constants.Constraints
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java")
    alias(libs.plugins.changelog)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.intelliJPlatform)
    alias(libs.plugins.grammarKit)
    alias(libs.plugins.kover)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.testLogger)
    alias(libs.plugins.versionPlugin)
    alias(libs.plugins.versionUpdate)
}

group = providers.gradleProperty("pluginGroup").get()
version =
    providers
        .gradleProperty("pluginVersion")
        .get()
        .let { baseVersion ->
            if (baseVersion.endsWith("-dev")) {
                val gitHash =
                    providers
                        .exec { commandLine("git", "rev-parse", "--short=8", "HEAD") }
                        .standardOutput
                        .asText
                        .get()
                        .trim()
                "$baseVersion+$gitHash"
            } else {
                baseVersion
            }
        }

kotlin {
    jvmToolchain(25)
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Include generated sources
sourceSets {
    main {
        java.srcDir("src/main/gen")
    }
}

dependencies {
    testImplementation(libs.jupiter)
    testImplementation(libs.jupiterParams)
    testImplementation(libs.mockk) {
        // The IntelliJ Platform bundles a patched kotlinx-coroutines fork; a vanilla one on the
        // test classpath shadows it and breaks the test framework (runBlockingWithParallelismCompensation).
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core")
    }
    testImplementation(libs.remoteRobot)
    testImplementation(libs.remoteRobotFixtures)
    testRuntimeOnly(libs.jupiterEngine)
    testRuntimeOnly(libs.junitPlatformLauncher)
    testRuntimeOnly("junit:junit:4.13.2") // legacy JUnit 4 support
    intellijPlatform {
        intellijIdeaUltimate(providers.gradleProperty("platformVersion"))
        plugin("com.redhat.devtools.lsp4ij", libs.versions.lsp4ij.get())
        pluginVerifier()
        zipSigner()
        testFramework(TestFrameworkType.Platform)
        testFramework(TestFrameworkType.JUnit5)
    }
}

// JFlex lexer generation using Grammar-Kit plugin
val generateFishLexer by tasks.registering(GenerateLexerTask::class) {
    sourceFile.set(file("src/main/kotlin/com/github/toxdev/fish/lexer/Fish.flex"))
    targetOutputDir.set(file("src/main/gen/com/github/toxdev/fish/lexer"))
    purgeOldFiles.set(true)
}

// BNF parser generation using Grammar-Kit plugin
val generateFishParser by tasks.registering(GenerateParserTask::class) {
    sourceFile.set(file("src/main/kotlin/com/github/toxdev/fish/parser/Fish.bnf"))
    targetRootOutputDir.set(file("src/main/gen"))
    pathToParser.set("com/github/toxdev/fish/parser/FishParser.java")
    pathToPsiRoot.set("com/github/toxdev/fish/psi")
    purgeOldFiles.set(true)
}

intellijPlatform {
    pluginConfiguration {
        name = providers.gradleProperty("pluginName")
        version = providers.gradleProperty("pluginVersion")
        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
            untilBuild = provider { null }
        }
    }
    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }
    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
    }
    pluginVerification {
        ides {
            val verifyIde = providers.gradleProperty("verifyIde").orNull
            if (verifyIde != null) {
                val ideType =
                    org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
                        .fromCode(verifyIde)
                create(ideType, providers.gradleProperty("platformVersion").get())
            } else {
                recommended()
            }
        }
    }
}

ktlint {
    filter {
        exclude("**/gen/**")
    }
}

changelog {
    groups.empty()
    repositoryUrl = providers.gradleProperty("pluginRepositoryUrl")
}

kover {
    currentProject {
        sources {
            excludeJava = true
        }
        instrumentation {
            disabledForTestTasks.add("uiTest")
        }
    }
    reports {
        total {
            xml {
                onCheck = true
            }
        }
        verify {
            rule {
                minBound(72)
            }
        }
    }
}

versionCatalogUpdate {
    keep {
        keepUnusedVersions = true
    }
}

tasks {
    wrapper {
        gradleVersion = providers.gradleProperty("gradleVersion").get()
    }

    compileKotlin {
        dependsOn(generateFishLexer, generateFishParser)
    }

    compileJava {
        dependsOn(generateFishLexer, generateFishParser)
    }

    named("runKtlintCheckOverMainSourceSet") {
        dependsOn(generateFishLexer, generateFishParser)
    }

    named("runKtlintFormatOverMainSourceSet") {
        dependsOn(generateFishLexer, generateFishParser)
    }

    test {
        useJUnitPlatform()
        exclude("**/UITest.class")
        jvmArgs(
            "-Djb.mapper.configuration.url=file:///dev/null",
        )
    }

    val uiTest =
        register<Test>("uiTest") {
            description = "Runs UI tests (requires runIdeForUiTests to be running)"
            group = "verification"
            useJUnitPlatform()
            include("**/UITest.class")
            testClassesDirs = sourceSets["test"].output.classesDirs
            classpath = sourceSets["test"].runtimeClasspath
            shouldRunAfter(test)
            jvmArgs(
                "--add-opens=java.base/java.lang=ALL-UNNAMED",
                "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
            )
        }

    buildSearchableOptions {
        enabled = false
    }

    prepareJarSearchableOptions {
        enabled = false
    }

    runIde {
        jvmArgs("-XX:+UnlockDiagnosticVMOptions")
    }
}

val runIdeForUiTests by intellijPlatformTesting.runIde.registering {
    type = org.jetbrains.intellij.platform.gradle.IntelliJPlatformType.IntellijIdea
    version = providers.gradleProperty("platformVersion")

    task {
        jvmArgumentProviders +=
            CommandLineArgumentProvider {
                buildList {
                    add("-Drobot-server.port=8082")
                    add("-Djb.privacy.policy.text=<!--999.999-->")
                    add("-Djb.consents.confirmation.enabled=false")
                    add("-Didea.trust.all.projects=true")
                    add("-Dide.show.tips.on.startup.default.value=false")
                    if (org.gradle.internal.os.OperatingSystem
                            .current()
                            .isMacOsX
                    ) {
                        add("-Dide.mac.message.dialogs.as.sheets=false")
                        add("-Dide.mac.file.chooser.native=false")
                        add("-DjbScreenMenuBar.enabled=false")
                        add("-Dapple.laf.useScreenMenuBar=false")
                    }
                }
            }
    }

    plugins {
        robotServerPlugin(Constraints.LATEST_VERSION)
    }
}
