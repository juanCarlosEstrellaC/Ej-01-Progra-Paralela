plugins {
    id("java")
}

group = "com.programacion"
version = "1.0-SNAPSHOT"
val lwjglVersion = "3.3.4"
val lwjglNatives = "natives-windows"

repositories {
    mavenCentral()
}

dependencies {
    implementation (platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    implementation ("org.lwjgl:lwjgl")
    implementation ("org.lwjgl:lwjgl-glfw")
    implementation ("org.lwjgl:lwjgl-opengl")
    runtimeOnly ("org.lwjgl:lwjgl::$lwjglNatives")
    runtimeOnly ("org.lwjgl:lwjgl-glfw::$lwjglNatives")
    runtimeOnly ("org.lwjgl:lwjgl-opengl::$lwjglNatives")
}