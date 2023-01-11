package com.example.devk.ui.Fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.devk.Model.Notes
import com.example.devk.R
import com.example.devk.ViewModel.NotesViewModel
import com.example.devk.databinding.FragmentHomeBinding
import com.example.devk.ui.Adapter.NotesAdapter



class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var oldNotes= arrayListOf<Notes>()
    lateinit var  adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater, container,false)
        setHasOptionsMenu(true)
//        MenuItem.SHOW_AS_ACTION_ALWAYS


        val staggeredGridLayoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rcvAllNotes.layoutManager= staggeredGridLayoutManager

        //get Notes
        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->

            oldNotes = notesList as ArrayList<Notes>
            adapter = NotesAdapter(requireContext(),notesList)
            binding.rcvAllNotes.adapter = adapter

        }

        //filter all Notes
        binding.allNotes.setOnClickListener{
            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->

                oldNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            }
        }

        //filter high Notes
        binding.filterHigh.setOnClickListener{
            viewModel.getHighNotes().observe(viewLifecycleOwner) { notesList ->

                oldNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            }
        }

        //filter medium Notes
        binding.filterMedium.setOnClickListener{
            viewModel.getMediumNotes().observe(viewLifecycleOwner) { notesList ->

                oldNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            }
        }

        //filter low Notes
        binding.filterLow.setOnClickListener{
            viewModel.getLowNotes().observe(viewLifecycleOwner) { notesList ->

                oldNotes = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),notesList)
                binding.rcvAllNotes.adapter = adapter
            }
        }


        binding.btnAddNotes.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }



        return binding.root
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        val item =menu.findItem(R.id.app_bar_search)
//        val searchView = item.actionView as SearchView
//
//        searchView.queryHint = "Digite o Título desejado..."
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                NotesFilteting(newText)
//                return true
//            }
//        })
//
//
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val searchView = item.actionView as SearchView

        searchView.queryHint = "Digite o Título desejado..."
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                NotesFilteting(newText)
                return true
            }
        })
        return super.onOptionsItemSelected(item)
    }

    private fun NotesFilteting(pO: String?) {
       val newFilteredList = arrayListOf<Notes>()
        for(i in oldNotes){
            if(i.title.contains(pO!!) || i.subTitle.contains(pO!!)){
                newFilteredList.add(i)
            }

            adapter.filtering(newFilteredList)
        }
    }

}