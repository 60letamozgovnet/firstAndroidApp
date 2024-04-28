package com.example.app2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.databinding.NoteItemBinding

class NoteAdapter(val listener: Listener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    val itemList: ArrayList<Note> = ArrayList()
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = NoteItemBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(note: Note, listener: Listener) = with(binding){
            bookTitle.text = note.book_title
            notes.text = note.note_text
            countP.text = "Количество страниц: " + note.count_page.toString()
            removeBtn.setOnClickListener{
                listener.onClickRmvBtn(note)
            }
            bodyOfNote.setOnClickListener{
                listener.onClickView(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position], listener)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    interface Listener{
        fun onClickRmvBtn(note: Note)
        fun onClickView(note: Note)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNote(note: Note){
        itemList.add(note)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeNote(note: Note){
        itemList.remove(note)
        notifyDataSetChanged()
    }
}