/*
 * MIT License
 *
 * Copyright (c) 2020 Shreyas Patil
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.0")

    defaultConfig {
        applicationId = "dev.shreyaspatil.foodium"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments.plusAssign(
                    hashMapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
        }
    }

    buildFeatures.viewBinding = true

    buildTypes {
        getByName("release") {
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

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(Dependencies.kotlin)

    // Coroutines
    implementation(Coroutines.core)
    implementation(Coroutines.android)

    // Android
    implementation(Android.appcompat)
    implementation(Android.activityKtx)
    implementation(Android.coreKtx)
    implementation(Android.constraintLayout)
    implementation(Android.swipeRefreshLayout)

    // Architecture Components
    implementation(Lifecycle.viewModel)
    implementation(Lifecycle.liveData)

    // Room components
    implementation(Room.runtime)
    implementation(Room.ktx)
    kapt(Room.compiler)

    // Material Design
    implementation(Dependencies.materialDesign)
    implementation(Dependencies.materialDialog)

    // Coil-kt
    implementation(Dependencies.coil)

    // Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.moshiRetrofitConverter)

    // Moshi
    implementation(Moshi.moshi)
    implementation(Moshi.codeGen)
    kapt(Moshi.codeGen)

    // Hilt + Dagger
    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltViewModel)
    kapt(Hilt.daggerCompiler)
    kapt(Hilt.hiltCompiler)

    // Testing
    testImplementation(Testing.core)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.room)
    testImplementation(Testing.okHttp)
    testImplementation(Testing.jUnit)

    // Android Testing
    androidTestImplementation(Testing.extJUnit)
    androidTestImplementation(Testing.espresso)
}

ktlint {
    android.set(true)
    outputColorName.set("RED")
}
