package com.aximov.sleeplogger

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sleep")
class Sleep(@PrimaryKey(autoGenerate = true) val id: Int,
            val date: Date,
            val length: Int) {
}