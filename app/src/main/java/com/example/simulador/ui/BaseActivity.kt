package com.example.simulador.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.simulador.R

open class BaseActivity : AppCompatActivity() {

    private lateinit var loaderView: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private lateinit var messageTextView: TextView

    override fun setContentView(rootView: View) {
        super.setContentView(rootView)
        this.setupLoaderView()
    }

    private fun setupLoaderView() {
        loaderView = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.loader_dialog, null)
        messageTextView = dialogView.findViewById<TextView>(R.id.message)
        loaderView.setView(dialogView)
        loaderView.setCancelable(false)
        dialog = loaderView.create()
    }

    fun showLoader(message: String) {
        messageTextView.text = message
        dialog.show()
    }

    fun dismissLoader() {
        dialog.dismiss()
    }

    fun showDialogError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}