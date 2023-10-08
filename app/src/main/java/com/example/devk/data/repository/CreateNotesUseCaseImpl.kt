package com.example.devk.data.repository

import android.text.format.DateFormat
import android.view.View
import com.example.devk.domain.createNotes.CreateNotesUseCase
import com.example.devk.domain.model.Notes
import java.util.*

class CreateNotesUseCaseImpl : CreateNotesUseCase {

    override fun createNotes(it: View?, title: String, subTitle: String, notes: String, priority: String) : Notes {

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("d MMMM yyyy", d.time)

        return Notes(
            id = null,
            title = title,
            subTitle = subTitle,
            notes = notes,
            date = notesDate as String,
            priority = priority
        )

    }

}