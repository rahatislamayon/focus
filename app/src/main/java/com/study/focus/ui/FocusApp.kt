package com.study.focus.ui

import android.app.Application
import com.study.focus.data.database.AppDatabase
import com.study.focus.data.SessionRepository
import com.study.focus.data.FocusTimerManager

class FocusApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    
    override fun onCreate() {
        super.onCreate()
        val repository = SessionRepository(database.sessionDao())
        FocusTimerManager.init(repository)
    }
}
