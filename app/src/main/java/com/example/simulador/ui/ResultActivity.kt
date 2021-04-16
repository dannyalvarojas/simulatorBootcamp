package com.example.simulador.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simulador.R
import com.example.simulador.data.entities.constancy.Constancy
import com.example.simulador.databinding.ActivityResultBinding
import com.example.simulador.util.Constants

class ResultActivity : BaseActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadConstancy()
        setupCloseButton()
    }

    private fun loadConstancy() {
        val amountTextView = binding.amountTextView
        val dateTextView = binding.dateFeeTextView

        intent.getParcelableExtra<Constancy>(Constants.KEY_CONSTANCY)?.let {
            amountTextView.text = "${it.moneda} ${it.cuota}"
            dateTextView.text = "Fecha de pago de primera cuota: ${it.primeraCuota}"
        }
    }

    private fun setupCloseButton() {
        val closeButton = binding.backButton

        closeButton.setOnClickListener() {
            this.finish()
        }
    }

}