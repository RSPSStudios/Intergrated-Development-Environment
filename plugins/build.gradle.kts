val pluginsDir: File by rootProject.extra

plugins {
    kotlin("kapt") apply false
    kotlin("jvm") apply false
}

// here we define the tasks which will build the plugins in the subprojects
subprojects {

    dependencies {
        compileOnly(project(":api"))
        compileOnly(kotlin("stdlib"))

        compileOnly("org.pf4j:pf4j:3.6.0")
        kapt("org.pf4j:pf4j:3.6.0")
    }

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "kapt")

    // if the variable definitions are put here they are resolved for each subproject
    val pluginId: String by project
    val pluginClass: String by project
    val pluginProvider: String by project

    val project = this
    // we have to apply the gradle jvm plugin, because it provides the jar and build tasks
    apply(plugin = "org.jetbrains.kotlin.jvm")

    // the plugin task will put the files into a zip file
    tasks.register<Jar>("plugin") {
        archiveBaseName.set("plugin-${pluginId}")

        // first taking the classes generated by the jar task
        into("classes") {
            with(tasks.named<Jar>("jar").get())
        }
        // and then we also need to include any libraries that are needed by the plugin
        dependsOn(configurations.runtimeClasspath)
        into("lib") {
            from({
                configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }
            })
        }
    }

    // the assemblePlugin will copy the zip file into the common plugins directory
    tasks.register<Copy>("assemblePlugin") {
        from(project.tasks.named("plugin"))
        into(pluginsDir)
    }

    // for the jar task we have to set the plugin properties, so they can be written to the manifest
    tasks.named<Jar>("jar") {
        manifest {
            attributes["Plugin-Class"] = pluginClass
            attributes["Plugin-Id"] = pluginId
            attributes["Plugin-Version"] = archiveVersion
            attributes["Plugin-Provider"] = pluginProvider
        }
    }

    tasks.named("build") {
        dependsOn(tasks.named("plugin"))
    }
}


tasks.register<Copy>("assemblePlugins") {
    dependsOn(subprojects.map { it.tasks.named("assemblePlugin") })
}

tasks {
    "build" {
        dependsOn(named("assemblePlugins"))
    }
}
