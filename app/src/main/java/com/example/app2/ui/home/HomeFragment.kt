package com.example.app2.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.service.autofill.Validators.and
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
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader


class HomeFragment : Fragment(), NoteAdapter.Listener {
    private var adapter = NoteAdapter(this)
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val rcView: RecyclerView = view.findViewById(R.id.rcView)
        val textView: TextView = view.findViewById(R.id.textView)
        rcView.adapter = adapter
        val notes = getDataFromFile("notes.txt").split("\n")
        if (notes.size > 1){
            println("u are this")
            rcView.visibility = View.VISIBLE
            textView.visibility = View.INVISIBLE
        }

        val btnPlus = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btnPlus)
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
                val count: Int =
                    if (dialogBinding.findViewById<EditText>(R.id.countPages).text.toString() == ""){
                        1
                    } else{
                        dialogBinding.findViewById<EditText>(R.id.countPages).text.toString().toInt()
                    }
                adapter.addNote(Note(
                    title, note, count
                ))
                appendNewLine("notes.txt", title + "\n" + note + "\n" + count + "\n")
                myDialog.dismiss()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rcView: RecyclerView = view.findViewById(R.id.rcView)
        val textView: TextView = view.findViewById(R.id.textView)
        val notes = getDataFromFile("notes.txt").split("\n")
        if (notes.size > 1){
            rcView.visibility = View.VISIBLE
            textView.visibility = View.INVISIBLE
            for (i in 0 until (notes.size - 3) step 3){
                val title: String =
                    if (notes[i] == ""){
                        " "
                    } else{
                        notes[i]
                    }
                val note: String =
                    if (notes[i+1] == ""){
                        " "
                    }
                    else {
                        notes[i+1]
                    }
                val countP: Int = notes[i+2].toInt()
                adapter.addNote(Note(title, note, countP))
            }
        }

        println(notes)


    }

    override fun onClickRmvBtn(note: Note) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Вы точно хотите удалить заметку?")
        builder.setPositiveButton("Ок") { dialog, _ ->
            // Implement the Code when OK Button is Clicked
            adapter.removeNote(note)
            deleteNote(note.book_title, note.note_text, note.count_page)
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

        println("Append new line inside file")
        println(data)
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

    fun deleteNote(title: String, note: String, count: Int){
        val data = getDataFromFile("notes.txt").split("\n")
        println(data)
        println(data::class.simpleName)

        var l: String = ""
        for (i in 0 .. (data.size-2) step 3) {
            if (
                (data[i] == title) and
                (data[i+1] == note) and
                (data[i+2].toInt() == count)
                ) {
                continue
            }
            else l += (data[i] + "\n" + data[i+1] + "\n" + data[i+2] + "\n")
        }
        rewriteFile("notes.txt", l)
        println(l)
    }

    fun rewriteFile(file: String, data: String) {
        val fileOutputStream: FileOutputStream
        // https://stackoverflow.com/questions/4015773/the-method-openfileoutput-is-undefined
        fileOutputStream = requireActivity().openFileOutput(file, Context.MODE_PRIVATE)
        fileOutputStream.write(data.toByteArray())

        println("Rewrite file")
        println(data)
    }
}