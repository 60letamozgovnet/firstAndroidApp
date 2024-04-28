package com.example.app2

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView

class CustomDialog : DialogFragment() {
    private var title: String = ""
    private var note_text: String = ""
    private var countP: Int = 0
    private lateinit var btnSave: Button
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
            .setView(R.layout.custom_dialog_add_note)
            .create()

        btnSave = builder.getButton(R.id.save)
        btnSave.setOnClickListener {
            requireView().findViewById<RecyclerView>(R.id.rcView).adapter
        }
        return builder
    }

    fun getBtnSave(): Button {
        return btnSave
    }
}