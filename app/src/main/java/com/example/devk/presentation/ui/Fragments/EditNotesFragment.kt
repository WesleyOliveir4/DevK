package com.example.devk.presentation.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.devk.data.Message.MessageBuilder
import com.example.devk.domain.model.Notes
import com.example.devk.R
import com.example.devk.presentation.ViewModel.NotesViewModel
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
            try {
                viewModel.updateNotes(
                    it,
                    binding.edtTitle.text.toString(),
                    binding.edtSubTitle.text.toString(),
                    binding.edtNotes.text.toString(),
                    priority,
                    oldNotes.data.id!!)

                MessageBuilder(requireContext()).MessageShowTimer( getString(R.string.update_notes_success),1500)
                Navigation.findNavController((it!!)).navigate(R.id.action_editNotesFragment_to_homeFragment)
            }catch (e: Exception){
                Log.e("exception in editSaveNotesListener ","$e")
                MessageBuilder(requireContext()).MessageShow(getString(R.string.update_notes_failure))
            }

        }

        return binding.root
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
               try{
                   viewModel.deleteNotes(oldNotes.data.id!!)
                   MessageBuilder(requireContext()).MessageShowTimer(getString(R.string.delete_notes_success),1500)
                   bottomSheet.dismiss()
                   requireActivity().onBackPressed()
               }catch(e: Exception){
                   MessageBuilder(requireContext()).MessageShow(getString(R.string.delete_notes_failure))
               }

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