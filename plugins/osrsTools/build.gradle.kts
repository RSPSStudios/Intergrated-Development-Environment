dependencies {
    kapt("org.pf4j:pf4j:3.6.0")
    plugin("com.javatar:osrs-definitions:0.1")
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