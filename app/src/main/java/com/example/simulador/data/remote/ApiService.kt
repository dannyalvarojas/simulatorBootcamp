package com.example.simulador.data.remote

import com.example.simulador.data.entities.constancy.ConstancyResponse
import com.example.simulador.data.entities.form.FormRequest
import com.example.simulador.data.entities.form.FormResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("bootcamp/wp-json/bcp/simulator")
    suspend fun dataForm(): Response<FormResponse>

    @FormUrlEncoded
    @POST("bootcamp/wp-json/bcp/simulator")
    suspend fun evaluateForm(@Field("dni") dni: Int,
                             @Field("cuotas") cuotas: Int,
                             @Field("monto") monto: Float,
                             @Field("tea") tea: Float,
                             @Field("dia_pago") dayPago: Int,
                             @Field("tarjeta") card: String): Response<ConstancyResponse>
}