// Top-level build file where you can add configuration options common to all sub-projects/modules.
apply(plugin = "kover")

apply {
    from("$rootDir/buildSrc/src/main/java/owasp.gradle")
    from("$rootDir/buildSrc/src/main/java/sonarqube.gradle")// sonar enlace local
}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
    id("org.sonarqube") version "3.5.0.2730"
    id("org.owasp.dependencycheck") version "8.1.2" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.43.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("org.jetbrains.kotlinx:kover:0.6.1_")
        classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.5.0.2730")
        classpath("org.owasp:dependency-check-gradle:8.1.2")
    }
}
koverMerged {
    enable()

    xmlReport {
        onCheck.set(true)
    }
    htmlReport {
        onCheck.set(true)
    }
    verify {
        onCheck.set(true)
    }
    filters {
        classes {
            excludes += listOf(
                "dagger.hilt.internal.aggregatedroot.codegen.*",
                "hilt_aggregated_deps.*",
                "*ComposablesSingletons*",
                "*_HiltModules*",
                "*Hilt_*",
                "*BuildConfig",
                ".*_Factory.*",
                ".*.*_Factory*",
                "*_Factory\$*",
                "*_*Factory",
                "*_*Factory\$*",
                "**/di/**",
                "**/modelo/**",
                "*entity.*",
                "**/*_presentacion/**",
                "*core_ui.*",
                "*util*"
            )
        }
    }
}

subprojects {
    apply(plugin = "org.sonarqube")
    apply(plugin = "org.jetbrains.kotlinx.kover")
}
allprojects {
    apply(plugin = "org.owasp.dependencycheck")
    apply {from("$rootDir/buildSrc/src/main/java/owasp.gradle") }
}

configure<org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension> {
    format = org.owasp.dependencycheck.reporting.ReportGenerator.Format.ALL.toString()
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}