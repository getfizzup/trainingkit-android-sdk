# TrainingKit Android SDK
This repository includes TrainingKit packages and a demo app.

## Requirements
TrainingKit SDK supports Android 5.0 or higher (API level 21).

## Installation
**1. Add your Github credentials**

Add the following two lines to the `local.properties` file located at the root of your project,
replace the placeholders with your Github account's credentials. This step is necessary to download
Github packages:
```properties
GithubUser=$YOUR_GITHUB_USERNAME
GithubToken=$YOUR_GITHUB_TOKEN
```

**2. Add the Github repository to your build file**

Add the following to your `allprojects` section in the `build.gradle` file:
```gradle
allprojects {
    repositories {
        ...
        maven {
            val properties = Properties()
            val file = File("local.properties")
            if(file.exists())
                properties.load(file.reader())

            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/getfizzup/trainingkit-android-sdk")
            credentials {
                username = properties.getProperty("GithubUser")
                password = properties.getProperty("GithubToken")
            }
        }
    }
}
```

**3. Add the dependency**

Check the available library packages [here](https://github.com/orgs/getfizzup/packages?repo_name=trainingkit-android-sdk).
```gradle
dependencies {
    implementation 'com.fysiki:trainingkit:v19'
}
```

## Documentation
1. ### **📱 [Integration guide](docs/integration.md)**
2. ### **📋 [Options](docs/options.md)**  

## Migration from WorkoutKit

TrainingKit replaces the former WorkoutKit name. Existing integrations should update the public names used by Gradle and Kotlin:

- Maven repository: `https://maven.pkg.github.com/getfizzup/trainingkit-android-sdk`
- Maven artifact: `com.fysiki:trainingkit`
- Kotlin package: `com.fysiki.trainingkit`
- Main SDK entry point: `TrainingKit`
- Demo app package: `com.fysiki.trainingkitsdkdemo`

After changing the dependency and imports, rebuild your app so generated sources such as Apollo, view binding, and data binding are recreated with the TrainingKit names.

___

FizzUp TrainingKit is a complete training module that can be inserted into your Health App.\
To subscribe to FizzUp TrainingKit contact business@fizzup.com.
