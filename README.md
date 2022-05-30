# Talky Android

![Authors](https://contrib.rocks/image?repo=Anthony-Jhoiro/talky-android)

- __Anthony Quéré__
- __Henri Boulnois__
- __Adrien Bassail__

```
.
├── .gitignore
├── app
│     └── src
│         ├── main
│         │     ├── AndroidManifest.xml
│         │     ├── java
│         │     │     └── com
│         │     │         └── talky
│         │     │             └── mobile
│         │     │                 ├── TalkyApplication.kt
│         │     │                 ├── api
│         │     │                 │     ├── apis                            // Retrofit API interfaces  (generated)
│         │     │                 │     ├── clients                         // retrofit clients provider
│         │     │                 │     ├── infrastructure                  // Retrofit parser tools (partially generated)
│         │     │                 │     ├── models                          // Talky API models (generated)
│         │     │                 │     ├── pagingSource                    // sources for pagination to interract with pagination queries
│         │     │                 │     └── services                        // Servces to interracto with the API
│         │     │                 ├── facades                               // Facades for external services
│         │     │                 └── ui
│         │     │                     ├── AuthenticationViewModel.kt        // View model for authentication related requests
│         │     │                     ├── EntryPointActivity.kt             // Main navigation
│         │     │                     ├── commons                           // Global components
│         │     │                     ├── features
│         │     │                     │     ├── feed                        // Feed screen
│         │     │                     │     ├── friendRequestsList          // Friend request list screen
│         │     │                     │     ├── friends                     // Friends screen
│         │     │                     │     ├── fullScreenImage             // Full screen image screen
│         │     │                     │     ├── loading                     // Loading screen
│         │     │                     │     ├── login                       // Login screen
│         │     │                     │     ├── messaging                   // Messages screen
│         │     │                     │     ├── postCreation                // Post Creation sscreen
│         │     │                     │     ├── profile                     // Profile sceen
│         │     │                     │     └── userSearch                  // User search screen
│         │     │                     └── theme                             // Material Theme 
│         │     ├── logo-playstore.png
│         │     └── res                                                     // Assets
│         └── test
│             └── java
│                 └── com
│                     └── talky
│                         └── mobile
│                             └── ExampleUnitTest.kt
├── build.gradle
├── gradle
│     └── wrapper
│         ├── gradle-wrapper.jar
│         └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
├── openapi-codegen.sh                                                      // Script for codegeneration
└── settings.gradle

```

## Api Code generation

The API exposes an OpenAPI API, that's allows us to use code generation for our API interfaces. 
The codegen sript located at `./openapi-codegen.sh`, it needs to be run a a linux system with docker installed. 