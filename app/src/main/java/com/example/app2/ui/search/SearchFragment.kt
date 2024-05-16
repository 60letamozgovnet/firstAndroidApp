package com.example.app2.ui.search

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.Book
import com.example.app2.BookAdapter
import com.example.app2.GlobalDataBase
import com.example.app2.R
import com.example.app2.databinding.FragmentSearchBinding
import com.example.app2.retrofit.BookDb
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date


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
            adapter.clear()
            CoroutineScope(Dispatchers.IO).launch {
                var list: List<BookDb> = GlobalDataBase().getBooks()
                for (i in list) {
                    activity?.runOnUiThread {
                        adapter.addBook(Book(i.bookname, i.author, i.genre))
                    }
                }
            }
        }

        binding.addBookInGDB.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_add_book, null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.CYAN))
            myDialog.show()

            dialogBinding.findViewById<Button>(R.id.saveForBk).setOnClickListener {
                val title: String = dialogBinding.findViewById<EditText>(R.id.titleForBk).text.toString()
                val authors: String = dialogBinding.findViewById<EditText>(R.id.authorForBk).text.toString()
                val genre: String = dialogBinding.findViewById<EditText>(R.id.genreForBk).text.toString()


                if (title.isNotEmpty()){
                    val token: String = getDataFromFile("token_bearer.txt").replace("\\s".toRegex(), "")
                    println(token)
                    println("$title $authors $genre")
                    CoroutineScope(Dispatchers.IO).launch {
                        println(GlobalDataBase().addBook(BookDb(title, authors, genre), token))
                    }
                    myDialog.dismiss()
                }
            }
        }

        return root
    }

    override fun OnClickView(book: Book) {
        val database = Firebase.database
        val ref = database.getReference(book.title)

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

        var username: String = getDataFromFile("profile.txt").split("\n")[0]
        val mesEdTV = dialogBinding.findViewById<EditText>(R.id.sendedText)
        if (username.isEmpty()){
            username = "Unregister user"
        }

        myDialog.show()
        btnAdd.setOnClickListener {
            appendNewLine("my_books.txt", title.text.toString() + "\n" + authors.text.toString() + "\n" + "Генри :)")
            myDialog.dismiss()
        }

        val tv: TextView = dialogBinding.findViewById<TextView>(R.id.textId)

        dialogBinding.findViewById<Button>(R.id.btnSend).setOnClickListener {
            val mes = mesEdTV.text.toString()
            onChangeListener(ref, tv)
            sendMsg(ref, username, mes)
        }
    }

    fun appendNewLine(file: String, data: String) {
        val fileOutputStream: FileOutputStream
        fileOutputStream = requireActivity().openFileOutput(file, Context.MODE_APPEND)
        fileOutputStream.write(data.toByteArray())
        fileOutputStream.write("\n".toByteArray())

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

    fun rewriteFile(file: String, data: String) {
        val fileOutputStream: FileOutputStream
        // https://stackoverflow.com/questions/4015773/the-method-openfileoutput-is-undefined
        fileOutputStream = requireActivity().openFileOutput(file, Context.MODE_PRIVATE)
        fileOutputStream.write(data.toByteArray())
//        fileOutputStream.write("\n".toByteArray())

        println("Rewrite file")
        println(data)
    }

    fun deleteBook(name: String){
        val data: String = getDataFromFile("my_books.txt")

        var cnt: Int = 0
        var cnt2: Int = 0
        var l: String = ""
        for (i in data.split("\n")) {
            if ((0 < cnt2) and (cnt2 <= 2)) {
                cnt2 ++
                continue
            }
            if ((cnt % 3 == 1) and (i == name)) {
                cnt2 = 1
                cnt += 3
                continue
            }

            l += (i + "\n")
            cnt ++
        }
        rewriteFile("my_books.txt", l)
        println(l)
    }

    fun sendMsg(ref: DatabaseReference, username: String, msg: String) {
        ref.get().addOnSuccessListener {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = "(" + sdf.format(Date()) + ")"

            var tmp: String
            tmp = it.value.toString() + currentDate + " " + username + ": " + msg + "\n"

            ref.setValue(tmp)
            // println(it.value.toString())


            // Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        println("end of foo")
    }

    fun onChangeListener(dRef: DatabaseReference, tv: TextView) {
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //tv.append("\n")
                tv.text = snapshot.value.toString()
                // tv.append(snapshot.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}