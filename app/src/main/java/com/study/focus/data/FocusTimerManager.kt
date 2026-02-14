package com.study.focus.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import com.study.focus.data.database.SessionEntity

object FocusTimerManager {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    private val _isSessionActive = MutableStateFlow(false)
    val isSessionActive: StateFlow<Boolean> = _isSessionActive.asStateFlow()

    private val _remainingTimeSeconds = MutableStateFlow(0L)
    val remainingTimeSeconds: StateFlow<Long> = _remainingTimeSeconds.asStateFlow()

    private var timer: Timer? = null
    private var repository: SessionRepository? = null
    private var currentSessionId: Long = 0

    fun init(repo: SessionRepository) {
        repository = repo
    }

    fun startSession(durationMinutes: Int) {
        if (_isSessionActive.value) return

        _remainingTimeSeconds.value = durationMinutes * 60L
        _isSessionActive.value = true
        
        // Save start time
        val startTime = System.currentTimeMillis()
        currentSessionId = startTime // Simplistic ID

        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val remaining = _remainingTimeSeconds.value
                if (remaining > 0) {
                    _remainingTimeSeconds.value = remaining - 1
                } else {
                    stopSession(completed = true, durationMinutes = durationMinutes)
                }
            }
        }, 1000, 1000)
    }

    fun stopSession(completed: Boolean, durationMinutes: Int = 0) {
        timer?.cancel()
        timer = null
        _isSessionActive.value = false
        
        // Save to DB
        if (completed) {
            scope.launch {
                repository?.insert(
                    SessionEntity(
                        startTime = currentSessionId,
                        endTime = System.currentTimeMillis(),
                        durationMinutes = durationMinutes,
                        distractionAttempts = 0, // TODO: Track distractions
                        isCompleted = true
                    )
                )
            }
        }
    }
}
