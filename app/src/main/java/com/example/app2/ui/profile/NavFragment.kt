package com.example.app2.ui.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils.split
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.Book
import com.example.app2.BookAdapter
import com.example.app2.R
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

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
        for (i in getDataFromFile("my_books.txt").split("\n")){
            val s = i.split("\\s+".toRegex())
            if (s != emptyList<String>()){
//                println(s)
//                adapter_readable.addBook(Book(s[0], s[1], "ass"))
            }
        }

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
        val btnAdd: Button = dialogBinding.findViewById(R.id.btn_add_in_my_book)

        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.CYAN))
        myDialog.show()
//        btnAdd.setOnClickListener {
//            appendNewLine("my_books.txt", title.text.toString() + authors.text.toString())
//        }
    }

    fun getDataFromFile(file: String): String {
        var fileInputStream: FileInputStream? = null
        fileInputStream = requireActivity().openFileInput(file)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
            stringBuilder.append("\n")

        }

        return stringBuilder.toString()
    }
}