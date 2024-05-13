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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.Book
import com.example.app2.BookAdapter
import com.example.app2.GlobalDataBase
import com.example.app2.R
import com.example.app2.retrofit.BookDb
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class NavFragment : Fragment(), BookAdapter.Listener {
    private val adapter_readable = BookAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View? {
        val view = inflater.inflate(R.layout.fragment_prof_nav, container, false)
        val readable: RecyclerView = view.findViewById(R.id.readableBooks)
        val readed: RecyclerView = view.findViewById(R.id.readedBooks)
        readable.adapter = adapter_readable
        val file: List<String> = getDataFromFile("my_books.txt").split("\n")
        var author: String = ""
        var genre: String = ""
        var title: String = ""
        for(i in 0 until file.size){
            if (i % 3 == 0){
                if (author.isNotEmpty() and genre.isNotEmpty() and title.isNotEmpty()){
                    adapter_readable.addBook(Book(title, author, genre))
                }
                title = file[i]
            }
            else if(i % 3 == 1){
                author = file[i]
            }
            else if(i % 3 == 2){
                genre = file[i]
            }
        }
        if (adapter_readable.getSize() > 0){
            view.findViewById<TextView>(R.id.empty_rcView).visibility = View.INVISIBLE
        }

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