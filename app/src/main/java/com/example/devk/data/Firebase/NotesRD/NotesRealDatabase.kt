package com.example.devk.data.Firebase.NotesRD

import com.example.devk.domain.model.Notes
import com.google.firebase.database.FirebaseDatabase

class NotesRealDatabase(
    private val id: String? = null,
    private val notes: List<Notes>
) {

     fun saveDB(){
        var reference = FirebaseDatabase.getInstance().reference
        reference.child("Docs").child(id!!).setValue(notes)
    }
}