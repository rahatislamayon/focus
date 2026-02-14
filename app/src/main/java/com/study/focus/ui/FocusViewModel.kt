package com.study.focus.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.study.focus.data.FocusTimerManager
import com.study.focus.data.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FocusViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = SessionRepository(
        (application as FocusApp).database.sessionDao()
    )

    private val _targetDurationMinutes = MutableStateFlow(25)
    val targetDurationMinutes: StateFlow<Int> = _targetDurationMinutes.asStateFlow()

    val isSessionActive = FocusTimerManager.isSessionActive
    val remainingTimeSeconds = FocusTimerManager.remainingTimeSeconds

    fun setDuration(minutes: Int) {
        if (!isSessionActive.value) {
            _targetDurationMinutes.value = minutes.coerceIn(5, 360)
        }
    }

    fun startSession() {
        FocusTimerManager.startSession(_targetDurationMinutes.value)
    }

    fun stopSession() {
        FocusTimerManager.stopSession(completed = false)
    }

    // Analytics
    val completedSessions = repository.completedSessionsCount
    val totalFocusMinutes = repository.totalFocusMinutes
}
