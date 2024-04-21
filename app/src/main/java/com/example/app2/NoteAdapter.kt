package com.example.app2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.databinding.NoteItemBinding

class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    val itemList: ArrayList<Note> = ArrayList()
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = NoteItemBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(note: Note) = with(binding){
            bookTitle.text = note.book_title
            notes.text = note.note_text
            countP.text = "Количество страниц: " + note.count_page.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNote(note: Note){
        itemList.add(note)
        notifyDataSetChanged()
    }
}