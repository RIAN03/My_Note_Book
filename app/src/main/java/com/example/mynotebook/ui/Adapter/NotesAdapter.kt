package com.example.mynotebook.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotebook.R
import com.example.mynotebook.databinding.ItemNotesBinding
import com.example.mynotebook.entity.Notes
import com.example.mynotebook.ui.Fragments.HomeFragmentDirections
import java.util.ArrayList

class NotesAdapter(val requireContext: android.content.Context, var notesList: List<Notes>):
    RecyclerView.Adapter<NotesAdapter.notesViewHolder>() {
    fun filtering(newFilteringList: ArrayList<Notes>) {
        notesList = newFilteringList
        notifyDataSetChanged()
    }

    override fun getItemCount() = notesList.size

        class notesViewHolder(val binding: ItemNotesBinding):
                RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {

        return notesViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            ),
        )

    }


    override fun onBindViewHolder(holder: notesViewHolder, position: Int) {

        val data = notesList[position]
        holder.binding.notesTitle.text = data.title
        holder.binding.notesSubtitle.text = data.subtitle
        holder.binding.notesDate.text = data.date

        when(data.priority){
            "1" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.green_dot)
            }"2" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.yellow_dot)
            }"3" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.red_dot)
            }
        }

        holder.binding.root.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragment3ToEditNotesFragment3(data)
            Navigation.findNavController(it).navigate(action)
        }

    }



}