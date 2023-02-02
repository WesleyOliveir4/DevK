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
import com.example.devk.Model.Notes
import com.example.devk.R
import com.example.devk.ViewModel.NotesViewModel
import com.example.devk.databinding.FragmentHomeBinding
import com.example.devk.ui.Adapter.NotesAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
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
            bottomSheet.setContentView(R.layout.dialog_cloud)

            val textviewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textviewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->

                textviewYes?.setOnClickListener {
                   writeToFile(notesList,requireContext())

                    bottomSheet.dismiss()

                }
            }

            textviewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }

            bottomSheet.show()
        }else if(item.title == "CloudFirebase"){

            val bottomSheet: BottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_cloud)

            val textviewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textviewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->

                textviewYes?.setOnClickListener {

                    //verificar se é plausivel encaixar o bloco abaixo no Auth
                    //se logar retorna um id e com esse id eu salvo no firebase

                    auth.signInWithEmailAndPassword("wlwwesley9@gmail.com","123456").addOnCompleteListener(requireActivity()) {
                            task ->
                        //questionar antes se o usuario deseja
                        //refatorar
                        if (task.isSuccessful){
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Logado com sucesso")
                            val authModel = AuthModel(auth.uid)
                            val notesRealDatabase = NotesRealDatabase(id = authModel.idLogado(), notes = notesList )
                            notesRealDatabase.saveDB()

                        }else{
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Erro ao logar", task.exception)

                        }

                    }

                    bottomSheet.dismiss()

                }

                textviewNo?.setOnClickListener {
                    bottomSheet.dismiss()
                }

                }
            bottomSheet.show()

        }
        else {
            requireActivity().onBackPressed()
        }


        return super.onOptionsItemSelected(item)
    }

    private fun writeToFile(data: List<Notes>, context: Context) {


        //interface ou uma Classe?

        val path = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        )
        val file = File(path, "DataBaseDevK.txt")


        try {

                if (!file.exists()) {
                    File.createTempFile("DataBaseDevK", ".txt", path);
                }

                viewModel.getNotes().observe(viewLifecycleOwner){ listNotes ->

                    var teste: String = ""
                    var teste2: String = ""
                    val notesLista: List<Notes> = listNotes
                    for (i in 0 until notesLista.size) {
                        val data = notesLista[i]
                        teste = buildString {
                            append(

                                "\n \"${i}\":{ \n" +
                                        "   \"Titulo\":\"${data.title}\",\n" +
                                        "   \"SubTitulo\":\"${data.subTitle}\",\n" +
                                        "   \"Doc\":\"${data.notes}\",\n" +
                                        "   \"Prioridade\":\"${data.priority}\",\n" +
                                        "   \"Data\":\"${data.date}\"\n"

                            )
                        }
                        teste2 = teste2.plus(teste)
                    }
                    file.writeText(teste2)
                }


        } catch (e: Exception) {
            Log.e("Exception", "Falha na exportação: " + e.toString());
        }
        Toast.makeText(getContext(),"Docs exportados com sucesso", Toast.LENGTH_SHORT).show()
    }

    private fun pushRecyclerView(listNotes: List<Notes>){
        binding.rcvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvAllNotes.adapter = NotesAdapter(requireContext(), listNotes)
    }
}