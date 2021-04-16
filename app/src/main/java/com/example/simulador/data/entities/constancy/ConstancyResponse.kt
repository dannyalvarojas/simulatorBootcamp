package com.example.simulador.data.entities.constancy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConstancyResponse(
        val code: Int,
        var response: Constancy,
        var mensaje: String
) : Parcelable

@Parcelize
data class Constancy(
        val cuota: String,
        val moneda: String,
        val primeraCuota: String,
        val estado: String
) : Parcelable
