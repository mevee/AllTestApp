package com.pareminder.data.local_db

import androidx.annotation.NonNull
import androidx.room.*
import com.pareminder.data.local_db.tables.Note

@Dao
interface NotesDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(@NonNull note: Note)

    @Delete
    suspend fun deleteNote(@NonNull notes: Note)

//    @Query("SELECT * from notes_table order by id ASC")
//    suspend fun getAllNotes(): List<Note>
    @Query("SELECT * from notes_table")
    suspend fun getAllNotes(): List<Note>


}