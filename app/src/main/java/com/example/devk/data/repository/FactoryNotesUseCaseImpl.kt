package com.example.devk.data.repository

import android.text.format.DateFormat
import android.view.View
import com.example.devk.domain.factoryNotes.FactoryNotesUseCase
import com.example.devk.domain.model.Notes
import java.util.*

class FactoryNotesUseCaseImpl : FactoryNotesUseCase {

    override fun factoryNotes(it: View?, title: String, subTitle: String, notes: String, priority: String, id: Int?) : Notes {

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("d MMMM yyyy", d.time)

        return Notes(
            id = id,
            title = title,
            subTitle = subTitle,
            notes = notes,
            date = notesDate as String,
            priority = priority
        )

    }

}