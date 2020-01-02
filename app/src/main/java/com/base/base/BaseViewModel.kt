package com.base.base

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.base.R
import com.base.commons.SingleLiveEvent
import com.base.model.retrofit.response.GenericResponse
import com.base.network.APIClient
import com.base.network.Repository.genericRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class BaseViewModel(private val myApp: Application, private val apiClient: APIClient) :
    AndroidViewModel(myApp) {

    /**
     *  Hata olmasi durumunda UI'da kullanmak uzere tanimlanan SingleLiveEvent
     */
    val networkErrorDetection by lazy { SingleLiveEvent<String>() }
    /**
     *  Request oncesinde true sonrasinda false olan SingleLiveEvent
     */
    val loadingDetection by lazy { SingleLiveEvent<Boolean>() }

    /**
     *  Retrofit'den donen hatalar burada yakalaniyor. Exception messaji kullaniciya gosterebilir veya ozellestirilebilir
     */
    private fun throwError(errorMessage: String?) {
        val message = when {
            errorMessage?.startsWith("HTTP 400") == true -> myApp.getString(R.string.error_message_401)
            errorMessage?.startsWith("HTTP 401") == true -> myApp.getString(R.string.error_message_401)
            errorMessage?.startsWith("HTTP 404") == true -> myApp.getString(R.string.error_message_404)
            errorMessage?.startsWith("HTTP 500") == true -> myApp.getString(R.string.error_message_500)
            else -> if (errorMessage.isNullOrBlank()) myApp.getString(R.string.error_message_main) else errorMessage
        }
        networkErrorDetection.postValue(message)
    }

    /**
     *  Tum generic response'lar icin request yollar
     */
    @SuppressLint("LogNotTimber")
    fun <T : Any> MutableLiveData<T>.sendRequest(client: suspend () -> GenericResponse<T>, function: ((T?) -> Unit)? = null) {
        viewModelScope.launch {
            loadingDetection.postValue(true)
            val result = genericRepository(client)
            when {
                result.isSuccess -> {
                    val responseData = result.getOrNull()?.data
                    function?.let { it(responseData) }
                    postValue(responseData)
                }
                else -> throwError(result.exceptionOrNull()?.message)
            }
            loadingDetection.postValue(false)
        }
    }

fun <T>sendRequest(client: suspend () -> GenericResponse<T>, function: ((T?) -> Unit)? = null) {
        viewModelScope.launch {
            loadingDetection.postValue(true)
            val result = genericRepository(client)
            when {
                result.isSuccess -> {
                    val responseData = result.getOrNull()?.data
                    function?.let { it(responseData) }
                }
                else -> throwError(result.exceptionOrNull()?.message)
            }
            loadingDetection.postValue(false)
        }
    }



    /**
     *  suspended function'lardan olusan vararg'lar olarak gonderilen requestleri sırayla calistirir
     *  loadingDetection ilk sorgu baslangici /son sorgu sonu arasında calisir.
     *  hatalar yakalanir.
     *
     *  Ornek:
    //        viewModelScope.launch {
    //            sendFlowRequest(
    //                { client.forgotPassClient(request) },
    //                { client.forgotPassClient(request) },
    //                { client.forgotPassClient(request) },
    //                { client.forgotPassClient(request) },
    //                { client.forgotPassClient(request) },
    //                { client.forgotPassClient(request) }
    //            ).collect {
    //                print("finished")
    //            }
     */
    @ExperimentalCoroutinesApi
    fun <T : Any> sendFlowRequest(
        vararg list: (suspend () -> GenericResponse<T>)
    ): Flow<Result<GenericResponse<T>>> {
        return list.asFlow().onStart {
            loadingDetection.postValue(true)
        }.onCompletion {
            loadingDetection.postValue(false)
        }.catch {
            throwError(it.message)
        }.transform {
            emit(genericRepository(it))
        }
    }






}