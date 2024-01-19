import org.jooq.meta.jaxb.Logging.WARN

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.flyway)
    alias(libs.plugins.jooq)
    alias(libs.plugins.ktlint)
}

group = "jooqsimple"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.jooq.kotlin)
    implementation(libs.flyway)
    implementation(libs.postgres)

    testImplementation(libs.junit.full)
    testImplementation(libs.bundles.testcontainers)

    jooqCodegen(libs.postgres)
}

tasks.compileKotlin {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

java.sourceCompatibility = JavaVersion.VERSION_17

tasks.test {
    useJUnitPlatform()
}

val generateUrl = "jdbc:postgresql://localhost:6432/generate?user=generate&password=secret"

flyway.url = generateUrl

tasks.jooqCodegen {
    dependsOn(tasks.flywayMigrate)
}

jooq {
    configuration {
        logging = WARN
        jdbc {
            driver = "org.postgresql.Driver"
            url = generateUrl
        }
        generator {
            name = "org.jooq.codegen.JavaGenerator"
            database {
                inputSchema = "public"
                excludes = "flyway_schema_history | spring_session | spring_session_attributes| .*trgm.*"
                isIncludeExcludePackageRoutines = true
                schemaVersionProvider = "none"
                target {
                    packageName = "se.biobanksverige.sbr.db"
                    directory = "$projectDir/src/main/java"
                }
            }
        }
    }
}
