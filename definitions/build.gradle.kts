plugins {
    java
}
dependencies {
    implementation("com.google.guava:guava:30.1-jre")
}

tasks.withType<JavaCompile> {
    targetCompatibility = "15"
    sourceCompatibility = "15"
}
