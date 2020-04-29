package com.aximov.sleeplogger

import androidx.lifecycle.LiveData

class SleepRepository(private val sleepDao: SleepDao) {

    val allSleeps: LiveData<List<Sleep>> = sleepDao.getAll()

    val sleepLengthSum: LiveData<Int> = sleepDao.getSum()

    suspend fun insert(sleep: Sleep) {
        sleepDao.insert(sleep)
    }
}