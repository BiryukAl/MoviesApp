@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
//    id("com.google.devtools.ksp")
    alias(libs.plugins.realm)
}

android {
    namespace = "ru.kpfu.itis.core.db"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        buildConfigField("Long", "VERSION_DB", "1L")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        buildConfig = true
    }
}

dependencies {

//    implementation(libs.room)
//    implementation(libs.room.kotlin)
//    ksp(libs.room.compiler)

    api(libs.realm)
//    implementation(libs.realm.sync)
    implementation(libs.realm.coroutines)

    implementation(libs.koin.android)

    implementation(libs.corutines.core)

    implementation(libs.tiimber)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)

}