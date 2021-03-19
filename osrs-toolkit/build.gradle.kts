plugins {
    java
}
dependencies {
    implementation("com.google.guava:guava:30.1-jre")
    implementation(project(":osrs-definitions"))
    testImplementation("com.displee:rs-cache-library:6.8")
}

tasks.withType<JavaCompile> {
    targetCompatibility = "15"
    sourceCompatibility = "15"
}
