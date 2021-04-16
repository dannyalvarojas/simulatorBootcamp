package com.example.simulador.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.lifecycle.Observer
import com.example.simulador.data.NetworkMessage
import com.example.simulador.data.entities.constancy.ConstancyResponse
import com.example.simulador.data.entities.form.Card
import com.example.simulador.data.entities.form.FormRequest
import com.example.simulador.data.entities.form.FormResponse
import com.example.simulador.databinding.ActivityMainBinding
import com.example.simulador.util.Constants
import com.example.simulador.viewModel.form.SimulatorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimulatorActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: SimulatorViewModel by viewModels()
    private lateinit var cardAdapter: SpinnerAdapter
    private lateinit var feeAdapter: SpinnerAdapter
    private lateinit var teaAdapter: SpinnerAdapter
    private lateinit var daysAdapter: SpinnerAdapter

    private lateinit var cardList: MutableList<String>
    private lateinit var feeDays: List<String>
    private lateinit var teaList: List<String>
    private lateinit var feeList: List<String>

    private var cardSelected: String = ""
    private var feeDaySelected: String = ""
    private var feeSelected: String = ""
    private var teaSelected: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        viewModel.getDataForm()
        nextButtonSetup()
    }

    private fun setupViewModel() {
        viewModel.formData.observe(this, formObserver)
        viewModel.constacyData.observe(this, constancyObserver)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.dniValidate.observe(this,dniValidateObserver)
    }

    private val formObserver = Observer<FormResponse> { data ->
        val cardsResponse = data.response.tarjetas
        cardList = ArrayList<String>()
        cardList.add(cardsResponse.clasica)
        cardList.add(cardsResponse.black)
        cardList.add(cardsResponse.oro)

        feeDays = data.response.dias_pagos
        feeList = data.response.cuotas
        teaList = data.response.tea

        cardSpinnerSetup()
        feeSpinnerSetup()
        teaSpinnerSetup()
        feeDaySpinnerSetup()

    }

    private val dniValidateObserver = Observer<String> {
        this.showDialogError(it)
    }

    private fun cardSpinnerSetup() {
        val spinner = binding.cardTypeSpinner
        cardSelected = KEY_CLASICA_VALUE
        cardAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cardList)
        spinner.adapter = cardAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val cardItem = cardList[position]
                if (cardItem.equals(KEY_CLASICA)) {
                    cardSelected = KEY_CLASICA_VALUE
                } else {
                    cardSelected = cardItem
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }

        }

    }

    private fun feeSpinnerSetup() {
        val spinner = binding.feeSpinner
        feeSelected = feeList.first()
        feeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, feeList)
        spinner.adapter = feeAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                feeSelected = feeList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }

        }
    }

    private fun feeDaySpinnerSetup() {
        feeDaySelected == feeDays.first()
        val spinner = binding.daySpinner
        daysAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, feeDays)
        spinner.adapter = daysAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                feeDaySelected = feeDays[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }

        }
    }

    private fun teaSpinnerSetup() {
        teaSelected = teaList.first()
        val spinner = binding.teaSpinner
        teaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, teaList)
        spinner.adapter = teaAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                teaSelected = teaList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }

        }
    }

    private fun nextButtonSetup() {
        binding.nextButton.setOnClickListener() {

            val dni = binding.dniNumberValueTextView.text.toString()
            val amount = binding.amountTextView.text.toString()

            val request = FormRequest(dni, cardSelected.toLowerCase(), amount, feeSelected.toInt(), teaSelected.toFloat(), feeDaySelected.toInt())
            viewModel.evaluateSimulator(request)
        }
    }

    private val constancyObserver = Observer<ConstancyResponse> { data ->

        if (!data.code.equals(Constants.RESPONSE_OK)) {
            this.showDialogError(data.mensaje)
            return@Observer
        }

        val constancy = data.response
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(Constants.KEY_CONSTANCY, constancy)
        startActivity(intent)
    }

    private val isViewLoadingObserver = Observer<Boolean> { loading ->
        if (loading) {
            this.showLoader("Cargando...")
        } else {
            this.dismissLoader()
        }
    }

    private val onMessageErrorObserver = Observer<Any> {

        val error = it as NetworkMessage
        this.showDialogError(error.body)

    }

    companion object {
        const val KEY_CLASICA = "Clásica"
        const val KEY_CLASICA_VALUE = "clasica"
    }
}