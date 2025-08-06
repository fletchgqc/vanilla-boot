plugins {
	kotlin("jvm") version "2.0.10"
	kotlin("plugin.spring") version "2.0.10"
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.gitlab.arturbosch.detekt") version "1.23.7"
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
	implementation("io.github.oshai:kotlin-logging-jvm:7.0.7")
	implementation("net.logstash.logback:logstash-logback-encoder:8.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("io.kotest:kotest-assertions-core-jvm:5.9.1")
	testImplementation("io.mockk:mockk:1.13.8")
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
	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.7")
}

tasks.check {
	dependsOn(tasks.detekt)
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
	val gcpProject = System.getenv("GCP_PROJECT") ?: "vanilla"
	val imageTag = System.getenv("CI_COMMIT_SHA") ?: project.version.toString()
	imageName.set("europe-west1-docker.pkg.dev/$gcpProject/vanilla/vanilla:$imageTag")
	environment.set(mapOf(
		"BP_JVM_VERSION" to "21",
		"BPL_JVM_HEAD_ROOM" to "10",
		"JAVA_TOOL_OPTIONS" to "-Xmx400m -XX:MaxMetaspaceSize=128m -XX:ReservedCodeCacheSize=64m"
	))
}
