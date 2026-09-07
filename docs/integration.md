### Classic Workout
TrainingKit allows you to display a **classic workout** with a list of video exercises.

**Features:**
- Navigate between exercises.
- View exercise details.
- Play and pause the workout.
- Coach voice guidance for the user.

The SDK provides an **Android Fragment** that can be embedded in your activity layout.

---

**Fragment Initialization**
```kotlin
val extraContent = JSONObject().apply {
    put("isHumanAudioCoachAvailable", false)
    put("texts", JSONObject(
        mapOf(
            "startWorkoutCTA" to "On y va !",
            "endedWorkoutTitle" to "Bravo !",
            "saveWorkoutCTA" to "Quitter"
        )
    ))
} // Configuration of the workout (see docs/options.md)

val goFragment = GoFragment.newInstance(
    workoutContent,                              // Data from our GraphQL API
    extraContent,                                // Configuration of the workout
    JwtToken,                                    // JWT token from our GraphQL API
    workoutFileId = workoutContent.optString("id") // Optional stable state identifier
)
```

**Supported configuration**

`GoFragment.newInstance` supports the following parameters:

Parameter | Type | Usage
------------ | ------------ | -------------------------------------
`workoutContent` | `JSONObject` | Workout data from the GraphQL API.
`extraContent` | `JSONObject` | Optional configuration object. See supported fields below.
`JwtToken` | `String` | JWT token from the GraphQL API.
`workoutFileId` | `String` | Optional identifier used to persist and restore workout state. Defaults to a random UUID.

`extraContent` supports the following fields:

Field | Type | Usage
------------ | ------------ | -------------------------------------
`skipWarmup` | `Boolean` | Skips warmup sections when `true`. Defaults to `false`.
`isMale` | `Boolean` | Selects the male voice prompt variant where available. Defaults to `true`.
`isHumanAudioCoachAvailable` | `Boolean` | Enables the human voice coach prompts when available. Defaults to `false`.
`userIsPremium` | `Boolean` | Unlocks premium workout content for the current user when `true`. Defaults to `false`.
`availableMusicPlaylists` | `JSONArray` | Available music choices. Each item must contain `style`, `label`, and `isActive`.
`quit_reasons` | `String` | JSON-encoded list of quit reasons displayed when the user quits a workout.
`notification_title` | `String` | Android foreground service notification title. Defaults to `Entrainement en cours`.
`notification_content` | `String` | Android foreground service notification body.
`notification_icon` | `Int` | Android drawable resource id used for the foreground service notification icon.
`texts` | `JSONObject` | Optional text overrides. See [Options](options.md).

Supported `texts` keys for `GoFragment`:

Field | Type | Usage
------------ | ------------ | -------------------------------------
`startWorkoutCTA` | `String` | Start screen button text.
`speechOverviewPromptTitle` | `String` | Voice coach overview prompt title.
`speechOverviewPromptHint` | `String` | Voice coach overview prompt subtitle.
`speechOverviewTurnOnCTA` | `String` | Voice coach overview prompt Activate button text.
`speechOverviewTurnOffCTA` | `String` | Voice coach overview prompt Disable button text.
`speechConfirmationPromptTitle` | `String` | Voice coach confirmation prompt title.
`speechConfirmationPromptHint` | `String` | Voice coach confirmation prompt subtitle.
`speechConfirmationKeepCTA` | `String` | Voice coach confirmation prompt Keep voice button text.
`speechConfirmationTurnOffCTA` | `String` | Voice coach confirmation prompt Disable button text.
`endedWorkoutTitle` | `String` | End screen main text.
`saveWorkoutCTA` | `String` | End screen button text.
`unlockPremiumCTA` | `String` | Premium unlock button text.
`watchAdsCTA` | `String` | Watch ads button text.

---

### Video Workout
TrainingKit also supports video-based workouts with the following features:

- Skip forward or backward 15 seconds.
- Play and pause the video.
Like the classic workout, the SDK provides an Android Fragment for easy integration into your activity layout.

**Fragment initialization**
```kotlin
val workoutContent = Data from our graphql api

val workoutConfig = JSONObject().apply {
    put("texts", JSONObject(mapOf(
        "startWorkoutCTA" to "On y va !",
        "endedWorkoutTitle" to "Bravo !",
        "saveWorkoutCTA" to "Quitter"
    )))
}

val videoFragment = WorkoutVideoFragment.newInstance(
    workoutContent,                              // Data from our GraphQL API
    workoutConfig,                               // Configuration of the workout
    JwtToken,                                    // JWT token from our GraphQL API
    castAvailable = false                       // Optional Google Cast availability flag
)
```

**Supported configuration**

`WorkoutVideoFragment.newInstance` supports the following parameters:

Parameter | Type | Usage
------------ | ------------ | -------------------------------------
`workoutContent` | `JSONObject` | Workout video data from the GraphQL API.
`workoutConfig` | `JSONObject?` | Optional configuration object. See supported fields below.
`JwtToken` | `String` | JWT token from the GraphQL API.
`castAvailable` | `Boolean` | Enables Google Cast controls when Google Cast is available. Defaults to `false`.

`workoutConfig` supports the following fields:

Field | Type | Usage
------------ | ------------ | -------------------------------------
`texts` | `JSONObject` | Optional text overrides. `WorkoutVideoFragment` uses `endedWorkoutTitle` and `saveWorkoutCTA`. See [Options](options.md).

Supported `texts` keys for `WorkoutVideoFragment`:

Field | Type | Usage
------------ | ------------ | -------------------------------------
`endedWorkoutTitle` | `String` | End screen main text.
`saveWorkoutCTA` | `String` | End screen button text.

**Keep screen on**
Add the following permission in AndroidManifest.xml to keep the screen on during the workout.
```xml
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

Add the following code to your activity to keep the screen on during the workout.
```kotlin
    // Keep screen ON
override fun onResume() {
    super.onResume()
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}

override fun onStop() {
    super.onStop()
    window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}
```
