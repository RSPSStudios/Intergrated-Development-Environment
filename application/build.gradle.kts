plugins {
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

application {
    mainClass.set("com.javatar.application.TerminalApplication")
    mainClassName = mainClass.get()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}

dependencies {
    implementation(project(":terminal"))
    implementation("com.xenomachina:kotlin-argparser:2.0.7")
}
