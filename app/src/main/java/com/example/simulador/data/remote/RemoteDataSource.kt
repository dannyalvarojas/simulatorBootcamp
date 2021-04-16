package com.example.simulador.data.remote

import com.example.simulador.data.entities.form.FormRequest
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private  val service: ApiService
) : BaseDataSource() {

    suspend fun dataForm() = safeApiCall { service.dataForm() }

    suspend fun dataConstancy(dataForm: FormRequest) = safeApiCall { service.evaluateForm(dataForm.dni.toInt(),
                                                                                            dataForm.cuotas,
                                                                                            dataForm.monto.toFloat(),
                                                                                            dataForm.tea,
                                                                                            dataForm.dia_pago,
                                                                                            dataForm.tarjeta) }
}