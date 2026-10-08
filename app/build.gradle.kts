plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.play.publisher)
}

android {
    namespace = "com.royals.class11physics"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.royals.class11physics"
        minSdk = 24
        targetSdk = 36
        versionCode = 7
        versionName = "1.7"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("Boolean", "SHOW_ADS", "false")
    }

    signingConfigs {
        create("release") {
            val keystorePath = System.getenv("KEYSTORE_FILE") ?: (project.findProperty("KEYSTORE_FILE") as? String ?: "app/release.keystore")
            val keystoreFile = rootProject.file(keystorePath).takeIf { it.exists() } ?: file(keystorePath)
            if (keystoreFile.exists()) {
                storeFile = keystoreFile
                storePassword = System.getenv("KEYSTORE_PASSWORD") ?: (project.findProperty("KEYSTORE_PASSWORD") as? String ?: "")
                keyAlias = System.getenv("KEY_ALIAS") ?: (project.findProperty("KEY_ALIAS") as? String ?: "")
                keyPassword = System.getenv("KEY_PASSWORD") ?: (project.findProperty("KEY_PASSWORD") as? String ?: "")
            }
        }
    }

    buildTypes {
        debug {
            // Google's official test ad unit IDs - safe to use during development, never show real ads.
            manifestPlaceholders["admobAppId"] = "ca-app-pub-3940256099942544~3347511713"
            buildConfigField("String", "BANNER_AD_UNIT_ID", "\"ca-app-pub-3940256099942544/9214589741\"")
            buildConfigField("String", "INTERSTITIAL_AD_UNIT_ID", "\"ca-app-pub-3940256099942544/1033173712\"")
            buildConfigField("String", "REWARDED_AD_UNIT_ID", "\"ca-app-pub-3940256099942544/5224354917\"")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            val releaseSigning = signingConfigs.getByName("release")
            if (releaseSigning.storeFile?.exists() == true) {
                signingConfig = releaseSigning
            }
            manifestPlaceholders["admobAppId"] = "ca-app-pub-1811294933992844~1432331276"
            buildConfigField("String", "BANNER_AD_UNIT_ID", "\"ca-app-pub-1811294933992844/1091665906\"")
            buildConfigField("String", "INTERSTITIAL_AD_UNIT_ID", "\"ca-app-pub-1811294933992844/5960849203\"")
            buildConfigField("String", "REWARDED_AD_UNIT_ID", "\"ca-app-pub-1811294933992844/4687864204\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

play {
    val possibleKeyFiles = listOf(
        rootProject.file("play-service-account.json"),
        rootProject.file("play-store-key.json"),
        file("play-service-account.json"),
        file("play-store-key.json")
    )
    val credFile = possibleKeyFiles.firstOrNull { it.exists() }
        ?: rootProject.file(".").listFiles()?.firstOrNull { 
            it.name.endsWith(".json") && (it.name.contains("play", ignoreCase = true) || it.name.contains("service-account", ignoreCase = true)) 
        }

    if (credFile != null && credFile.exists()) {
        serviceAccountCredentials.set(credFile)
    }
    track.set("internal")
    defaultToAppBundles.set(true)
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.billing)
    implementation(libs.play.services.ads)
    implementation(libs.play.review)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}