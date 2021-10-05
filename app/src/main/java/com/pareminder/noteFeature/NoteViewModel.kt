package com.pareminder.noteFeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pareminder.data.local_db.tables.Note
import com.pareminder.repository.NotesRepository
import com.pareminder.common.ScreenState
import kotlinx.coroutines.launch

class NoteViewModel(val repository: NotesRepository) : ViewModel() {
    private val _notesList = MutableLiveData<List<Note>>()
    lateinit var screenState : ScreenState
    val notes: LiveData<List<Note>> get() = _notesList

  /*  init {
        loadAllNotes()
    }
*/
    public fun loadAllNotes() {
      screenState?.lading()
        viewModelScope.launch {
            val notes = repository.getAllNotes();
            notes?.let {
                _notesList.postValue(it)
            }
            screenState?.completed()
        }
    }

    fun saveNotes(note: Note) {
        screenState?.lading()
        if (note == null) {

        } else {
            viewModelScope.launch {
                repository.saveNote(note)
                loadAllNotes()
            }
        }
    }

    fun removeNote(note: Note,position:Int) {
        screenState?.lading()
        if (note == null) {

        } else {
            viewModelScope.launch {
                repository.deleteNote(note)
                loadAllNotes()

            }
        }
    }

}