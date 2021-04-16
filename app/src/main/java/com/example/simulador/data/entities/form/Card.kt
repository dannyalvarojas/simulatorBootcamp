package com.example.simulador.data.entities.form

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Card (
    val clasica: String,
    val oro: String,
    val black: String
    ) : Parcelable