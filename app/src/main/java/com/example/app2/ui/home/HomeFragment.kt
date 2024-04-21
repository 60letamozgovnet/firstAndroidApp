package com.example.app2.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.CustomDialog
import com.example.app2.Note
import com.example.app2.NoteAdapter
import com.example.app2.R
import com.example.app2.databinding.FragmentHomeBinding



class HomeFragment : Fragment(){
    private var adapter = NoteAdapter()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rcView: RecyclerView = binding.rcView
        rcView.adapter = adapter
        val btnPlus = binding.btnPlus
        btnPlus.setOnClickListener{
            val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog, null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
            val btnSave = dialogBinding.findViewById<Button>(R.id.save)
            btnSave.setOnClickListener {
                adapter.addNote(Note(
                    dialogBinding.findViewById<EditText>(R.id.titleBk).text.toString(),
                    dialogBinding.findViewById<EditText>(R.id.note).text.toString(),
                    dialogBinding.findViewById<EditText>(R.id.countPages).text.toString().toInt(),
                ))
            }
        }
        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}