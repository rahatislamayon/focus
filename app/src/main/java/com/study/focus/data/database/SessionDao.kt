package com.study.focus.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Insert
    suspend fun insertSession(session: SessionEntity)

    @Query("SELECT * FROM sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<SessionEntity>>

    @Query("SELECT SUM(durationMinutes) FROM sessions WHERE isCompleted = 1")
    fun getTotalFocusMinutes(): Flow<Long?>

    @Query("SELECT COUNT(*) FROM sessions WHERE isCompleted = 1")
    fun getCompletedSessionCount(): Flow<Int>
    
    @Query("SELECT SUM(distractionAttempts) FROM sessions")
    fun getTotalDistractions(): Flow<Int?>
}
