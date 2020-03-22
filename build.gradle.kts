import Build_gradle.Version.ASSERTJ_VERSION
import Build_gradle.Version.GSON_VERSION
import Build_gradle.Version.KOIN_VERSION
import Build_gradle.Version.KOTLIN_COROUTINES_VERSION
import Build_gradle.Version.LOGBACK_VERSION
import Build_gradle.Version.MOCKK_VERSION
import Build_gradle.Version.TESTNG_VERSION
import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.JavaVersion.VERSION_11

object Version {
    const val KOTLIN_VERSION = "1.3.70"
    const val KOTLIN_COROUTINES_VERSION = "1.3.5"
    const val KOIN_VERSION = "2.1.4"
    const val LOGBACK_VERSION = "1.2.3"
    const val TESTNG_VERSION = "7.1.0"
    const val ASSERTJ_VERSION = "3.12.2"
    const val MOCKK_VERSION = "1.9.3"
    const val GSON_VERSION = "2.8.6"
}

val jacocoMinCoverage = "0.9"
val jacocoExcludedClasses = listOf("**/*/Application*", "**/*/io/InputReader*")

plugins {
    kotlin("jvm") version "1.3.70"
    id("io.gitlab.arturbosch.detekt") version "1.6.0"
    application
    jacoco
}

group = "com.mastrodaro"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "com.mastrodaro.earthquakes.ApplicationKt"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", KOTLIN_COROUTINES_VERSION)
    implementation("org.koin", "koin-core", KOIN_VERSION)
    implementation("ch.qos.logback", "logback-classic", LOGBACK_VERSION)
    implementation("com.google.code.gson", "gson", GSON_VERSION)
    testImplementation("org.testng", "testng", TESTNG_VERSION)
    testImplementation("org.assertj", "assertj-core", ASSERTJ_VERSION)
    testImplementation("io.mockk", "mockk", MOCKK_VERSION)
    testImplementation("org.koin", "koin-test", KOIN_VERSION)
}

configure<JavaPluginConvention> {
    sourceCompatibility = VERSION_11
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    withType<Detekt> {
        this.jvmTarget = "11"
    }
    test {
        useTestNG {
            parallel = "methods"
        }
        dependsOn(":detekt")
        jacoco
        finalizedBy(jacocoTestCoverageVerification)
        finalizedBy(jacocoTestReport)
    }
    jacocoTestReport {
        reports {
            xml.isEnabled = true
            xml.destination = file("$buildDir/reports/coverage/coverage.xml")
            csv.isEnabled = false
            html.isEnabled = true
            html.destination = file("$buildDir/reports/coverage/html")
        }
        classDirectories.setFrom(
            sourceSets.main.get().output.asFileTree.matching {
                exclude(jacocoExcludedClasses)
            }
        )
    }
    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    minimum = jacocoMinCoverage.toBigDecimal()
                }
            }
        }
        classDirectories.setFrom(
            sourceSets.main.get().output.asFileTree.matching {
                exclude(jacocoExcludedClasses)
            }
        )
    }
}

sourceSets {
    main {
        resources {
            exclude("detekt-config.yml")
        }
    }
}

tasks.register<Jar>("fatJar") {
    manifest {
        attributes(
            "Main-Class" to application.mainClassName
        )
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

detekt {
    input = files("src/main/kotlin", "src/test/kotlin")
    config = files("src/main/resources/detekt-config.yml")
}
