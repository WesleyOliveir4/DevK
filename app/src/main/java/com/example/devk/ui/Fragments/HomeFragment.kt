package com.example.devk.ui.Fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.devk.Firebase.Auth.AuthModel
import com.example.devk.Firebase.NotesRD.NotesRealDatabase
import com.example.devk.Message.MessageBuilder
import com.example.devk.Model.Notes
import com.example.devk.R
import com.example.devk.Storage.StorageFormat
import com.example.devk.ViewModel.NotesViewModel
import com.example.devk.databinding.FragmentHomeBinding
import com.example.devk.ui.Adapter.NotesAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog_message_app.*
import java.io.File


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()

    private lateinit var auth: FirebaseAuth
    private var database: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()


        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
            pushRecyclerView(notesList)
        }


        binding.allNotes.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
                pushRecyclerView(notesList)
            }
        }

        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner) { notesList ->
                pushRecyclerView(notesList)
            }
        }
        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner) { notesList ->
                pushRecyclerView(notesList)
            }
        }
        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner) { notesList ->
                pushRecyclerView(notesList)
            }
        }


        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.title == "Exportar") {
            val bottomSheet: BottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_export)

            val textviewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textviewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->

                textviewYes?.setOnClickListener {
                    writeToFile(notesList, requireContext())

                    bottomSheet.dismiss()

                }
            }

            textviewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }

            bottomSheet.show()
        } else if (item.title == "CloudFirebase") {

            val bottomSheet: BottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_cloud)

            val textviewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textviewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->

                textviewYes?.setOnClickListener {

                    AuthModel().loginFirebase(requireActivity(), notesList)
                    bottomSheet.dismiss()

                }

                textviewNo?.setOnClickListener {
                    bottomSheet.dismiss()
                }

            }
            bottomSheet.show()

        } else {
            requireActivity().onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun writeToFile(data: List<Notes>, context: Context) {

        viewModel.getNotes().observe(viewLifecycleOwner) { listNotes ->
            try {
                StorageFormat().formatToTXT(listNotes)
                MessageBuilder(requireContext()).MessageShowTimer("Docs exportados com sucesso",1500)

            } catch (e: Exception) {
                MessageBuilder(requireContext()).MessageShow("Falha na exportação")
                Log.e("Exception", "Falha na exportação: $e ");
            }
        }
    }

    private fun pushRecyclerView(listNotes: List<Notes>) {
        binding.rcvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvAllNotes.adapter = NotesAdapter(requireContext(), listNotes)
    }
}