package com.example.devk.presentation.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.devk.data.Dao.NotesDao
import com.example.devk.data.Database.NotesDatabase
import com.example.devk.domain.model.Notes

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesDao

    init {
        repository = NotesDatabase.getDatabaseInstance((application)).myNotesDao()
    }

    fun addNotes(notes: Notes) {

        repository.insertNotes((notes))
    }

    fun getNotes(): LiveData<List<Notes>> = repository.getNotes()

    fun getHighNotes():LiveData<List<Notes>> =repository.getHighNotes()

    fun getMediumNotes():LiveData<List<Notes>> =repository.getMediumNotes()

    fun getLowNotes():LiveData<List<Notes>> =repository.getLowNotes()

    fun deleteNotes(id: Int) {
        repository.deleteNotes(id)
    }

    fun uptadeNotes(notes: Notes) {
        repository.updateNotes(notes)
    }
}