package com.example.app2.ui.searchedBooks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.app2.databinding.FragmentSearchedBooksBinding


class SearchedBooksFragment : Fragment() {

    private var _binding: FragmentSearchedBooksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return onCreateView(inflater, container, savedInstanceState)
        val searchedBooksViewModel =
            ViewModelProvider(this).get(SearchedBooksViewModel::class.java)

        _binding = FragmentSearchedBooksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val lineView: TextView = binding.searchLine
        searchedBooksViewModel.text.observe(viewLifecycleOwner) {
            lineView.text = ""
        }
        return root
    }
}