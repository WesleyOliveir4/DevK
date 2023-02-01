package com.example.devk.Firebase.Auth

import com.google.firebase.database.FirebaseDatabase

class AuthModel(
    private val id: String? = null,
) {

   fun idLogado():String{ return id!!}


}