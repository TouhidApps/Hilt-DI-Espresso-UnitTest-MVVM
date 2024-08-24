// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false

    id("com.google.dagger.hilt.android") version "2.52" apply false
}
true // Needed to make the Suppress annotation work for the plugins block

