@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlinx.serialization)

}

android {
    namespace = "com.proco.data"
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

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    api(project(":domain"))
    api(project(":common:network"))

    api(libs.core.ktx)
    api(libs.appcompat)

    /* --- Kotlin Serialization */
    api(libs.kotlinx.serialization.converter)
    api(libs.kotlinx.serialization.json)

    /* --- Hilt ---*/
    api(libs.dagger.hilt.android)
    api(libs.dagger.hilt.navigation)
    ksp(libs.dagger.hilt.compiler)

    /* --- Retrofit & Okhttp */
    api(libs.retrofit)
    api(libs.okhttp)
    api(libs.okhttp.logging)
    api(libs.kotlinx.serialization.converter)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    /* --- EncryptSharedPreferences */
    implementation(libs.androidx.security.crypto)


    /*--------------------- Tests -------------------------------*/
    testApi(libs.mockito.kotlin)
    testApi(libs.kotlinx.coroutines.test)
    androidTestApi(libs.dagger.hilt.android.testing)
    kspAndroidTest(libs.dagger.hilt.android.compiler)
    kspAndroidTest(libs.dagger.hilt.android.testing)
    testApi(libs.junit)
    androidTestApi(libs.ui.test.junit4)
    androidTestApi(libs.androidx.test.ext.junit)
}