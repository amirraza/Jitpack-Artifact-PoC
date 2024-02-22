import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import java.util.function.Predicate

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
        version = "1.9"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
    }
    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/java", "src/main/kotlin")
        }
    }
    /*publishing {
        singleVariant("release") {
            withJavadocJar()
        }
    }*/
    /*tasks.withType<Jar> {
        exclude("*sources.jar")
    }*/
}

dependencies {

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

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.example.mylibrary"
            artifactId = project.archivesName.get()
            version = project.version.toString()
//            pom.packaging = "aar"
//            artifact("$buildDir/outputs/aar/myLib-release.aar")
            afterEvaluate {
                from(components["release"])
            }

            val sourcesJar = artifacts.find { it.extension == "jar" && it.classifier == "sources" }
            if (sourcesJar != null) {
                artifacts.remove(sourcesJar)
            }
        }
    }

    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
    }
}
