plugins {
    application
    id("org.openjfx.javafxplugin") version "0.0.9"
}

javafx {
    version = "15.0.1"
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

val user = project.properties["nexusUsername"] as String
val pass = project.properties["nexusPassword"] as String

repositories {
    maven("http://legionkt.com:8085/repository/rsps-studios-private/") {
        credentials {
            username = user
            password = pass
        }
    }
}

dependencies {
    implementation("com.javatar:api:0.1-20210527.230828-1")
    implementation("org.pf4j:pf4j:3.6.0")
    implementation("io.ktor:ktor-client-apache:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.4.2")
    implementation("no.tornado:tornadofx:2.0.0-SNAPSHOT")
    implementation("org.controlsfx:controlsfx:11.0.3")
    implementation("de.jensd:fontawesomefx-fontawesome:4.7.0-9.1.2")
    implementation("com.displee:rs-cache-library:6.8")
    implementation("org.koin:koin-core:2.2.1")
}
