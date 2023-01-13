package com.example.devk.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.devk.MainActivity
import com.example.devk.Model.Notes
import com.example.devk.R
import com.example.devk.ViewModel.NotesViewModel
import com.example.devk.databinding.FragmentCreateNotesBinding
import com.example.devk.databinding.FragmentEditNotesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class EditNotesFragment: Fragment() {

    val oldNotes by navArgs<EditNotesFragmentArgs>()
    lateinit var  binding: FragmentEditNotesBinding
    val viewModel: NotesViewModel by viewModels()



    var priority: String = "1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditNotesBinding.inflate(layoutInflater , container, false)
        setHasOptionsMenu(true)

        binding.edtTitle.setText(oldNotes.data.title)
        binding.edtSubTitle.setText(oldNotes.data.subTitle)
        binding.edtNotes.setText(oldNotes.data.notes)


        when(oldNotes.data.priority){
            "1"->{
                applyPriorityColor(binding.optEditGreen)
                priority = "1"
            }
            "2"->{
                applyPriorityColor(binding.optEditYellow)
                priority = "2"
            }
            "3"->{
                applyPriorityColor(binding.optEditRed)
                priority = "3"
            }
        }

        binding.optEditGreen.setOnClickListener {
            applyPriorityColor(binding.optEditGreen)
            priority = "1"
        }
        binding.optEditYellow.setOnClickListener {
            applyPriorityColor(binding.optEditYellow)
            priority = "2"
        }
        binding.optEditRed.setOnClickListener {
            applyPriorityColor(binding.optEditRed)
            priority = "3"
        }


        binding.btnEditSaveNotes.setOnClickListener{
            updateNotes(it)
        }

        return binding.root
    }

    private fun updateNotes(it: View?) {

        val title = binding.edtTitle.text.toString()
        val subTitle = binding.edtSubTitle.text.toString()
        val notes = binding.edtNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("d MMMM yyyy", d.time)

        val notesFinish = Notes(
            oldNotes.data.id,
            title = title,
            subTitle = subTitle,
            notes = notes,
            date = notesDate as String,
            priority = priority
        )

        viewModel.uptadeNotes(notesFinish)

        Toast.makeText(activity,"Documento salvo", Toast.LENGTH_SHORT).show()

        Navigation.findNavController((it!!)).navigate(R.id.action_editNotesFragment_to_homeFragment)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun applyPriorityColor (imgView: ImageView) {
        binding.optEditGreen.setImageResource(0)
        binding.optEditYellow.setImageResource(0)
        binding.optEditRed.setImageResource(0)
        imgView.setImageResource(R.drawable.ic_check)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.title == "Delete"){
            val bottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)

            val textviewYes=bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textviewNo=bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textviewYes?.setOnClickListener{
                viewModel.deleteNotes(oldNotes.data.id!!)
                bottomSheet.dismiss()

            }
            textviewNo?.setOnClickListener{
                bottomSheet.dismiss()
            }

            bottomSheet.show()
        }else{
            requireActivity().onBackPressed()
        }


        return super.onOptionsItemSelected(item)
    }


}