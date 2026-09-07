# Options
TrainingKit allows you to customize the **theme** of the workout experience.

To modify **theme settings** (colors, fonts, CTA styles, localization), please contact our team.

## Configuration
You can configure workout screens to change texts and screen behavior before presenting the screen.

Use the configuration before launching the workout.

**Example**
```kotlin
val workoutConfig = JSONObject().apply {
    put("skipWarmup", false)
    put("isMale", true)
    put("isHumanAudioCoachAvailable", true)
    put("userIsPremium", false)
    put("texts", JSONObject(
        mapOf(
            "startWorkoutCTA" to "On y va !",
            "endedWorkoutTitle" to "Bravo !",
            "saveWorkoutCTA" to "Quitter"
        )
    ))
}
```

## Available keys
### Root configuration

`GoFragment` receives these fields through its `extraContent` argument. `WorkoutVideoFragment` only reads `texts` from its `workoutConfig` argument.

Key  | Type | Usage
------------ | ------------ | -------------------------------------
`skipWarmup` | `Boolean` | Skips warmup sections in classic workouts when `true`. Defaults to `false`.
`isMale` | `Boolean` | Selects the male voice prompt variant where available. Defaults to `true`.
`isHumanAudioCoachAvailable` | `Boolean` | Enables human voice coach prompts when available. Defaults to `false`.
`userIsPremium` | `Boolean` | Unlocks premium workout content for the current user when `true`. Defaults to `false`.
`availableMusicPlaylists` | `JSONArray` | Music playlists available in classic workout settings. Each item must contain `style`, `label`, and `isActive`.
`quit_reasons` | `String` | JSON-encoded list of quit reasons displayed when the user quits a classic workout.
`notification_title` | `String` | Android foreground service notification title for classic workouts. Defaults to `Entrainement en cours`.
`notification_content` | `String` | Android foreground service notification body for classic workouts.
`notification_icon` | `Int` | Android drawable resource id used as the foreground service notification icon for classic workouts.
`texts` | `JSONObject` | Text overrides. Supported keys are listed below.

### Start screen
Key  | Usage
------------ | -------------------------------------
`startWorkoutCTA`     | Start screen button text. Used by `GoFragment`.


### Speech prompts
TrainingKit displays a Voice coach overview prompt at the first launch of a workout. It presents the voice coach feature. This prompt is displayed at each workout start until the user answers the Voice coach confirmation prompt.

Voice coach confirmation prompt is displayed at the end of the first section block.

Once Voice confirmation has been answered, no more voice coach prompts will be displayed.

Key  | Usage
------------ | -------------------------------------
`speechOverviewPromptTitle`     | Voice coach overview prompt title. Used by `GoFragment`.
`speechOverviewPromptHint`      | Voice coach overview prompt subtitle. Used by `GoFragment`.
`speechOverviewTurnOnCTA`       | Voice coach overview prompt Activate button text. Used by `GoFragment`.
`speechOverviewTurnOffCTA`      | Voice coach overview prompt Disable button text. Used by `GoFragment`.
`speechConfirmationPromptTitle` | Voice coach confirmation prompt title. Used by `GoFragment`.
`speechConfirmationPromptHint`  | Voice coach confirmation prompt subtitle. Used by `GoFragment`.
`speechConfirmationKeepCTA`     | Voice coach confirmation prompt Keep voice button text. Used by `GoFragment`.
`speechConfirmationTurnOffCTA`  | Voice coach confirmation prompt Disable button text. Used by `GoFragment`.

You can disable voice coach prompts with the following key.

Key  | Usage
------------ | -------------------------------------
`isHumanAudioCoachAvailable` (bool) | Add Voice coach overview prompts if available. Used by `GoFragment`.

### End screen
Key  | Usage
------------ | -------------------------------------
`endedWorkoutTitle`     | End screen main text. Used by `GoFragment` and `WorkoutVideoFragment`.
`saveWorkoutCTA`        | End screen button text. Used by `GoFragment` and `WorkoutVideoFragment`.

### Premium content
Key  | Usage
------------ | -------------------------------------
`unlockPremiumCTA`     | Premium unlock button text. Used by `GoFragment`.
`watchAdsCTA`          | Watch ads button text. Used by `GoFragment`.
