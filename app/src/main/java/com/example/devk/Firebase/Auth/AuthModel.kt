package com.example.devk.Firebase.Auth

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import com.example.devk.Firebase.NotesRD.NotesRealDatabase
import com.example.devk.Model.Notes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthModel() {

    private lateinit var auth: FirebaseAuth
    private var id:String = ""

   fun idLogado():String{ return id}

    fun loginFirebase(activity: Activity ,  listNotes: List<Notes>) {

        auth = Firebase.auth

            auth.signInWithEmailAndPassword("wlwwesley9@gmail.com","123456").addOnCompleteListener(activity) {
                    task ->

                if (task.isSuccessful){
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "Logado com sucesso")
                    try {
                        id = auth.uid!!
                        val notesRealDatabase = NotesRealDatabase(id = id, notes = listNotes)
                        notesRealDatabase.saveDB()
                        Toast.makeText(activity,"Salvo na cloud com sucesso", Toast.LENGTH_SHORT).show()
                    }catch (e : Exception){
                        Toast.makeText(activity,"Falha ao salvar", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "Erro ao logar", task.exception)
                    Toast.makeText(activity,"Falha ao salvar", Toast.LENGTH_SHORT).show()
                }


            }

    }


}