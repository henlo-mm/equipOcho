plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.appmovil.mediapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.appmovil.mediapp"
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
    packaging {
        resources.excludes.addAll(
                listOf(
                    "META-INF/DEPENDENCIES"
            )
        )
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    val navVersion = "2.3.5"

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation("androidx.navigation:navigation-common:$navVersion")

    //cardView
    implementation("androidx.cardview:cardview:1.0.0")

    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //firestore y firebase:
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")

    //Google Calendar
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.api-client:google-api-client-android:2.6.0")
    implementation("com.google.apis:google-api-services-calendar:v3-rev411-1.25.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.32.1")
    implementation("com.google.http-client:google-http-client-gson:1.39.2")
    implementation("com.google.http-client:google-http-client-android:1.39.2")
    implementation("com.google.apis:google-api-services-gmail:v1-rev110-1.25.0")


    implementation("com.sun.mail:javax.mail:1.6.2")
    implementation("javax.activation:activation:1.1.1")
    //dagger hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")

}