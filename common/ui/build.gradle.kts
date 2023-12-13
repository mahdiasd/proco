@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.proco.ui"
    compileSdk = 34

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
}

dependencies {
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(project(":domain"))
    implementation(project(":common:utils"))

    api(libs.appcompat)
    api(libs.core.ktx)
    api(libs.lifecycle.runtime.ktx)
    api(libs.activity.compose)
    api(platform(libs.compose.bom))
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling.preview)
    api(libs.material3)

    api(libs.lifecycle.runtime.compose)
    api(libs.navigation.compose)
    api(libs.constraintlayout)
    api(libs.kotlinx.collections.immutable)

    api(libs.core.ktx)
    api(libs.appcompat)
    api(libs.material)

    api(libs.coil.compose)

    api(libs.accompanist.swiperefresh)

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