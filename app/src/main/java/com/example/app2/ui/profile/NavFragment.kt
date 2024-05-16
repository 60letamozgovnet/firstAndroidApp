package com.example.app2.ui.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils.split
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.Book
import com.example.app2.BookAdapter
import com.example.app2.GlobalDataBase
import com.example.app2.R
import com.example.app2.retrofit.BookDb
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date

class NavFragment : Fragment(), BookAdapter.Listener {
    private val adapter_readable = BookAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View? {
        val view1 = inflater.inflate(R.layout.fragment_prof_nav, container, false)
        val readable: RecyclerView = view1.findViewById(R.id.readableBooks)
        val username: TextView = view1.findViewById(R.id.nameUser)
        val profFile = getDataFromFile("profile.txt").split("\n")
        if (profFile.size > 1){
            username.text = profFile[0]
        }

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
            view1.findViewById<TextView>(R.id.empty_rcView).visibility = View.INVISIBLE
        }

        return view1
    }

    override fun OnClickView(book: Book) {
        val database = Firebase.database
        val ref = database.getReference("message")

        val dialogBinding = layoutInflater.inflate(R.layout.dialog_book, null)
        val myDialog = Dialog(requireContext())
        val title: TextView = dialogBinding.findViewById(R.id.BkTitle)
        title.text = book.title
        val authors: TextView = dialogBinding.findViewById(R.id.authorsDialog)
        authors.text = book.about
        val btnAdd: Button = dialogBinding.findViewById(R.id.btn_add_in_my_book)
        btnAdd.text = "Удалить из читаемых"

        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.LTGRAY))
        val tv: TextView = dialogBinding.findViewById<TextView>(R.id.textId)

        var username: String = getDataFromFile("profile.txt").split("\n")[0]
        val mesEdTV = dialogBinding.findViewById<EditText>(R.id.sendedText)
        if (username.isEmpty()){
            username = "Unregister user"
        }

        myDialog.show()
        btnAdd.setOnClickListener {
            deleteBook(book.title, book.about, book.genre)
            adapter_readable.deleteBook(book)
            myDialog.dismiss()
        }

        dialogBinding.findViewById<Button>(R.id.btnSend).setOnClickListener {
            val mes = mesEdTV.text.toString()
            onChangeListener(ref, tv)
            sendMsg(ref, username, mes)
        }
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

    fun deleteBook(title: String, about: String, genre: String){
        val data = getDataFromFile("my_books.txt").split("\n")
        println(data)
        println(data::class.simpleName)

        var l: String = ""
        for (i in 0 .. (data.size-2) step 3) {
            if (
                (data[i] == title) and
                (data[i+1] == about) and
                (data[i+2] == genre)
            ) {
                continue
            }
            else l += (data[i] + "\n" + data[i+1] + "\n" + data[i+2] + "\n")
        }
        rewriteFile("my_books.txt", l)
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