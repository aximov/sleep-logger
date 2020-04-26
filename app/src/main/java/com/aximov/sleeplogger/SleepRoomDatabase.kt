package com.aximov.sleeplogger

import android.content.Context
import androidx.room.*

@Database(entities = [Sleep::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SleepRoomDatabase : RoomDatabase() {

    abstract fun sleepDao(): SleepDao

    companion object {
        // Singleton pattern

        @Volatile
        private var INSTANCE: SleepRoomDatabase? = null

        fun getDatabase(context: Context): SleepRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SleepRoomDatabase::class.java,
                    "sleep"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}