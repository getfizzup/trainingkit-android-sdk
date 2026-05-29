package com.fysiki.trainingkitsdkdemo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.WindowManager
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fysiki.trainingkit.GoAction
import com.fysiki.trainingkit.fragments.GoFragment
import com.fysiki.trainingkit.interfaces.TrainingKitInterface
import com.fysiki.trainingkit.states.SaveWorkoutState
import com.fysiki.trainingkit.utils.DispatcherUtils
import com.fysiki.trainingkit.utils.JWTVerificationException
import com.fysiki.trainingkit.utils.Tracking
import com.fysiki.trainingkit.utils.WorkoutConfiguration
import com.fysiki.trainingkit.utils.download.FizzupAssetPackLocation
import com.fysiki.trainingkit.models.MusicPlaylist
import com.fysiki.trainingkitsdkdemo.databinding.ActivityWorkoutBinding
import org.jdeferred.Promise
import org.jdeferred.impl.DeferredObject
import org.json.JSONObject
import java.io.File

class WorkoutActivity : AppCompatActivity(), TrainingKitInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        val binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workoutContent = intent.getStringExtra(WORKOUT_CONTENT_KEY)?.let {
            JSONObject(it)
        } ?: JSONObject()

        val workoutConfig = JSONObject().apply {
            put("isHumanAudioCoachAvailable", true)
            put("texts", JSONObject(
                mapOf(
                    "startWorkoutCTA" to "On y va !",
                    "endedWorkoutTitle" to "Bravo !",
                    "saveWorkoutCTA" to "Quitter"
                )
            ))
        }

        val goFragment = GoFragment.newInstance(
            workoutContent,
            workoutConfig,
            intent.getStringExtra(JWT_TOKEN_KEY) ?: "",
        )

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.goFragment, goFragment, "GO_FRAGMENT")
        transaction.commit()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val navigationBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.setPadding(0, 0, 0, navigationBarInsets.bottom) // Only apply bottom padding
            insets
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33+
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                DispatcherUtils.dispatch(GoAction.Workout.BackPressed())
            }
        } else {
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    DispatcherUtils.dispatch(GoAction.Workout.BackPressed())
                }
            })
        }
    }

    // Keep screen ON
    override fun onResume() {
        super.onResume()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onStop() {
        super.onStop()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    companion object {
        private const val WORKOUT_CONTENT_KEY = "MainActivity.workoutContent"
        private const val JWT_TOKEN_KEY = "MainActivity.jwtToken"

        fun newIntent(context: Context, workoutContent: JSONObject, jwtToken: String): Intent {
            val intent = Intent(context, WorkoutActivity::class.java)
            intent.putExtra(WORKOUT_CONTENT_KEY, workoutContent.toString())
            intent.putExtra(JWT_TOKEN_KEY, jwtToken)
            return intent
        }
    }

    override fun closeGoMode(state: SaveWorkoutState?) {
        finish()
    }

    override fun displayCheckout() {
    }

    override fun enableMusicStyle(value: Any?) {
    }

    override fun disableMusicStyle(value: Any?) {
    }

    override fun fetchAllRecommendations(unlockedBlockId: String?) {
    }

    override fun getAssetsPromise(): Promise<FizzupAssetPackLocation, Void, Double>? {
        return null
    }

    override fun getPlaylists(): Promise<ArrayList<File>, Void, Double> {
        val deferred = DeferredObject<ArrayList<File>, Void, Double>()
        val promise: Promise<ArrayList<File>, Void, Double> = deferred.promise()
        deferred.resolve(arrayListOf())
        return promise
    }

    override fun getAvailablePlaylists(): ArrayList<MusicPlaylist> {
        return arrayListOf()
    }

    override fun onTokenVerificationError(exception: JWTVerificationException) {
        // Display fatal error dialog popup
        val message = "Le token JWT est invalide : ${exception.message}"
        val spannableMessage = SpannableString(message).apply {
            setSpan(
                ForegroundColorSpan(Color.BLACK), // Change to any color (e.g., Color.RED)
                0,
                message.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        }
        AlertDialog.Builder(this)
            .setTitle("Erreur de Token")
            .setMessage(spannableMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }.setOnDismissListener {
                finish()
            }.show()
    }

    override fun goNextScreen(state: SaveWorkoutState?) {
        // TODO: Implement End Screen
        finish()
    }

    override fun hideShareMenu() {
    }

    override fun musicSample(musicStyles: Any?): ArrayList<File> {
        return arrayListOf()
    }

    override fun saveWorkout(state: SaveWorkoutState) {
        DispatcherUtils.dispatch(GoAction.Workout.Saving())

        // Launch function after 5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            DispatcherUtils.dispatch(GoAction.Workout.NotifySaved())
        }, 5000)
    }

    override fun shouldDisplayCloseButton(): Boolean {
        return true
    }

    override fun trackEvent(name: Tracking.Event, properties: Map<Tracking.Property, String>?) {

    }
}