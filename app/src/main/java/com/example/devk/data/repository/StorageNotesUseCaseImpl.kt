package com.example.devk.data.repository

import android.os.Environment
import com.example.devk.domain.model.Notes
import com.example.devk.domain.storage.StorageNotesUseCase
import java.io.File

class StorageNotesUseCaseImpl(): StorageNotesUseCase {
    @Throws(Exception::class)
    override fun formatToTXT(listNotes: List<Notes>) {

        val path = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        )

        val file = File(path, "DataBaseDevK.txt")

        if (!file.exists()) {
            File.createTempFile("DataBaseDevK", ".txt", path);
        }

            var noteFormat: String = ""
            var noteTXT: String = ""

            val notesLista: List<Notes> = listNotes
            for (i in 0 until notesLista.size) {
                val data = notesLista[i]
                noteFormat = buildString {
                    append(

                        "\n \"${i}\":{ \n" +
                                "   \"Id\":\"${data.id}\",\n" +
                                "   \"Titulo\":\"${data.title}\",\n" +
                                "   \"SubTitulo\":\"${data.subTitle}\",\n" +
                                "   \"Doc\":\"${data.notes}\",\n" +
                                "   \"Prioridade\":\"${data.priority}\",\n" +
                                "   \"Data\":\"${data.date}\"\n"

                    )
                }
                noteTXT = noteTXT.plus(noteFormat)
            }
            file.writeText(noteTXT)

    }

}