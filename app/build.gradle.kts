plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.davemac.product_techexam"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.davemac.product_techexam"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true

    }
}

dependencies {

    val glide_version = "4.16.0"
    val retrofit_version = "2.11.0"
    val lifecycle_version = "2.8.7"
    val gson_version = "2.12.1"
    val gsonConverter_version = "2.11.0"
    val coroutines_core_version = "1.10.1"
    val coroutines_adr_version  = "1.3.9"
    val shimmer_version = "0.5.0"
    val room_version = "2.6.1"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Shimmer effect
    implementation("com.facebook.shimmer:shimmer:$shimmer_version")

    //Navigation
    implementation(libs.androidx.navigation.fragment)

    //Glide
    implementation("com.github.bumptech.glide:glide:$glide_version")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_core_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_adr_version")

    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")

    implementation("com.google.code.gson:gson:$gson_version")

    implementation("com.squareup.retrofit2:converter-gson:$gsonConverter_version")

    //Room
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
}