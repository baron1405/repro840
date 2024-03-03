# Reproduction of Issue "IllegalAccessError in unnamed module #840"

This is a very simple Gradle build which reproduces
[IllegalAccessError in unnamed module #840](https://github.com/autonomousapps/dependency-analysis-gradle-plugin/issues/840).

The cause is Guava incompatibilities between the dependency-analysis plugin and
transitive dependencies in the buildSrc build. When the dependency-analysis plugin
is applied to the root build, it will try to resolve its dependency on Guava
from any direct or transitive dependencies on Guava from the buildSrc dependencies.
Due to class loader behavior for buildSrc, if the versions of Guava do not match,
an IllegalAccessError will result.

To reproduce:
1. Clone this repo
2. Run `./gradlew buildHealth`

See the buildSrc/build.gradle.kts for more details and a workaround. Please
consider shadowing (or even better eliminating) Guava.
