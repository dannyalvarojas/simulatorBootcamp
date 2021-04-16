package com.example.simulador.data.entities.form

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormRequest(
        val dni: String,
        val tarjeta: String,
        val monto: String,
        val cuotas: Int,
        val tea: Float,
        val dia_pago: Int
) : Parcelable
