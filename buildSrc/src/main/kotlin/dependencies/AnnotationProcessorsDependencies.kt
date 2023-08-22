package dependencies

object AnnotationProcessorsDependencies {
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"
    const val DAGGER_ANDROID_PROCESSOR =
        "com.google.dagger:dagger-android-processor:${Versions.DAGGER}"
    const val ROOM_ANNOTATION_PROCESSOR = "androidx.room:room-compiler:${Versions.ROOM}"
}
