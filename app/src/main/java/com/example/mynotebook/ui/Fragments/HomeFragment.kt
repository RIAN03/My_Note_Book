package com.example.mynotebook.ui.Fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotebook.R
import com.example.mynotebook.ViewModel.NotesViewModel
import com.example.mynotebook.databinding.FragmentHomeBinding
import com.example.mynotebook.entity.Notes
import com.example.mynotebook.ui.Adapter.NotesAdapter


class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding
    val viewModel:NotesViewModel by viewModels()
    var oldMyNotes = arrayListOf<Notes>()
    lateinit var adapter:NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        setHasOptionsMenu(true)

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager

        viewModel.getNotes().observe(viewLifecycleOwner,{notesList ->

   //         for(i in notesList){
   //             Log.e("@@@@@","onCreateView: ${i.title}")
   //         }
            oldMyNotes=notesList as ArrayList<Notes>
            binding.rcvAllNotes.layoutManager = GridLayoutManager(requireContext(),2)
            adapter = NotesAdapter(requireContext() , notesList)
            binding.rcvAllNotes.adapter = adapter
        })

        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment3_to_createNoteFragment3)
        }

        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner,{notesList ->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext() , notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }
        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner,{notesList ->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext() , notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }
        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner,{notesList ->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext() , notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }
        binding.iconFilter.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner,{notesList ->
                oldMyNotes=notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext() , notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val item = menu.findItem(R.id.app_bar_search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Enter Notes Here..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                NotesFiltering(newText)
                return true
            }

        })


        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun NotesFiltering(p0: String?) {
        val newFilteredList = arrayListOf<Notes>()

        for (i in oldMyNotes){
            if (i.title.contains(p0!!) || i.subtitle.contains(p0!!)){
                newFilteredList.add(i)
            }
        }
        adapter.filtering(newFilteredList)
    }


}