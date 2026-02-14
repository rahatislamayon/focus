package com.study.focus.data

import com.study.focus.data.database.SessionDao
import com.study.focus.data.database.SessionEntity
import kotlinx.coroutines.flow.Flow

class SessionRepository(private val sessionDao: SessionDao) {
    val allSessions: Flow<List<SessionEntity>> = sessionDao.getAllSessions()
    val totalFocusMinutes: Flow<Long?> = sessionDao.getTotalFocusMinutes()
    val completedSessionsCount: Flow<Int> = sessionDao.getCompletedSessionCount()
    val totalDistractions: Flow<Int?> = sessionDao.getTotalDistractions()

    suspend fun insert(session: SessionEntity) {
        sessionDao.insertSession(session)
    }
}
