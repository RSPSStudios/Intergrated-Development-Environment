import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
val pluginsDir by extra { file("$buildDir/plugins") }

plugins {
    kotlin("jvm") version "1.5.10" apply false
    kotlin("kapt") version "1.5.10" apply false
}

subprojects {

    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }

    group = "com.javatar"
    version = "1.0-SNAPSHOT"

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")

    dependencies {
        "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-RC")
        "implementation"("org.koin:koin-core:2.2.1")
        "implementation"("org.slf4j:slf4j-api:2.0.0-alpha1")
        "implementation"("org.slf4j:slf4j-log4j12:2.0.0-alpha1")
        "implementation"("com.google.guava:guava:30.1.1-jre")
        "testImplementation"(kotlin("test-junit5"))
        "testImplementation"("org.junit.jupiter:junit-jupiter-api:5.6.0")
        "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "15"
    }
}

