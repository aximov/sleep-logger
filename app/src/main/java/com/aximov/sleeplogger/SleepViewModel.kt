package com.aximov.sleeplogger

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SleepViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SleepRepository
    val allSleeps: LiveData<List<Sleep>>

    init {
        val sleepsDao = SleepRoomDatabase.getDatabase(application).sleepDao()
        repository = SleepRepository(sleepsDao)
        allSleeps = repository.allSleeps
    }

    fun insert(sleep: Sleep) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(sleep)
    }
}