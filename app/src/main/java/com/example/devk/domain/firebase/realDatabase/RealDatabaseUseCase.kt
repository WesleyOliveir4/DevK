package com.example.devk.domain.firebase.realDatabase

import com.example.devk.domain.model.Notes
import com.example.devk.presentation.state.SaveNotesState

interface RealDatabaseUseCase {
      fun saveNotesDB(notes: List<Notes>,result: (SaveNotesState<String>) -> Unit)
}