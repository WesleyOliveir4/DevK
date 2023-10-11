package com.example.devk.presentation.ViewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.devk.data.Dao.NotesDao
import com.example.devk.data.Database.NotesDatabase
import com.example.devk.data.repository.FactoryNotesUseCaseImpl
import com.example.devk.domain.model.Notes

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesDao
    private val factoryNotesUseCase : FactoryNotesUseCaseImpl

    init {
        repository = NotesDatabase.getDatabaseInstance((application)).myNotesDao()
        factoryNotesUseCase = FactoryNotesUseCaseImpl()
    }

    fun getNotes(): LiveData<List<Notes>> = repository.getNotes()

    fun getHighNotes():LiveData<List<Notes>> =repository.getHighNotes()

    fun getMediumNotes():LiveData<List<Notes>> =repository.getMediumNotes()

    fun getLowNotes():LiveData<List<Notes>> =repository.getLowNotes()

    fun deleteNotes(id: Int) {
        repository.deleteNotes(id)
    }

    fun createNotes(it: View?, title: String, subTitle: String, notes: String, priority: String){
        repository.insertNotes(factoryNotesUseCase.factoryNotes(it,title,subTitle,notes,priority,null))
    }

    fun updateNotes(it: View?, title: String, subTitle: String, notes: String, priority: String, id: Int){
        repository.updateNotes(factoryNotesUseCase.factoryNotes(it,title,subTitle,notes,priority,id))
    }
}