package com.example.app2.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.Note
import com.example.app2.NoteAdapter
import com.example.app2.R
import com.example.app2.databinding.FragmentHomeBinding
import java.io.FileOutputStream


class HomeFragment : Fragment(), NoteAdapter.Listener {
    private var adapter = NoteAdapter(this)
    private var _binding: FragmentHomeBinding? = null



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val rcView: RecyclerView = binding.rcView
        val textView: TextView = binding.textView
        rcView.adapter = adapter
        val btnPlus = binding.btnPlus
        btnPlus.setOnClickListener{
            val dialogBinding = layoutInflater.inflate(R.layout.custom_dialog_add_note, null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
            val btnSave = dialogBinding.findViewById<Button>(R.id.save)
            btnSave.setOnClickListener {
                rcView.visibility = View.VISIBLE;
                textView.visibility = View.INVISIBLE
                val title = dialogBinding.findViewById<EditText>(R.id.titleBk).text.toString()
                val note = dialogBinding.findViewById<EditText>(R.id.note).text.toString()
                var count: Int
                if (dialogBinding.findViewById<EditText>(R.id.countPages).text.toString() == ""){
                    count = 1
                }
                else{
                    count = dialogBinding.findViewById<EditText>(R.id.countPages).text.toString().toInt()
                }
                adapter.addNote(Note(
                    title, note, count
                ))
                appendNewLine("notes.txt", title + "\n" + note + "\n" + count)
                myDialog.dismiss()
            }

        }

        return root

    }

    override fun onClickRmvBtn(note: Note) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Вы точно хотите удалить заметку?")
        builder.setPositiveButton("Ок") { dialog, _ ->
            // Implement the Code when OK Button is Clicked
            adapter.removeNote(note)
            dialog.dismiss()
        }
        builder.setNegativeButton("Отмена") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }

    override fun onClickView(note: Note) {

    }

    fun appendNewLine(file: String, data: String) {
        val fileOutputStream: FileOutputStream
        fileOutputStream = requireActivity().openFileOutput(file, Context.MODE_APPEND)
        fileOutputStream.write(data.toByteArray())
        fileOutputStream.write("\n".toByteArray())

        println("Append new line inside file")
        println(data)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}