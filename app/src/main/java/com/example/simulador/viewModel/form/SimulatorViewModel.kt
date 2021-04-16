package com.example.simulador.viewModel.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simulador.data.entities.constancy.ConstancyResponse
import com.example.simulador.data.entities.form.FormRequest
import com.example.simulador.data.entities.form.FormResponse
import com.example.simulador.data.remote.Result
import com.example.simulador.data.respository.SimulatorRepository
import com.example.simulador.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SimulatorViewModel @Inject constructor(private val repository: SimulatorRepository) : BaseViewModel() {

    private val _formData = MutableLiveData<FormResponse>()
    val formData: LiveData<FormResponse> = _formData

    private val _constancyData = MutableLiveData<ConstancyResponse>()
    val constacyData: LiveData<ConstancyResponse> = _constancyData

    private val _dniValidate = MutableLiveData<String>()
    val dniValidate: LiveData<String> = _dniValidate

    private val _amountValidate = MutableLiveData<String>()
    val amountValidate: LiveData<String> = _amountValidate

    fun getDataForm() {
        _isViewLoading.postValue(true)

        viewModelScope.launch {
            val result: Result<FormResponse> = withContext(Dispatchers.IO) {
                repository.dataForm()
            }

            _isViewLoading.postValue(false)

            when (result) {
                is Result.Success -> {
                    _formData.value = result.data
                }

                is Result.ApiError -> _onMessageError.postValue(result.exception)
            }
        }
    }

    fun evaluateSimulator(request: FormRequest) {

        if (!validateDni(request.dni)) {
            return
        }

        if (!validateAmount(request.monto)) {
            return
        }

        _isViewLoading.postValue(true)

        viewModelScope.launch {
            val result: Result<ConstancyResponse> = withContext(Dispatchers.IO) {
                repository.dataConstany(request)
            }

            _isViewLoading.postValue(false)

            when (result) {
                is Result.Success -> {
                    _constancyData.value = result.data
                }

                is Result.ApiError -> _onMessageError.postValue(result.exception)
            }
        }
    }


    fun validateDni(dni: String): Boolean {
        if (dni.isEmpty()) {
            _dniValidate.value = "Ingresa tu DNI."
            return false
        }
        if (dni.length < 8 || dni.length > 8) {
            _dniValidate.value = "DNI inválido. Ingrese nuevamente."
            return false
        }
        return true
    }

    fun validateAmount(amount: String): Boolean {

        if (amount.isEmpty()) {
            _dniValidate.value = "Ingresa un monto."
            return false
        }
        return true
    }

}

interface SimulatorViewModelProtocol {
    fun validateDNI(message: String)
}