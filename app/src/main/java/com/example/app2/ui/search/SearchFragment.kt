package com.example.app2.ui.search

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.Book
import com.example.app2.BookAdapter
import com.example.app2.GlobalDataBase
import com.example.app2.R
import com.example.app2.databinding.FragmentSearchBinding
import com.example.app2.retrofit.BookDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchFragment : Fragment(), BookAdapter.Listener {
    private val adapter = BookAdapter(this)
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val lineView: TextView = binding.searchLine
        lineView.text = ""


        val rcView: RecyclerView = binding.rcView
        rcView.adapter = adapter
        val btnSearch: Button = binding.btnSearch
        btnSearch.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                var list: List<BookDb> = GlobalDataBase().getBooks()
                for (i in list) {
                    activity?.runOnUiThread {
                        adapter.addBook(Book(i.bookname, i.author))
                    }
                }
            }
        }

        return root
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