package com.aximov.sleeplogger

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SleepDao {

    @Query("SELECT * from sleep ORDER BY id DESC")
    fun getAll(): LiveData<List<Sleep>>

    @Query("SELECT SUM(length) from sleep")
    fun getSum(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sleep: Sleep)

    @Query("DELETE FROM sleep")
    suspend fun deleteAll()
}