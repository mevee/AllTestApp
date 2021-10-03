package com.pareminder.data.repository

import com.pareminder.data.local_db.NotesDatabase
import com.pareminder.data.local_db.tables.Note

class NotesRepository(private val noteDb: NotesDatabase) {

    suspend fun getAllNotes() = noteDb.notesDao.getAllNotes()

    suspend fun saveNote(note: Note) = noteDb.notesDao.insertNote(note)
    suspend fun deleteNote(note: Note) = noteDb.notesDao.deleteNote(note)


}