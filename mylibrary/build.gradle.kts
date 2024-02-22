import com.android.build.gradle.internal.scope.ProjectInfo.Companion.getBaseName
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import java.util.function.Predicate
kotlin {
    jvmToolchain(17)
}

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.example.mylibrary"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        version = "2.0"
        archivesName = "myLib"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lint {
        abortOnError = false
    }

    buildFeatures {
        buildConfig = true
    }
    sourceSets {
        getByName("main") {
            assets.srcDirs("src/main/assets")
        }
    }
//    publishing {
//        singleVariant("release")
//    }
    /*tasks.withType<Jar> {
        exclude("*sources.jar")
    }*/
}
/*
tasks.register("publishToJitPack") {
    dependsOn("assembleRelease")
    doLast {
        val mavenInstallerCmd = listOf(
            "mvn",
            "install:install-file",
            "-DgroupId=com.example.mylibrary",
            "-DartifactId=myLib",
            "-Dversion=4.0",
            "-Dfile=$buildDir/outputs/aar/myLib-release.aar",
            "-Dpackaging=aar",
            "-DgeneratePom=true",
//            "-DpomFile=pom.xml",
            "-DlocalRepositoryPath=.",
            "-DcreateChecksum=true"
        )

        project.exec {
            commandLine(mavenInstallerCmd)
        }
    }
}
tasks.named("assemble") {
    finalizedBy("publishToJitPack")
}*/
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
//tasks.whenTaskAdded {
//    if(this.name.endsWith("Jar")) {
//        this.enabled = false
//    }
//}
//tasks.register<Jar>("sourcesJar") {
//    archiveClassifier.set("sources")
//    from(android.sourceSets["main"].java.srcDirs)
//}
//
//artifacts {
//    add("archives", tasks.named("sourcesJar"))
//}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.example.mylibrary"
            artifactId = "myLib"
            version = "4.0"
            artifact("$buildDir/outputs/aar/myLib-release.aar")
        }
        repositories {
            maven {
                url = uri("https://jitpack.io")
                credentials {
                    username = "jp_4qluuao9sukqiolmqarjdmni4d"
                }
            }
        }
    }
}

tasks.named("publishMavenJavaPublicationToMavenLocal") {
    mustRunAfter("assembleRelease")
}

/*publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            groupId = "com.example.mylibrary"
            artifactId = project.archivesName.get()
            version = project.version.toString()
            pom.packaging = "aar"
//            artifact("$buildDir/outputs/aar/myLib-release.aar")
            afterEvaluate {
                from(components["release"])
            }
        }
    }

    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
    }
}*/
