package com.example.simulador.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorObjectResponse(
        val exceptionCategory: String,
        val exceptionCode: String,
        val exceptionMessage: String,
        val exceptionDetail: String,
        val exceptionSeverity: String
) : Parcelable
