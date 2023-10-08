package com.example.devk.domain.createNotes

import android.view.View
import com.example.devk.domain.model.Notes

interface CreateNotesUseCase {

    fun createNotes(it: View?, title: String, subTitle: String, notes: String, priority: String): Notes

}