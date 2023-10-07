package com.example.devk.data.Firebase.Auth

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import com.example.devk.data.Firebase.NotesRD.NotesRealDatabase
import com.example.devk.data.Message.MessageBuilder
import com.example.devk.domain.model.Notes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthModel() {

    private lateinit var auth: FirebaseAuth
    private var id:String = ""

//   fun idLogado():String{ return id}

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
                        MessageBuilder(activity).MessageShowTimer("Salvo na cloud com sucesso",1500)

                    }catch (e : Exception){
                        Log.e(ContentValues.TAG, "Falha ao salvar", task.exception)
                        MessageBuilder(activity).MessageShow("Falha ao salvar")
                    }

                }else{
                    // If sign in fails, display a message to the user.
                    Log.e(ContentValues.TAG, "Erro ao logar", task.exception)
                    MessageBuilder(activity).MessageShow("Falha ao salvar")
                }


            }

    }


}