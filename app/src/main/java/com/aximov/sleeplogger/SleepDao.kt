package com.aximov.sleeplogger

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SleepDao {

    @Query("SELECT * from sleep ORDER BY id ASC")
    fun getAll(): LiveData<List<Sleep>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sleep: Sleep)

    @Query("DELETE FROM sleep")
    suspend fun deleteAll()
}