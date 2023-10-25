package com.example.devk.domain.firebase.realDatabase

import com.example.devk.domain.model.Notes
import com.example.devk.presentation.state.SaveNotesState

interface RealDatabaseUseCase {
     suspend fun saveNotesDB(notes: List<Notes>,result: (SaveNotesState<Boolean>) -> Unit)
}