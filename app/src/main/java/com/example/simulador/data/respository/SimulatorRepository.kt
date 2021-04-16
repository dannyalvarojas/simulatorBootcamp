package com.example.simulador.data.respository

import com.example.simulador.data.entities.form.FormRequest
import com.example.simulador.data.entities.form.FormResponse
import com.example.simulador.data.remote.BaseDataSource
import com.example.simulador.data.remote.RemoteDataSource
import com.example.simulador.data.remote.Result
import javax.inject.Inject

class SimulatorRepository @Inject constructor(
        private val remoteDataSource: RemoteDataSource
) {

    suspend fun dataForm(): Result<FormResponse> = remoteDataSource.dataForm()

    suspend fun dataConstany(dataForm: FormRequest) = remoteDataSource.dataConstancy(dataForm)
}