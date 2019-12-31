package com.base.ui.fragment.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.base.base.BaseViewModel
import com.base.model.retrofit.request.LoginRequest
import com.base.model.retrofit.response._loginAuth.LoginResponse
import com.base.network.APIClient

class SplashVM(app: Application, client: APIClient) : BaseViewModel(app, client)