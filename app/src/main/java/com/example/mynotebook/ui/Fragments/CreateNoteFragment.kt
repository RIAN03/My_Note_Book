package com.example.mynotebook.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mynotebook.R
import com.example.mynotebook.ViewModel.NotesViewModel
import com.example.mynotebook.databinding.FragmentCreateNoteBinding
import com.example.mynotebook.entity.Notes
import java.util.*


class CreateNoteFragment : Fragment() {

    lateinit var binding:FragmentCreateNoteBinding
    var priority:String ="1"
    val viewModel : NotesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateNoteBinding.inflate(layoutInflater,container,false)

        binding.btnSaveNote.setOnClickListener {
            createNotes(it)
        }

        binding.pGreen.setImageResource(R.drawable.baseline_done_24)
        binding.pGreen.setOnClickListener {
            priority ="1"
            binding.pGreen.setImageResource(R.drawable.baseline_done_24)
            binding.pYellow.setImageResource(0)
            binding.pRed.setImageResource(0)

        }
        binding.pYellow.setOnClickListener {
            priority ="2"
            binding.pYellow.setImageResource(R.drawable.baseline_done_24)
            binding.pGreen.setImageResource(0)
            binding.pRed.setImageResource(0)

        }
        binding.pRed.setOnClickListener {
            priority ="3"
            binding.pRed.setImageResource(R.drawable.baseline_done_24)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)

        }

        return binding.root
    }

    private fun createNotes(it: View?) {
        val title= binding.edtTitle.text.toString()
        val subtitle = binding.edtSubTitle.text.toString()
        val notes = binding.edtNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)
        //Log.e("@@@@@" , "createNotes: $s")

        val data = Notes(null,title,subtitle,notes,notesDate.toString(),priority)
        viewModel.addNotes(data)
        Toast.makeText(requireContext(),"Notes created successfully",Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_createNoteFragment3_to_homeFragment3)

    }


}