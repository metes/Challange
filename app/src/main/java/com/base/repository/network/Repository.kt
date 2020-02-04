package com.base.repository.network

import com.base.model.retrofit.response.GenericResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

object Repository {

    suspend fun <T> genericRepository(client: suspend () -> GenericResponse<T>): Result<GenericResponse<T>> {
        return handleRequest { client.invoke() }
    }

    private suspend fun <T : Any> handleRequest(requestFunc: suspend () -> T): Result<T> {
        return try {
            Result.success(requestFunc.invoke())
        } catch (httpException: HttpException) {
            val errorMessage = getErrorMessageFromGenericResponse(httpException)
            if (errorMessage.isNullOrBlank()) {
                Result.failure(httpException)
            } else {
                Result.failure(Throwable(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Throwable(e.message))
        }
    }

    private fun getErrorMessageFromGenericResponse(httpException: HttpException): String? {
        var errorMessage: String? = null
        try {
            val body = httpException.response()?.errorBody()
            val adapter = Gson().getAdapter(GenericResponse::class.java)
            val errorParser = adapter.fromJson(body?.string())
            errorMessage = errorParser.errorMessage
        } catch (e: IOException) {
            e.printStackTrace()
            if (errorMessage.isNullOrBlank()) {
                errorMessage = e.message
            }
        } finally {
            return errorMessage
        }
    }


}