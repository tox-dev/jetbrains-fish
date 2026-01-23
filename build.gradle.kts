import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask
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
version = providers.gradleProperty("pluginVersion").get()

kotlin {
    jvmToolchain(21)
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
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.jupiterEngine)
    testRuntimeOnly(libs.junitPlatformLauncher)
    testRuntimeOnly("junit:junit:4.13.2") // legacy JUnit 4 support
    intellijPlatform {
        intellijIdeaCommunity(providers.gradleProperty("platformVersion"))
        pluginVerifier()
        zipSigner()
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
    pluginVerification {
        ides {
            recommended()
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
    }
    reports {
        total {
            xml {
                onCheck = true
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
        jvmArgs(
            "-Djb.mapper.configuration.url=file:///dev/null",
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
