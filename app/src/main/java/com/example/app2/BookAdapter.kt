package com.example.app2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.databinding.BookItemBinding


class BookAdapter() : RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    var itemList: ArrayList<Book> = ArrayList()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are initializing our text view.
//        val item: TextView = itemView.findViewById(R.id.text_row_item)
        private val binding = BookItemBinding.bind(itemView)
        fun bind(book: Book) = with(binding){
            titleBook.text = book.title
            aboutBook.text = book.about
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addBook(book: Book){
        itemList.add(book)
        notifyDataSetChanged()
    }

    fun getSize(): Int{
        return itemList.size
    }
}