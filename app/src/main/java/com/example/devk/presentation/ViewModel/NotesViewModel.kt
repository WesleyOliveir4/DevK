package com.example.devk.presentation.ViewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.devk.data.Dao.NotesDao
import com.example.devk.data.Database.NotesDatabase
import com.example.devk.data.repository.FactoryNotesUseCaseImpl
import com.example.devk.data.repository.RealDatabaseUseCaseImpl
import com.example.devk.data.repository.StorageNotesUseCaseImpl
import com.example.devk.domain.model.Notes
import com.example.devk.presentation.state.SaveNotesState
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesDao
    private val factoryNotesUseCase : FactoryNotesUseCaseImpl
    private val storageNotesUseCase : StorageNotesUseCaseImpl
    private val realDatabaseUseCase : RealDatabaseUseCaseImpl

        private val _state by lazy { MutableLiveData<SaveNotesState<Boolean>>() }
        val state: LiveData<SaveNotesState<Boolean>> get()= _state


    init {
        repository = NotesDatabase.getDatabaseInstance((application)).myNotesDao()
        factoryNotesUseCase = FactoryNotesUseCaseImpl()
        storageNotesUseCase = StorageNotesUseCaseImpl()
        realDatabaseUseCase = RealDatabaseUseCaseImpl()
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

    fun writeToFile(listNotes: List<Notes>){
            storageNotesUseCase.formatToTXT(listNotes)
   }
    @Throws(Exception::class)
    fun saveRealDatabase(listNotes: List<Notes>){
        viewModelScope.launch {
           realDatabaseUseCase.saveNotesDB(listNotes, {_state.value = it } )
        }
    }

}