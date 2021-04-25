plugins {
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("org.openjfx.javafxplugin") version "0.0.9"
    id("org.beryx.runtime") version "1.12.2"
}

application {
    mainClass.set("com.javatar.application.ApplicationGUI")
    applicationDefaultJvmArgs = listOf(
        "--add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED",
        "--add-opens=javafx.graphics/com.sun.javafx.scene.traversal=ALL-UNNAMED",
        "--add-opens=javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED",
        "--add-opens=javafx.controls/javafx.scene.control=ALL-UNNAMED",
        "--add-opens=javafx.controls/javafx.scene.control.skin=ALL-UNNAMED"
    )
    mainClassName = mainClass.get()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}
javafx {
    version = "15.0.1"
    //modules = [ 'javafx.controls' ]
    modules("javafx.base", "javafx.controls", "javafx.graphics", "javafx.fxml", "javafx.web")
    configuration = "compileOnly"
}

val javaFXOptions = the<org.openjfx.gradle.JavaFXOptions>()

dependencies {
    implementation(project(":ui"))
    implementation("com.javatar:api:0.1")
    implementation("org.pf4j:pf4j:3.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.4.2")
    implementation("no.tornado:tornadofx:2.0.0-SNAPSHOT") {
        exclude("org.jetbrains.kotlin")
    }
    implementation("org.controlsfx:controlsfx:11.0.3")
    implementation("de.jensd:fontawesomefx-fontawesome:4.7.0-9.1.2")
    implementation("com.displee:rs-cache-library:6.8")
    implementation("com.xenomachina:kotlin-argparser:2.0.7")
    implementation("org.koin:koin-core:2.2.1")
    implementation("io.ktor:ktor-client-apache:1.5.2")
    implementation("io.ktor:ktor-client-gson:1.5.2")
    implementation("io.ktor:ktor-client-auth:1.5.2")
    org.openjfx.gradle.JavaFXPlatform.values().forEach { platform ->
        val cfg = configurations.create("javafx_" + platform.classifier)
        org.openjfx.gradle.JavaFXModule.getJavaFXModules(javaFXOptions.modules).forEach { m ->
            project.dependencies.add(
                cfg.name,
                String.format("org.openjfx:%s:%s:%s", m.artifactName, javaFXOptions.version, platform.classifier)
            );
        }
    }
}

runtime {
    imageZip.set(project.file("${project.buildDir}/image-zip/asset-manager-image.zip"))
    options.set(listOf("--compress", "2", "--no-header-files", "--no-man-pages"))
    modules.set(listOf("java.sql", "java.desktop", "jdk.unsupported", "java.scripting", "java.logging", "java.xml", "java.naming"))

    targetPlatform("linux", "/usr/lib/jvm/java-15-openjdk")
    targetPlatform("win", "/home/javatar/platforms/windows/jdk-15.0.2")
    targetPlatform("mac", "/home/javatar/platforms/mac/jdk-15.0.2.jdk/Contents/Home")
}

tasks.withType(CreateStartScripts::class).forEach { script ->
    script.doFirst {
        script.classpath = files("lib/*")
    }
}

tasks["runtime"].doLast {
    org.openjfx.gradle.JavaFXPlatform.values().forEach { platform ->
        val cfg = configurations["javafx_" + platform.classifier]
        cfg.resolvedConfiguration.files.forEach { f ->
            copy {
                from(f)
                into("build/image/application-${platform.classifier}/lib")
            }
        }
    }
}
