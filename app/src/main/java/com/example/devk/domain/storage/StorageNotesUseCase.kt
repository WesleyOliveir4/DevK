package com.example.devk.domain.storage

import com.example.devk.domain.model.Notes

interface StorageNotesUseCase {

    fun formatToTXT(listNotes: List<Notes>)

}