package com.example.devk.presentation.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.devk.data.Message.MessageBuilder
import com.example.devk.domain.model.Notes
import com.example.devk.R
import com.example.devk.presentation.ViewModel.NotesViewModel
import com.example.devk.databinding.FragmentCreateNotesBinding
import java.util.*

class CreateNotesFragment : Fragment() {

    lateinit var binding: FragmentCreateNotesBinding
    var priority: String = "1"
    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        binding.btnEditSaveNotes.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val subTitle = binding.edtSubTitle.text.toString()
            val notes = binding.edtNotes.text.toString()

            viewModel.createNotes(it,title,subTitle,notes,priority)
            MessageBuilder(requireContext()).MessageShowTimer("Documento salvo",1500)
            Navigation.findNavController((it!!)).navigate(R.id.action_createNotesFragment3_to_homeFragment)
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

    private fun applyPriorityColor(imgView: ImageView) {
        binding.optGreen.setImageResource(0)
        binding.optYellow.setImageResource(0)
        binding.optRed.setImageResource(0)
        imgView.setImageResource(R.drawable.ic_check)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        requireActivity().onBackPressed()
        return super.onOptionsItemSelected(item)
    }


}