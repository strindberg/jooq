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
    implementation(libs.postgres.r2dbc)

    testImplementation(libs.junit.full)
    testImplementation(libs.bundles.testcontainers)

    jooqCodegen(libs.bundles.jooq)
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
    configuration {
        logging = WARN
        generator {
            name = "org.jooq.codegen.JavaGenerator"
            database {
                name = "org.jooq.meta.extensions.ddl.DDLDatabase"
                properties.add(org.jooq.meta.jaxb.Property().withKey("scripts").withValue("src/main/resources/db/migration/*.sql"))
                properties.add(org.jooq.meta.jaxb.Property().withKey("sort").withValue("flyway"))
                properties.add(org.jooq.meta.jaxb.Property().withKey("defaultNameCase").withValue("lower"))
                schemaVersionProvider = "none"
                target {
                    packageName = "se.strindberg.jooqsimple.db"
                    directory = "$projectDir/src/main/java"
                }
            }
        }
    }
}
