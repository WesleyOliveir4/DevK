package com.example.devk.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.devk.Model.Notes
import com.example.devk.R
import com.example.devk.ViewModel.NotesViewModel
import com.example.devk.databinding.FragmentCreateNotesBinding
import com.example.devk.databinding.FragmentHomeBinding
import java.util.*

class CreateNotesFragment : Fragment() {

    lateinit var binding: FragmentCreateNotesBinding
    var priority: String = "1"
    val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)


        binding.btnEditSaveNotes.setOnClickListener {
            createNotes(it)
        }

        binding.optGreen.setImageResource(R.drawable.ic_check)

        binding.optGreen.setOnClickListener {
            applyPriorityColor(binding.optGreen)
            priority = "1"
        }
        binding.optYellow.setOnClickListener {
            applyPriorityColor(binding.optYellow)
            priority = "2"
        }
        binding.optRed.setOnClickListener {
            applyPriorityColor(binding.optRed)
            priority = "3"
        }


        return binding.root
    }

    private fun createNotes(it: View?) {
        val title = binding.edtTitle.text.toString()
        val subTitle = binding.edtSubTitle.text.toString()
        val notes = binding.edtNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("d MMMM yyyy", d.time)

        val notesFinish = Notes(
            id = null,
            title = title,
            subTitle = subTitle,
            notes = notes,
            date = notesDate as String,
            priority = priority
        )

        viewModel.addNotes(notesFinish)

        Toast.makeText(activity,"Documento salvo",Toast.LENGTH_SHORT).show()

        Navigation.findNavController((it!!)).navigate(R.id.action_createNotesFragment3_to_homeFragment)
    }

    private fun applyPriorityColor(imgView: ImageView) {
        binding.optGreen.setImageResource(0)
        binding.optYellow.setImageResource(0)
        binding.optRed.setImageResource(0)
        imgView.setImageResource(R.drawable.ic_check)
    }

}