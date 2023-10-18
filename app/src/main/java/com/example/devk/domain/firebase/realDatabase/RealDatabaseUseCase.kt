package com.example.devk.domain.firebase.realDatabase

import com.example.devk.domain.model.Notes
import com.google.firebase.auth.FirebaseAuth

interface RealDatabaseUseCase {
     suspend fun saveNotesDB(notes: List<Notes>)
}