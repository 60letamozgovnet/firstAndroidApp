package com.example.app2.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.app2.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return onCreateView(inflater, container, savedInstanceState)
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val lineView: TextView = binding.searchLine
        searchViewModel.text.observe(viewLifecycleOwner) {
            lineView.text = ""
        }
        return root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.btnSearch.setOnClickListener {
//            val fragmentTransaction = parentFragmentManager.beginTransaction()
//            fragmentTransaction.replace(
//                R.id.fragment_search, SearchedBooksFragment()
//                )
//            fragmentTransaction.commit()
////        }
//    }


}