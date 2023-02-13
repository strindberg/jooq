import nu.studer.gradle.jooq.JooqGenerate
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.Logging

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.flyway)
    alias(libs.plugins.jooq)

    java
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.jooq.kotlin)
    implementation(libs.flyway)

    runtimeOnly(libs.postgres)

    testImplementation(libs.junit.full)

    jooqGenerator(libs.bundles.jooq)
    jooqGenerator(libs.postgres)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val generatedPath = "$projectDir/src/main/generated"
val generatedPathJooq = "$generatedPath/jooq"
val generatedPathConf = "$generatedPath/conf"

val jooqDbUrl: String by project
val jooqDbUser: String by project
val jooqDbPassword: String by project

kotlin.sourceSets.main {
    kotlin.srcDir(generatedPathJooq)
    kotlin.srcDir(generatedPathConf)
}

val generateDbConf = tasks.register("generateDbConf") {
    doLast {
        mkdir("${generatedPathConf}/jooqsimple")
        file("${generatedPathConf}/jooqsimple/DbConf.kt").writeText(
            """
               package jooqsimple
                
               const val DBURL = "$jooqDbUrl"
               const val DBUSERNAME = "$jooqDbUser"
               const val DBPASSWORD = "$jooqDbPassword"
            """.trimIndent()
        )
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
}
compileKotlin.dependsOn(generateDbConf)

java.sourceCompatibility = JavaVersion.VERSION_17

val clean: Delete by tasks
clean.doFirst {
    delete(generatedPath)
}

flyway {
    url = jooqDbUrl
    user = jooqDbUser
    password = jooqDbPassword
}

jooq {
    version.set(libs.versions.jooq.get())
    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = jooqDbUrl
                    user = jooqDbUser
                    password = jooqDbPassword
                }
                generator.apply {
                    name = "org.jooq.codegen.JavaGenerator"
                    database.apply {
                        inputSchema = "public"
                        excludes = "flyway_schema_history | spring_session | spring_session_attributes| .*trgm.*"
                        isIncludeExcludePackageRoutines = true
                        schemaVersionProvider = "none"
                        target.apply {
                            packageName = "jooqsimple.db"
                            directory = generatedPathJooq
                        }
                    }
                }
            }
        }
    }
}

val generateJooq: JooqGenerate by tasks
generateJooq.dependsOn(tasks.getByName("flywayMigrate"))
