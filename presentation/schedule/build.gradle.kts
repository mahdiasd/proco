@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.proco.schedule"
    compileSdk = 33

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    api(project(":domain"))
    api(project(":common:ui"))

    /* --- Hilt ---*/
    implementation(libs.dagger.hilt.android)
    implementation(libs.dagger.hilt.navigation)
    ksp(libs.dagger.hilt.compiler)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    /*--------------------- Tests -------------------------------*/
    testApi(libs.mockito.kotlin)
    testApi(libs.kotlinx.coroutines.test)
    androidTestApi(libs.dagger.hilt.android.testing)
    kspAndroidTest(libs.dagger.hilt.android.compiler)
    kspAndroidTest(libs.dagger.hilt.android.testing)
    testApi(libs.junit)
    androidTestApi(libs.ui.test.junit4)
    androidTestApi(libs.androidx.test.ext.junit)
    androidTestApi(libs.espresso.core)
    androidTestApi(platform(libs.compose.bom))
    debugApi(libs.ui.tooling)
    debugApi(libs.ui.test.manifest)
}