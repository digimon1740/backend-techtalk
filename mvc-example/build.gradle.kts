import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName("bootJar") { enabled = true }
tasks.getByName("jar") { enabled = true }

plugins {
	kotlin("plugin.spring")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.getByName<BootJar>("bootJar") {
	launchScript()
}

kotlin {
	jvmToolchain(21)
}
