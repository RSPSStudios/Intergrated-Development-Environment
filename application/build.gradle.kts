plugins {
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("org.openjfx.javafxplugin") version "0.0.9"
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
}

dependencies {
    implementation(project(":ui"))
    implementation("org.pf4j:pf4j:3.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.4.2")
    implementation("no.tornado:tornadofx:2.0.0-SNAPSHOT")
    implementation("org.controlsfx:controlsfx:11.0.3")
    implementation("de.jensd:fontawesomefx-fontawesome:4.7.0-9.1.2")
    implementation("com.displee:rs-cache-library:6.8")
    implementation("com.xenomachina:kotlin-argparser:2.0.7")
}
