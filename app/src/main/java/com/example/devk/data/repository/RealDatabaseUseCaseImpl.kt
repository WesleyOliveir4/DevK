package com.example.devk.data.repository

import android.content.ContentValues
import android.util.Log
import com.example.devk.R
import com.example.devk.domain.model.Notes
import com.example.devk.domain.firebase.realDatabase.RealDatabaseUseCase
import com.example.devk.presentation.state.SaveNotesState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class RealDatabaseUseCaseImpl(): RealDatabaseUseCase {

    private lateinit var auth: FirebaseAuth

    @Throws(Exception::class)
    override fun saveNotesDB(notes: List<Notes>, result: (SaveNotesState<String>) -> Unit) {
        auth = Firebase.auth

     auth.signInWithEmailAndPassword("wlwwesley9@gmail.com","123456").addOnCompleteListener {
                task ->

            if (task.isSuccessful){
                result.invoke(SaveNotesState.Success("Logado com sucesso"))
                // Sign in success, update UI with the signed-in user's information
                Log.d(ContentValues.TAG, "Logado com sucesso")
                FirebaseDatabase.getInstance().reference.child("Docs").child(auth.uid!!).setValue(notes)

            }else{
                // If sign in fails, display a message to the user.
                result.invoke(SaveNotesState.Failure(task.exception?.message.toString()))
                Log.e(ContentValues.TAG, "Erro ao logar", task.exception)
            }

        }

    }
}