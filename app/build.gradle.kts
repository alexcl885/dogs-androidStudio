plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.listaperros"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.listaperros"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    viewBinding{
        enable=true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.androidx.media3.common.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation ("com.github.bumptech.glide:glide:4.15.1")

    //ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

    //LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    // Facilita mvvm en el Fragment
    implementation ("androidx.fragment:fragment-ktx:1.3.2")

    // Facilita mvvm en el Activity
    implementation ("androidx.activity:activity-ktx:1.2.2")
}