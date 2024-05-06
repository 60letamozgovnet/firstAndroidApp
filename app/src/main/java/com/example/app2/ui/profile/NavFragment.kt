package com.example.app2.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.BookAdapter
import com.example.app2.R

class NavFragment : Fragment() {
    private var adapter = BookAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View? {
        val view = inflater.inflate(R.layout.fragment_prof_nav, container, false)
        val rcView: RecyclerView = view.findViewById(R.id.rcViewProf)
        rcView.adapter = adapter
        val textNote: TextView = view.findViewById(R.id.empty_rcView)
        if (adapter.getSize() > 0){
            textNote.visibility = View.INVISIBLE
        }
        else{
            textNote.visibility = View.VISIBLE
        }
        return view
    }
}