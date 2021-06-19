# gradle-spring-profile
Like maven's profile replacement plugin

## Quick Start

1.You have files such as application.yml or application.yaml, application.properties, etc. in your project, where it says @spring.active@

```properties
spring.profiles.active=@spring.active@
```

```yaml
spring:
  profiles:
    active: @spring.active@
```

2. Introduce the plugin in the build.gradle.kts file

```kotlin
plugins {
    id("com.github.aniaan.gradle-spring-profile") version "1.0.1-RELEASE"
}
```

3. start build

```shell
./gradlew clean bootJar -Pprofile=dev
```

4. In your jar, you can see that @spring.active@ has been replaced




