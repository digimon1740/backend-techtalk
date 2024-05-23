import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName("bootJar") { enabled = true }
tasks.getByName("jar") { enabled = true }

plugins {
	kotlin("plugin.spring")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("io.asyncer:r2dbc-mysql:1.0.4")

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.8.0")

}

tasks.getByName<BootJar>("bootJar") {
	launchScript()
}

kotlin {
	jvmToolchain(21)
}
