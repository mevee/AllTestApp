package com.pareminder.data.local_db

import android.content.Context
import android.os.strictmode.InstanceCountViolation
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pareminder.data.local_db.tables.Note

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract val notesDao: NotesDao

    companion object {

        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase {
            synchronized(this) {
                return INSTANCE ?:Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "pa_reminder_db"
                ).build().also {
                    INSTANCE=it
                }
            }
        }


    }
/*Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabase::class.java,
                    "pa_reminder_db"
                ).build()*/
}