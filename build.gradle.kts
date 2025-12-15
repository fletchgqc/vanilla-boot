plugins {
	kotlin("jvm") version "2.2.21"
	kotlin("plugin.spring") version "2.2.21"
	id("org.springframework.boot") version "3.5.8"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.gitlab.arturbosch.detekt") version "1.23.8"
	id("com.google.cloud.tools.jib") version "3.5.1"
}

group = "de.codecentric"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.oshai:kotlin-logging-jvm:7.0.13")
	implementation("net.logstash.logback:logstash-logback-encoder:9.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("io.kotest:kotest-assertions-core-jvm:6.0.7")
	testImplementation("io.mockk:mockk:1.14.6")
	testImplementation("com.ninja-squad:springmockk:4.0.2")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

detekt {
	autoCorrect = true
	config.setFrom("$projectDir/config/detekt/detekt.yml")
	buildUponDefaultConfig = true
}

dependencies {
	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.8")
}

// avoids failures like "detekt was compiled with Kotlin 2.0.21 but is currently running with 2.2.10."
configurations.matching { it.name == "detekt" }.all {
	resolutionStrategy.eachDependency {
		if (requested.group == "org.jetbrains.kotlin") {
			@Suppress("UnstableApiUsage")
			useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
		}
	}
}

tasks.register<io.gitlab.arturbosch.detekt.Detekt>("detektWithIgnoreFailures") {
	description = "Run detekt with auto-correct, ignoring failures"
	group = "verification"

	ignoreFailures = true
	autoCorrect = true
	config.setFrom("$projectDir/config/detekt/detekt.yml")
	buildUponDefaultConfig = true
	setSource(files("src/main/kotlin", "src/test/kotlin"))
}

tasks.register("detektTwice") {
	description = "Run detekt twice - first with auto-correct, second to verify"
	group = "verification"
	// This causes the tasks to run in serial. If both tasks are in dependsOn, they run in parallel.
	dependsOn("detektWithIgnoreFailures")
	finalizedBy("detekt")
}
