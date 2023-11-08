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
import com.example.devk.presentation.state.CreateNotesState
import com.example.devk.presentation.state.SaveNotesState
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesDao
    private val factoryNotesUseCase : FactoryNotesUseCaseImpl
    private val storageNotesUseCase : StorageNotesUseCaseImpl
    private val realDatabaseUseCase : RealDatabaseUseCaseImpl

        private val _stateSaveNote by lazy { MutableLiveData<SaveNotesState<String>>() }
        val stateSaveNote: LiveData<SaveNotesState<String>> get()= _stateSaveNote

        private val _stateCreateNotes by lazy { MutableLiveData<CreateNotesState<String>>() }
        val stateCreateNotes: LiveData<CreateNotesState<String>> get()= _stateCreateNotes


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
        if(title.isNotEmpty() || subTitle.isNotEmpty() || notes.isNotEmpty() ){
            repository.insertNotes(factoryNotesUseCase.factoryNotes(it,title,subTitle,notes,priority,null))
            _stateCreateNotes.postValue(CreateNotesState.Success("Nota criada"))
        }else{
            _stateCreateNotes.postValue(CreateNotesState.Failure("Falha ao salvar, nota vazia"))
        }
    }

    fun updateNotes(it: View?, title: String, subTitle: String, notes: String, priority: String, id: Int){
        repository.updateNotes(factoryNotesUseCase.factoryNotes(it,title,subTitle,notes,priority,id))
    }

    fun writeToFile(listNotes: List<Notes>){
            storageNotesUseCase.formatToTXT(listNotes)
   }
    @Throws(Exception::class)
    fun saveRealDatabase(listNotes: List<Notes>){
           realDatabaseUseCase.saveNotesDB(listNotes, {_stateSaveNote.value = it } )
    }

}