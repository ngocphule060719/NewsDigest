plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.app.newsdigest.data.local"
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
    implementation(project(":utils"))
    testImplementation(libs.junit)
}
