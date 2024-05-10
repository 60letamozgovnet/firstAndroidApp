package com.example.app2.ui.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.Book
import com.example.app2.BookAdapter
import com.example.app2.R

class NavFragment : Fragment(), BookAdapter.Listener {
    val adapter_readable = BookAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View? {
        val view = inflater.inflate(R.layout.fragment_prof_nav, container, false)
        val readable: RecyclerView = view.findViewById(R.id.readableBooks)
        val readed: RecyclerView = view.findViewById(R.id.readedBooks)
        readable.adapter = adapter_readable
        adapter_readable.addBook(Book("TITLE", "About..."))

//        readable.adapter = adapter
//        val textNote: TextView = view.findViewById(R.id.empty_rcView)
//        if (adapter.getSize() > 0){
//            textNote.visibility = View.INVISIBLE
//        }
//        else{
//            textNote.visibility = View.VISIBLE
//        }
//        val readed: RecyclerView = view.findViewById(R.id.readedBooks)
//        readed.adapter = adapter2
//        adapter2.addBook(Book("Прочитанные книги:", ""))
//        println(adapter.getSize())
        return view
    }

    override fun OnClickView(book: Book) {
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_book, null)
        val myDialog = Dialog(requireContext())
        val title: TextView = dialogBinding.findViewById(R.id.BkTitle)
        title.text = book.title
        val authors: TextView = dialogBinding.findViewById(R.id.authorsDialog)
        authors.text = book.about
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.CYAN))
        myDialog.show()
    }
}