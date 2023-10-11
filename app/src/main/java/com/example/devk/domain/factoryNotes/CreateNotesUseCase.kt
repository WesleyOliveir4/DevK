package com.example.devk.domain.factoryNotes

import android.view.View
import com.example.devk.domain.model.Notes

interface FactoryNotesUseCase {

    fun factoryNotes(it: View?, title: String, subTitle: String, notes: String, priority: String, id:Int?): Notes

}