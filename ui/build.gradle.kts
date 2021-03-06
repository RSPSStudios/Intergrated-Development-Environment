plugins {
    application
    id("org.openjfx.javafxplugin") version "0.0.9"
}

javafx {
    version = "16"
    //modules = [ 'javafx.controls' ]
    modules(
        "javafx.base",
        "javafx.controls",
        "javafx.graphics",
        "javafx.fxml",
        "javafx.web",
        "javafx.swing"
    )
}

dependencies {
    implementation(project(":api"))
    implementation("org.pf4j:pf4j:3.6.0")
    implementation("io.ktor:ktor-client-apache:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.4.2")
    implementation("no.tornado:tornadofx:2.0.0-SNAPSHOT")
    implementation("org.controlsfx:controlsfx:11.0.3")
    implementation("de.jensd:fontawesomefx-fontawesome:4.7.0-9.1.2")
    implementation("com.displee:rs-cache-library:6.8")
    implementation("io.insert-koin:koin-core:3.1.2")
    implementation("com.google.code.gson:gson:2.8.6")
}
