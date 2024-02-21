import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

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
        version = "1.5"
        archivesName = "myLib"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

// Define the publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.github.amirraza"
            artifactId = "myLib"
            version = "1.6"
        }
    }
}

// Define tasks to install locally and publish to Maven
tasks.register("installAndPublish") {
    dependsOn("install")
    dependsOn("publishToMavenLocal")
}

// Optionally, configure any dependencies or other settings

// Define custom task to execute mvn install command
tasks.register("mvnInstall") {
    doLast {
        // Execute mvn install command
        val result = project.exec {
            commandLine = listOf("mvn", "install")
        }
        if (result.exitValue != 0) {
            throw GradleException("mvn install command failed")
        }
    }
}

// Optionally, configure the task further if needed

// Execute the custom task
tasks.named("mvnInstall").configure {
    dependsOn("installAndPublish")
}


/*
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.github.amirraza"
            artifactId = "myLib"
            version = "1.5"
            artifact("$buildDir/outputs/aar/myLib-release.aar")
        }
    }
}

tasks.named("publishMavenJavaPublicationToMavenLocal") {
    mustRunAfter("bundleReleaseAar")
}*/
