package com.example.mynotebook.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.mynotebook.R
import com.example.mynotebook.ViewModel.NotesViewModel
import com.example.mynotebook.databinding.FragmentEditNotesBinding
import com.example.mynotebook.entity.Notes
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import java.util.*


class EditNotesFragment : Fragment() {

    val oldNotes by navArgs<EditNotesFragmentArgs>()
    lateinit var binding: FragmentEditNotesBinding
    var priority:String="1"
    val viewModel : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentEditNotesBinding.inflate(layoutInflater,container,false)

        setHasOptionsMenu(true)

        val title= binding.edtTitle.setText(oldNotes.notesArg.title)
        val subtitle = binding.edtSubtitle.setText(oldNotes.notesArg.subtitle)
        val notes = binding.edtNotes.setText(oldNotes.notesArg.notes)

        when(oldNotes.notesArg.priority){
            "1" -> {
                priority ="1"
                binding.pGreen.setImageResource(R.drawable.baseline_done_24)
                binding.pYellow.setImageResource(0)
                binding.pRed.setImageResource(0)
            }"2" -> {
            priority ="2"
            binding.pYellow.setImageResource(R.drawable.baseline_done_24)
            binding.pGreen.setImageResource(0)
            binding.pRed.setImageResource(0)
            }"3" -> {
            priority ="3"
            binding.pRed.setImageResource(R.drawable.baseline_done_24)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }
        }

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

        binding.btnEditSaveNote.setOnClickListener{
            updateNotes(it)
        }

        return binding.root
    }

    private fun updateNotes(it: View?) {
        val title= binding.edtTitle.text.toString()
        val subtitle = binding.edtSubtitle.text.toString()
        val notes = binding.edtNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)
        //Log.e("@@@@@" , "createNotes: $s")

        val data = Notes(oldNotes.notesArg.id,title,subtitle,notes,notesDate.toString(),priority)
        viewModel.addNotes(data)
        Toast.makeText(requireContext(),"Notes updated successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment3_to_homeFragment3)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            val bottomSheet:BottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)

            val textviewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textviewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textviewYes?.setOnClickListener {
                viewModel.deleteNotes(oldNotes.notesArg.id!!)
                //Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment3_to_homeFragment3)//problem
                viewLifecycleOwner.lifecycleScope.launch {
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_editNotesFragment3_to_homeFragment3)
                }
                bottomSheet.dismiss()
            }

            textviewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }

            bottomSheet.show()
        }
        return super.onOptionsItemSelected(item)
    }

}