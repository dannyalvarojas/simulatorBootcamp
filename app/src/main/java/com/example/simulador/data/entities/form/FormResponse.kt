package com.example.simulador.data.entities.form

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormResponse(
        val code: Int,
        val response: ResponseObject

) : Parcelable

@Parcelize
data class ResponseObject(
        val tarjetas: Card,
        val cuotas: List<String>,
        val dias_pagos: List<String>,
        val tea: List<String>
) : Parcelable
