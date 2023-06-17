// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
//    alias(libs.plugins.android.hilt) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    kotlin("android") version "1.8.20" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.5.0" apply false
}