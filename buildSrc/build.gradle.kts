repositories {
    mavenCentral()
}

dependencies {
    // This dependency has a transitive dependency on com.google.guava:guava:28.2-android.
    // Because this a buildSrc dependency, it and its transitives are placed on the build
    // classpath. It appears that a new class loader is used to load this dependency and its
    // transitives. The problem occurs when the top level Gradle file loads a plugin with
    // transitive dependencies on a difference version of Guava (e.g. the dependency-analysis
    // plugin). That plugin is loaded by yet another class loader but is trying to resolve its
    // Guava dependency from the buildSrc dependency's class loader. That leads to the
    // IllegalAccessError. The best solution to this is for the dependency-analysis plugin
    // to shadow its use of Guava (or getter yet eliminate the use of Guava due to the
    // endless compatibility issues it introduces). A workaround is to force the version of
    // Guava being used (see below).

    implementation("com.github.java-json-tools:json-schema-validator:2.2.14")

    // Here is a direct dependency on an incompatible version of Guava, which will reproduce
    // the problem. Simply comment out the above dependency and uncomment this one for
    // a direct reproduction of the problem.

    // implementation("com.google.guava:guava:28.2-android")
}

// Uncomment the following to demonstrate the workaround for the problem. This is very fragile
// because it requires clients to know the version of Guava used by Gradle and the
// dependency-analysis plugin. Not sure what happens if those two versions are different.

// configurations.all {
//    resolutionStrategy {
//        force("com.google.guava:guava:31.1-jre")
//    }
//}
