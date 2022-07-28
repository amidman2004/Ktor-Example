val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version:String by project
val koin_version:String by project

plugins {
    application
    kotlin("jvm") version "1.7.0"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {

    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cio-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    //MySQL
    implementation("org.mariadb.jdbc:mariadb-java-client:3.0.5")
    implementation("mysql:mysql-connector-java:8.0.29")

    //Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    //StatusPages
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koin_version")

    //Ktor auth JWT
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")

    //Password Encryption
    implementation("at.favre.lib:bcrypt:0.9.0")

    //Database Migration
    implementation("org.flywaydb:flyway-core:7.7.1")
    implementation("com.zaxxer:HikariCP:5.0.1")


}