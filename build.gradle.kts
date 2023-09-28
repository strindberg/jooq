import org.jooq.meta.jaxb.Logging

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.jooq)
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
    implementation(libs.postgres.r2dbc)

    testImplementation(libs.junit.full)
    testImplementation(libs.bundles.testcontainers)

    jooqGenerator(libs.bundles.jooq)
}

val generatedPath = "$projectDir/src/main/generated"

kotlin.sourceSets.main {
    kotlin.srcDir(generatedPath)
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

jooq {
    version = libs.versions.jooq
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation = false
            jooqConfiguration.apply {
                logging = Logging.WARN
                generator.apply {
                    name = "org.jooq.codegen.JavaGenerator"
                    database.apply {
                        name = "org.jooq.meta.extensions.ddl.DDLDatabase"
                        properties.add(org.jooq.meta.jaxb.Property().withKey("scripts").withValue("src/main/resources/db/migration/*.sql"))
                        properties.add(org.jooq.meta.jaxb.Property().withKey("sort").withValue("flyway"))
                        properties.add(org.jooq.meta.jaxb.Property().withKey("defaultNameCase").withValue("lower"))
                        schemaVersionProvider = "none"
                        target.apply {
                            packageName = "se.strindberg.jooqsimple.db"
                            directory = generatedPath
                        }
                    }
                }
            }
        }
    }
}
