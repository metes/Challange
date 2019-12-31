package com.base.ui.fragment.songs.parent

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.base.base.BaseViewModel
import com.base.model.retrofit.request.LoginRequest
import com.base.model.retrofit.response._loginAuth.LoginResponse
import com.base.network.APIClient

class SongsVM(app: Application, client: APIClient) : BaseViewModel(app, client) {

    val loginLiveData = MutableLiveData<LoginResponse>()

    fun login(request: LoginRequest) {
        loginLiveData.sendLoginRequest(request)
    }



}
