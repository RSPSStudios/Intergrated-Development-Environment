dependencies {
    kapt("org.pf4j:pf4j:3.6.0")
    plugin(project(":osrs-definitions"))
    plugin(project(":osrs-toolkit"))
}

javafx {
    version = "15.0.1"
    configuration = "compileOnly"
    modules(
        "javafx.base",
        "javafx.controls",
        "javafx.graphics",
        "javafx.fxml",
        "javafx.web",
        "javafx.swing"
    )
}
