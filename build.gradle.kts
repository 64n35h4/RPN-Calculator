import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
val ktlint by configurations.creating

plugins {
    kotlin("jvm") version "1.5.10"
    jacoco
    application
}

group = "me.tzvika"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.insert-koin:koin-core:3.1.2")
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation ("org.junit.jupiter:junit-jupiter-api")
    ktlint("com.pinterest:ktlint:0.42.1") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
    testImplementation("io.insert-koin:koin-test:3.1.2")
    testImplementation(kotlin("test"))
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

tasks.test {
    useJUnit()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

val ktlintCheck by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("src/**/*.kt")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("-F", "src/**/*.kt")
}

application {
    mainClass.set("MainKt")
}