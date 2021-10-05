package com.pareminder.noteFeature

import androidx.lifecycle.*
import com.pareminder.repository.NotesRepository
import androidx.lifecycle.ViewModel


class NoteViewModelFactory(private val repository: NotesRepository) :  ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return NoteViewModel(repository) as T
    }
}