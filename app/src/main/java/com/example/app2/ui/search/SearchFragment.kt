package com.example.app2.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.Book
import com.example.app2.BookAdapter
import com.example.app2.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private val adapter = BookAdapter()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val lineView: TextView = binding.searchLine
        lineView.text = ""


        val rcView: RecyclerView = binding.rcView
        rcView.adapter = adapter
        val btnSearch: Button = binding.btnSearch
        btnSearch.setOnClickListener{
            val book = Book(lineView.text.toString(), "about...")
            adapter.addBook(book)
        }

        return root
    }
}