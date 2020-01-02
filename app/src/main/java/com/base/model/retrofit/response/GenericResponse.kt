package com.base.model.retrofit.response

data class GenericResponse<T>(
    val `data`: T,
    val errorMessage: String,
    val isSuccess: Boolean,
    var statusCode: String
)