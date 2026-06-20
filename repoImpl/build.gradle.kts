plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.app.newsdigest.repo"
    compileSdk {
        version = release(37)
    }
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":data:local"))
    implementation(project(":data:remote"))
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(project(":utils"))
    testImplementation(libs.junit)
}
