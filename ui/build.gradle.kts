import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}
val sdkVersionMajor = properties["sdkVersionMajor"].toString()
val sdkVersionMinor = properties["sdkVersionMinor"].toString()
val sdkVersionPatch = properties["sdkVersionPatch"].toString()
val sdkVersionCode = (sdkVersionMajor.toInt() * 10000) + (sdkVersionMinor.toInt() * 100) + sdkVersionPatch.toInt()
val sdkVersion = "${sdkVersionMajor}.${sdkVersionMinor}.${sdkVersionPatch}"
val uatServerProtocol = properties["uat_server_protocol"].toString()
val uatServerDomain = properties["uat_server_domain"].toString()
val realServerProtocol = properties["real_server_protocol"].toString()
val realServerDomain = properties["real_server_domain"].toString()

android {
    namespace = "kr.co.geniemarket.ui"
    compileSdk = 34

    flavorDimensions.add("default")

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    productFlavors {
        create("real") {
            dimension = "default"
            minSdk = 24
            targetSdk = 34
            version = sdkVersion
            multiDexEnabled = true
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
            buildConfigField("String", "GENIEMARKET_VERSION", "\"$sdkVersion\"")
            buildConfigField("String", "GENIEMARKET_SERVER_PROTOCOL", "\"$realServerProtocol\"")
            buildConfigField("String", "GENIEMARKET_SERVER_DOMAIN", "\"$realServerDomain\"")
        }

        create("uat") {
            dimension = "default"
            minSdk = 24
            targetSdk = 34
            version = sdkVersion
            multiDexEnabled = false
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
            buildConfigField("String", "GENIEMARKET_VERSION", "\"$sdkVersion\"")
            buildConfigField("String", "GENIEMARKET_SERVER_PROTOCOL", "\"$uatServerProtocol\"")
            buildConfigField("String", "GENIEMARKET_SERVER_DOMAIN", "\"$uatServerDomain\"")
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(properties["keystore"].toString())
            storePassword = properties["keystore_pass"].toString()
            keyAlias = properties["keystore_alias"].toString()
            keyPassword = properties["key_pass"].toString()
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules-debug.pro"
            )
        }
    }

    lint {
        checkReleaseBuilds = false
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
    implementation(project(":core"))

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")


    // 롬복 디펜던시
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")


    // Retrofit2 디펜던시.
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Retrofit2-JSON Converter 디펜던시.
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Retrofit-RxJava 디펜던시.
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    // Http Logging 디펜던시.
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // rx Java 디펜던시.
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("io.reactivex.rxjava3:rxjava:3.0.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}