package com.meepleprofessionals.stockkeep.update

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.appdistribution.FirebaseAppDistribution
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Handles in-app update checks using Firebase App Distribution
 * Shows update prompt when a new version is available
 */
class UpdateManager {
    
    private val firebaseAppDistribution = FirebaseAppDistribution.getInstance()
    
    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.Idle)
    val updateState: StateFlow<UpdateState> = _updateState.asStateFlow()
    
    private var currentActivity: Activity? = null
    
    fun initialize(application: Application) {
        // Register activity lifecycle callbacks
        application.registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
                override fun onActivityStarted(activity: Activity) {}
                override fun onActivityResumed(activity: Activity) {
                    currentActivity = activity
                }
                override fun onActivityPaused(activity: Activity) {
                    if (currentActivity == activity) {
                        currentActivity = null
                    }
                }
                override fun onActivityStopped(activity: Activity) {}
                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
                override fun onActivityDestroyed(activity: Activity) {}
            }
        )
        
        // Check for updates when app comes to foreground
        ProcessLifecycleOwner.get().lifecycle.addObserver(
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    checkForUpdate()
                }
            }
        )
    }
    
    /**
     * Check if a new release is available
     */
    fun checkForUpdate() {
        _updateState.value = UpdateState.Checking
        
        firebaseAppDistribution.checkForNewRelease()
            .addOnSuccessListener { release ->
                if (release != null) {
                    Log.d(TAG, "New release available: ${release.versionCode}")
                    _updateState.value = UpdateState.UpdateAvailable(
                        versionCode = release.versionCode ?: 0,
                        releaseNotes = release.releaseNotes ?: "New version available",
                        onAccept = { startUpdate() },
                        onDecline = { 
                            _updateState.value = UpdateState.Idle
                        }
                    )
                } else {
                    Log.d(TAG, "No update available")
                    _updateState.value = UpdateState.Idle
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Failed to check for updates", exception)
                _updateState.value = UpdateState.Error(exception.message ?: "Unknown error")
            }
    }
    
    /**
     * Start the in-app update flow
     */
    fun startUpdate() {
        _updateState.value = UpdateState.Downloading(0)
        
        firebaseAppDistribution.updateIfNewReleaseAvailable()
            .addOnSuccessListener {
                Log.d(TAG, "Update flow completed")
                _updateState.value = UpdateState.Completed
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Update failed", exception)
                _updateState.value = UpdateState.Error(exception.message ?: "Update failed")
            }
    }
    
    companion object {
        private const val TAG = "UpdateManager"
        
        @Volatile
        private var instance: UpdateManager? = null
        
        fun getInstance(): UpdateManager {
            return instance ?: synchronized(this) {
                instance ?: UpdateManager().also { instance = it }
            }
        }
    }
}

sealed class UpdateState {
    object Idle : UpdateState()
    object Checking : UpdateState()
    data class UpdateAvailable(
        val versionCode: Long,
        val releaseNotes: String,
        val onAccept: () -> Unit,
        val onDecline: () -> Unit
    ) : UpdateState()
    data class Downloading(val progress: Int) : UpdateState()
    object Completed : UpdateState()
    data class Error(val message: String) : UpdateState()
}
