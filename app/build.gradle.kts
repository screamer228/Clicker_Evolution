plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.clickerevolution"
    compileSdk = 34

    defaultConfig {
        applicationId = "screamersInc.piggyClicker"
        minSdk = 26
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
            buildConfigField("String", "SHARED_PREFS", "\"sharedPreferences\"")
            buildConfigField("String", "PREFS_CURRENT_GOLD_TITLE_KEY", "\"prefsGoldValueKey\"")
            buildConfigField("String", "PREFS_CURRENT_GOLD_DEFAULT_VALUE", "\"0\"")
            buildConfigField(
                "String",
                "PREFS_CURRENT_DIAMONDS_TITLE_KEY",
                "\"prefsDiamondsValueKey\""
            )
            buildConfigField("String", "PREFS_CURRENT_DIAMONDS_DEFAULT_VALUE", "\"0\"")
            buildConfigField("String", "PREFS_LAST_EXIT_TIME_TITLE_KEY", "\"prefsExitTimeKey\"")
            buildConfigField("Long", "PREFS_LAST_EXIT_TIME_DEFAULT_VALUE", "0L")
        }

        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "SHARED_PREFS", "\"sharedPreferences\"")
            buildConfigField("String", "PREFS_CURRENT_GOLD_TITLE_KEY", "\"prefsGoldValueKey\"")
            buildConfigField("String", "PREFS_CURRENT_GOLD_DEFAULT_VALUE", "\"0\"")
            buildConfigField(
                "String",
                "PREFS_CURRENT_DIAMONDS_TITLE_KEY",
                "\"prefsDiamondsValueKey\""
            )
            buildConfigField("String", "PREFS_CURRENT_DIAMONDS_DEFAULT_VALUE", "\"0\"")
            buildConfigField("String", "PREFS_LAST_EXIT_TIME_TITLE_KEY", "\"prefsExitTimeKey\"")
            buildConfigField("Long", "PREFS_LAST_EXIT_TIME_DEFAULT_VALUE", "0L")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    //workManager
    implementation ("androidx.work:work-runtime-ktx:2.9.1")

    //splashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //coil
    implementation("io.coil-kt:coil:2.6.0")

    //dagger
    val daggerVersion = "2.50"
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    //navigation
    val navVersion = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}