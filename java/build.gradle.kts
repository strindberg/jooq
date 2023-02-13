import nu.studer.gradle.jooq.JooqGenerate
import org.jooq.meta.jaxb.Logging

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jooq)
    alias(libs.plugins.flyway)

    java
}

dependencies {
    implementation(libs.bundles.jooq)
    implementation(libs.flyway)
    implementation("org.jetbrains:annotations:23.0.0")

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

sourceSets.main {
    java.srcDir(generatedPathJooq)
    java.srcDir(generatedPathConf)
}

val generateDbConf = tasks.register("generateDbConf") {
    doLast {
        mkdir("${generatedPathConf}/jooqsimple")
        file("${generatedPathConf}/jooqsimple/DbConf.java").writeText(
            """
               package jooqsimple;

               public class DbConf {
                   public static final String DBURL = "$jooqDbUrl";
                   public static final String DBUSERNAME = "$jooqDbUser";
                   public static final String DBPASSWORD = "$jooqDbPassword";
               }
            """.trimIndent()
        )
    }
}

val compileJava: JavaCompile by tasks
compileJava.dependsOn(generateDbConf)

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
